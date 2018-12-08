package com.ufpe.if710.quentinhas.provider

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.ufpe.if710.quentinhas.R
import com.ufpe.if710.quentinhas.model.User
import kotlinx.android.synthetic.main.activity_provider_configurations.*

class ProviderConfigurationsActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private var currentUser: FirebaseUser? = null
    private var usersRef: DatabaseReference? = null

    private var user: User? = null
    private var key: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_provider_configurations)

        setSupportActionBar(toolbar_configurations)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        picker_end.setIs24HourView(true)
        picker_arrival.setIs24HourView(true)

        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth!!.currentUser
        usersRef = FirebaseDatabase.getInstance().reference.child("users")

        findUser()
    }

    override fun onSupportNavigateUp():Boolean {
        onBackPressed()
        return true
    }

    private fun findUser(){
        val userID = currentUser!!.uid
        val query = usersRef!!.orderByKey().equalTo(userID)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println(p0.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.children.first()
                user = data.getValue(User::class.java)
                key = data.key
            }
        })
    }
}

package com.ufpe.if710.quentinhas

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.FirebaseError
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.ufpe.if710.quentinhas.model.User

class MyRequestsActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private var currentUser: FirebaseUser? = null

    private var user: User? = null
    private var name: String? = null
    private var email: String? = null
    private var phone: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_requests)

        mAuth = FirebaseAuth.getInstance()

    }

    override fun onStart() {
        super.onStart()
        currentUser = mAuth!!.currentUser
        updateUI()
    }

    private fun updateUI(){
        val userID = currentUser!!.uid
        val usersRef = FirebaseDatabase.getInstance().reference.child("users")
        val query = usersRef.orderByKey()

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println(p0.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot.children
                println("count: "+snapshot.children.count().toString())
                children.forEach {
                    if (it.key.equals(userID)){
                        user = it.getValue(User::class.java)
                        name = user!!.name
                        email = user!!.email
                        phone = user!!.phone
                        Log.d("encontrado", "$name $email $phone")
                    }
                }
            }
        })
    }

}

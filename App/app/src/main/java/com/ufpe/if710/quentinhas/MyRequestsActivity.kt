package com.ufpe.if710.quentinhas

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.FirebaseError
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class MyRequestsActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private var currentUser: FirebaseUser? = null


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
        var userID = currentUser!!.uid
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
                    println(it.toString())
                }
            }
        })
    }

}

package com.ufpe.if710.quentinhas

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ufpe.if710.quentinhas.client.ClientTabbedActivity
import com.ufpe.if710.quentinhas.model.User
import com.ufpe.if710.quentinhas.provider.ProviderTabbedActivity


class LoginActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private var currentUser: FirebaseUser? = null

    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setSupportActionBar(toolbar_login)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        mAuth = FirebaseAuth.getInstance()
//        currentUser = mAuth!!.currentUser

        btn_login.setOnClickListener {
            login()
        }
    }

    override fun onSupportNavigateUp():Boolean {
        onBackPressed()
        return true
    }

    private fun login () {
        val email = edit_email.text.toString()
        val password = edit_password.text.toString()

        if (!email.isEmpty() && !password.isEmpty()) {
            progress_bar_login.visibility = View.VISIBLE

            this.mAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener ( this) { task ->
                if (task.isSuccessful) {
                    currentUser = mAuth!!.currentUser
                    findUser()
                } else {
                    progress_bar_login.visibility = View.GONE
                    Toast.makeText(this, "Error Logging in :(", Toast.LENGTH_SHORT).show()
                }
            }

        }else {
            Toast.makeText(this, "Please fill up the Credentials :|", Toast.LENGTH_SHORT).show()
        }
    }

    private fun goHome(){
        val intent: Intent = if (user!!.provider!!){
            Intent(this, ProviderTabbedActivity::class.java)
        } else {
            Intent(this, ClientTabbedActivity::class.java)
        }
        progress_bar_login.visibility = View.GONE
        startActivity(intent)
    }

    private fun findUser(){
        val userID = currentUser!!.uid
        val usersRef = FirebaseDatabase.getInstance().reference.child("users")
        val query = usersRef.orderByKey().equalTo(userID)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println(p0.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.children.first()
                user = data.getValue(User::class.java)
                goHome()
            }

        })
    }
}

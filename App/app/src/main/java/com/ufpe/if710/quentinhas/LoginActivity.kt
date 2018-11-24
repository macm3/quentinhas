package com.ufpe.if710.quentinhas

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import com.google.firebase.auth.FirebaseUser



class LoginActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private var currentUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setSupportActionBar(toolbar_login)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        mAuth = FirebaseAuth.getInstance()

        btn_login.setOnClickListener {
            login()
        }
    }

    override fun onSupportNavigateUp():Boolean {
        onBackPressed()
        return true
    }

    override fun onStart() {
        super.onStart()
        currentUser = mAuth!!.currentUser
    }

    private fun login () {
        val email = edit_email.text.toString()
        val password = edit_password.text.toString()

        if (!email.isEmpty() && !password.isEmpty()) {
            progress_bar_login.visibility = View.VISIBLE

            this.mAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener ( this) { task ->
                if (task.isSuccessful) {
                    progress_bar_login.visibility = View.GONE
                    val intent = Intent(this, MyRequestsActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Successfully Logged in :)", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Error Logging in :(", Toast.LENGTH_SHORT).show()
                }
            }

        }else {
            Toast.makeText(this, "Please fill up the Credentials :|", Toast.LENGTH_SHORT).show()
        }
    }
}

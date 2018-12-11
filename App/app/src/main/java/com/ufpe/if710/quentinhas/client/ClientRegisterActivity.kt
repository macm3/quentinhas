package com.ufpe.if710.quentinhas.client

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.ufpe.if710.quentinhas.MyOrdersActivity
import com.ufpe.if710.quentinhas.R
import com.ufpe.if710.quentinhas.model.User
import kotlinx.android.synthetic.main.activity_client_register.*


class ClientRegisterActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private var mDatabase: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_register)

        setSupportActionBar(toolbar_register_client)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        mAuth = FirebaseAuth.getInstance()

        btn_register_client.setOnClickListener {
            registerUser()
        }
    }

    override fun onSupportNavigateUp():Boolean {
        onBackPressed()
        return true
    }

    private fun registerUser(){
        val email = edit_register_client_email.text.toString()
        val password = edit_register_client_password.text.toString()
        val name = edit_register_client_name.text.toString()
        val phone = edit_register_client_phone.text.toString()
        progress_bar_client.visibility = View.VISIBLE

        if (!email.isEmpty() && !password.isEmpty()) {
            mAuth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    createUser(mAuth!!.currentUser!!, email, name, phone)
                }else {
                    progress_bar_client.visibility = View.GONE
                    Toast.makeText(this, "Error registering, try again later :(", Toast.LENGTH_LONG).show()
                }
            }
        }else {
            progress_bar_client.visibility = View.GONE
            Toast.makeText(this,"Please fill up the Credentials :|", Toast.LENGTH_LONG).show()
        }
    }

    private fun createUser(firebaseUser: FirebaseUser, email: String, name: String, phone: String){
        mDatabase = FirebaseDatabase.getInstance().reference
        mAuth = FirebaseAuth.getInstance()

        val userId = firebaseUser.uid

        val user = User(null, name, email, phone, arrayListOf(), null, null, null, arrayListOf(), false)

        mDatabase!!.child("users").child(userId).setValue(user).addOnCompleteListener {
            progress_bar_client.visibility = View.GONE
            startActivity(Intent(this, MyOrdersActivity::class.java))
        }
    }

}

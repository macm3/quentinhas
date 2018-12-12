package com.ufpe.if710.quentinhas.provider

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.ufpe.if710.quentinhas.R
import com.ufpe.if710.quentinhas.model.User
import kotlinx.android.synthetic.main.activity_provider_register.*

class ProviderRegisterActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private var mDatabase: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_provider_register)

        setSupportActionBar(toolbar_register_provider)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        mAuth = FirebaseAuth.getInstance()

        btn_register_provider.setOnClickListener {
            registerUser()
        }
    }

    override fun onSupportNavigateUp():Boolean {
        onBackPressed()
        return true
    }

    private fun registerUser(){
        val email = edit_register_provider_email.text.toString()
        val password = edit_register_provider_password.text.toString()
        val name = edit_register_provider_name.text.toString()
        val phone = edit_register_provider_phone.text.toString()
        val restaurant = edit_register_provider_restaurant.text.toString()
        progress_bar_provider.visibility = View.VISIBLE

        if (!email.isEmpty() && !password.isEmpty()) {
            mAuth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    createUser(mAuth!!.currentUser!!, email, name, phone, restaurant)
                }else {
                    progress_bar_provider.visibility = View.GONE
                    Toast.makeText(this, "Error registering, try again later :(", Toast.LENGTH_LONG).show()
                }
            }
        }else {
            progress_bar_provider.visibility = View.GONE
            Toast.makeText(this,"Please fill up the Credentials :|", Toast.LENGTH_LONG).show()
        }
    }

    private fun createUser(firebaseUser: FirebaseUser, email: String, name: String, phone: String, restaurant: String){
        mDatabase = FirebaseDatabase.getInstance().reference
        mAuth = FirebaseAuth.getInstance()

        val userId = firebaseUser.uid

        val user = User(restaurant, name, email, phone, arrayListOf(), null, null, null, arrayListOf(),true)

        mDatabase!!.child("users").child(userId).setValue(user).addOnCompleteListener {
            progress_bar_provider.visibility = View.GONE
            startActivity(Intent(this, ProviderTabbedActivity::class.java))
        }
    }
}

package com.ufpe.if710.quentinhas

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.ufpe.if710.quentinhas.LoginActivity.Companion.EMAIL
import com.ufpe.if710.quentinhas.LoginActivity.Companion.NAME
import com.ufpe.if710.quentinhas.LoginActivity.Companion.PHONE
import com.ufpe.if710.quentinhas.model.User
import kotlinx.android.synthetic.main.activity_client_register.*


class ClientRegisterActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_register)

        setSupportActionBar(toolbar_register_client)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        mAuth = FirebaseAuth.getInstance()

        btn_register_client.setOnClickListener {
            createUser()
        }
    }

    override fun onSupportNavigateUp():Boolean {
        onBackPressed()
        return true
    }

    private fun createUser(){
        val email = edit_register_client_email.text.toString()
        val password = edit_register_client_password.text.toString()
        val name = edit_register_client_name.text.toString()
        val phone = edit_register_client_phone.text.toString()
        progress_bar_client.visibility = View.VISIBLE

        if (!email.isEmpty() && !password.isEmpty()) {
            this.user?.email = email
            mAuth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    progress_bar_client.visibility = View.GONE
                    val intent = Intent(this, MyRequestsActivity::class.java)
                    intent.putExtra(NAME, name)
                    intent.putExtra(EMAIL, email)
                    intent.putExtra(PHONE, phone)
                    startActivity(intent)
                }else {
                    Toast.makeText(this, "Error registering, try again later :(", Toast.LENGTH_LONG).show()
                }
            }
        }else {
            Toast.makeText(this,"Please fill up the Credentials :|", Toast.LENGTH_LONG).show()
        }
    }


}

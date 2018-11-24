package com.ufpe.if710.quentinhas

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
    }

    override fun onSupportNavigateUp():Boolean {
        onBackPressed()
        return true
    }

    public override fun onStart() {
        super.onStart()
        currentUser = mAuth!!.currentUser
    }
}

package com.ufpe.if710.quentinhas

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val handle = Handler()
        handle.postDelayed({goToLogin()}, 2000)
    }

    private fun goToLogin(){
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

}

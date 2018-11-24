package com.ufpe.if710.quentinhas

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_register_main.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        btn_login_main.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    companion object {
        val NAME = "name"
        val EMAIL = "email"
        val PHONE = "phone"
        val RESTAURANT = "restaurant"
    }

}

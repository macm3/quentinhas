package com.ufpe.if710.quentinhas.provider

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ufpe.if710.quentinhas.R
import kotlinx.android.synthetic.main.activity_new_menu.*

class NewMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_menu)

        setSupportActionBar(toolbar_new_menu)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp():Boolean {
        onBackPressed()
        return true
    }
}

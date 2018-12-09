package com.ufpe.if710.quentinhas.provider

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ufpe.if710.quentinhas.R
import kotlinx.android.synthetic.main.activity_edit_menu.*

class EditMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_menu)

        setSupportActionBar(toolbar_edit_menu)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
    }
    
    override fun onSupportNavigateUp():Boolean {
        onBackPressed()
        return true
    }
}

package com.ufpe.if710.quentinhas.provider

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ufpe.if710.quentinhas.R
import kotlinx.android.synthetic.main.activity_provider_configurations.*

class ProviderConfigurationsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_provider_configurations)

        setSupportActionBar(toolbar_configurations)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        picker_end.setIs24HourView(true)
        picker_arrival.setIs24HourView(true)
    }

    override fun onSupportNavigateUp():Boolean {
        onBackPressed()
        return true
    }
}

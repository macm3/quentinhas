package com.ufpe.if710.quentinhas.order

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ufpe.if710.quentinhas.R
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.PROVIDER

class SizeActivity : AppCompatActivity() {
    private var providerID: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_size)

        providerID = intent.extras!!.getString(PROVIDER)
        Log.d("xablau", providerID)
    }
}

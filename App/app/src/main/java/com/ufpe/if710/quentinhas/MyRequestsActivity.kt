package com.ufpe.if710.quentinhas

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ufpe.if710.quentinhas.ClientRegisterActivity.Companion.EMAIL
import com.ufpe.if710.quentinhas.ClientRegisterActivity.Companion.NAME
import com.ufpe.if710.quentinhas.ClientRegisterActivity.Companion.PHONE
import kotlinx.android.synthetic.main.activity_my_requests.*

class MyRequestsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_requests)

        teste.text = "${intent.getStringExtra(NAME)} ${intent.getStringExtra(EMAIL)} ${intent.getStringExtra(PHONE)}"
    }
}

package com.ufpe.if710.quentinhas

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ufpe.if710.quentinhas.provider.ProviderRegisterActivity
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btn_register_provider.setOnClickListener {
            startActivity(Intent(this, ProviderRegisterActivity::class.java))
        }

        btn_register_client.setOnClickListener {
            startActivity(Intent(this, ClientRegisterActivity::class.java))
        }
    }

}

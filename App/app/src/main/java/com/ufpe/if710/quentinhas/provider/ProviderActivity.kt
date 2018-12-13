package com.ufpe.if710.quentinhas.provider

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.*
import com.ufpe.if710.quentinhas.R
import com.ufpe.if710.quentinhas.client.ClientOrderFragment
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.PROVIDER
import com.ufpe.if710.quentinhas.model.User
import com.ufpe.if710.quentinhas.order.ChooseMenuActivity
import kotlinx.android.synthetic.main.activity_provider.*

class ProviderActivity : AppCompatActivity() {
    private var providerID: String? = null
    private var usersRef: DatabaseReference? = null
    private var user: User? = null
    private var key: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_provider)

        setSupportActionBar(toolbar_provider)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        usersRef = FirebaseDatabase.getInstance().reference.child("users")

        providerID = intent.extras!!.getString(PROVIDER)

        findUser()

        btn_choose_provider.setOnClickListener {
            val intent = Intent(this, ChooseMenuActivity::class.java)
            intent.putExtra(ClientOrderFragment.PROVIDER, providerID)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp():Boolean {
        onBackPressed()
        return true
    }

    private fun updateUI(){
        toolbar_provider_title.text = "Fornecedor ${user!!.restaurant}"

        for (payment in user!!.paymentMethods){
            when(payment){
                User.PaymentMethod.CASH -> {
                    tv_payment_provider.text = "${tv_payment_provider.text}\n${resources.getString(R.string.toggle_cash)}"
                }
                User.PaymentMethod.PAGSEGURO -> {
                    tv_payment_provider.text = "${tv_payment_provider.text}\n${resources.getString(R.string.toggle_pagseguro)}"
                }
            }
        }

        tv_end_time_provider.text = user!!.endTime
        tv_pickup_time_provider.text = user!!.pickupTime
        tv_pickup_address_provider.text = user!!.address
    }

    private fun findUser(){
        val query = usersRef!!.orderByKey().equalTo(providerID)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println(p0.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val data = snapshot.children.first()
                    user = data.getValue(User::class.java)
                    key = data.key
                    updateUI()
                }
            }
        })
    }
}

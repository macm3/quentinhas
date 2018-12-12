package com.ufpe.if710.quentinhas.order

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ufpe.if710.quentinhas.R
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.MENU
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.NOTES
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.PAYMENT
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.PROTEIN
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.PROVIDER
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.SIDES
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.SIZE
import kotlinx.android.synthetic.main.activity_send_order.*

class SendOrderActivity : AppCompatActivity() {
    private var providerID: String? = null
    private var menuID: String? = null
    private var size: String? = null
    private var protein: String? = null
    private var sides: ArrayList<String>? = null
    private var notes: String? = null
    private var paymentMethod: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_order)

        setSupportActionBar(toolbar_send_order)

        providerID = intent.extras!!.getString(PROVIDER)
        menuID = intent.extras!!.getString(MENU)
        size = intent.extras!!.getString(SIZE)
        protein = intent.extras!!.getString(PROTEIN)
        sides = intent.extras!!.getStringArrayList(SIDES)
        notes = intent.extras!!.getString(NOTES)
        paymentMethod = intent.extras!!.getString(PAYMENT)
    }
}

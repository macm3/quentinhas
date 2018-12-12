package com.ufpe.if710.quentinhas.order

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ufpe.if710.quentinhas.R
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.MENU
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.NOTES
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.PROTEIN
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.PROVIDER
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.SIDES
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.SIZE
import com.ufpe.if710.quentinhas.model.User
import kotlinx.android.synthetic.main.activity_choose_payment.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException

class ChoosePaymentActivity : AppCompatActivity() {
    private var paymentMethods: ArrayList<User.PaymentMethod> = arrayListOf()
    private var recyclerView: RecyclerView? = null

    private var providerID: String? = null
    private var menuID: String? = null
    private var size: String? = null
    private var protein: String? = null
    private var sides: ArrayList<String>? = null
    private var notes: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_payment)

        setSupportActionBar(toolbar_order_payment)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        recyclerView = findViewById(R.id.recycler_view_choose_payment)

        providerID = intent.extras!!.getString(PROVIDER)
        menuID = intent.extras!!.getString(MENU)
        size = intent.extras!!.getString(SIZE)
        protein = intent.extras!!.getString(PROTEIN)
        sides = intent.extras!!.getStringArrayList(SIDES)
        notes = intent.extras!!.getString(NOTES)

        findPaymentMethod()
    }

    private fun updateUI(){
        try {
            doAsync {
                val adapter = PaymentAdapter(paymentMethods, providerID!!, menuID!!, size!!, protein!!, sides!!, notes!!)
                uiThread {
                    recyclerView!!.adapter = adapter
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun findPaymentMethod(){
        val usersRef = FirebaseDatabase.getInstance().reference.child("users")
        val query = usersRef.orderByKey().equalTo(providerID)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val user = snapshot.children.first().getValue(User::class.java)
                    if (user != null){
                        paymentMethods = user.paymentMethods
                    }
                }
                updateUI()
            }

        })
    }

    override fun onSupportNavigateUp():Boolean {
        onBackPressed()
        return true
    }
}

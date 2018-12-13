package com.ufpe.if710.quentinhas.order

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.ufpe.if710.quentinhas.R
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.MENU
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.NOTES
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.PAYMENT
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.PROTEIN
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.PROVIDER
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.SIDES
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.SIZE
import com.ufpe.if710.quentinhas.model.Order
import com.ufpe.if710.quentinhas.model.User
import kotlinx.android.synthetic.main.activity_send_order.*
import java.text.SimpleDateFormat
import java.util.*

class SendOrderActivity : AppCompatActivity() {
    private var providerID: String? = null
    private var menuID: String? = null
    private var size: String? = null
    private var protein: String? = null
    private var sides: ArrayList<String> = arrayListOf()
    private var notes: String? = null
    private var paymentMethod: String? = null

    private var mDatabase: DatabaseReference? = null
    private var usersRef: DatabaseReference? = null
    private var orderID: String? = null

    private var user: User? = null
    private var key: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_order)

        setSupportActionBar(toolbar_send_order)

        providerID = intent.extras!!.getString(PROVIDER)
        menuID = intent.extras!!.getString(MENU)
        size = intent.extras!!.getString(SIZE)
        protein = intent.extras!!.getString(PROTEIN)
        sides = intent.extras!!.getStringArrayList(SIDES)!!
        notes = intent.extras!!.getString(NOTES)
        paymentMethod = intent.extras!!.getString(PAYMENT)

        mDatabase = FirebaseDatabase.getInstance().reference
        usersRef = FirebaseDatabase.getInstance().reference.child("users")

        btn_send_order.setOnClickListener {
            saveOrder()
        }

        findUser()
    }

    private fun updateUI(){
        text_view_protein.text = protein
        text_view_sides.text = ""
        for (side in sides){
            text_view_sides.text = "${text_view_sides.text}\n$side"
        }
        text_view_size.text = size
        text_view_notes.text = notes
        text_view_payment_method.text = paymentMethod

        text_view_pickup_address.text = user!!.address
        text_view_pickup_time.text = user!!.pickupTime
    }

    private fun saveOrder(){
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        orderID = mDatabase!!.child("orders").push().key
        val clientID = FirebaseAuth.getInstance().currentUser!!.uid

        val order = Order(orderID, clientID, providerID, sdf.format(Date()), protein, sides, size, null, notes)

        mDatabase!!.child("orders").child(orderID!!).setValue(order).addOnCompleteListener {
            Toast.makeText(this, "Pedido enviado com sucesso!", Toast.LENGTH_LONG).show()
        }
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

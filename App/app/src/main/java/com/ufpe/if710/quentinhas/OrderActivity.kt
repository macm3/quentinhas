package com.ufpe.if710.quentinhas

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.ufpe.if710.quentinhas.OrderAdapter.Companion.ORDER_ID
import com.ufpe.if710.quentinhas.model.Order
import com.ufpe.if710.quentinhas.order.ShowMenuAdapter
import kotlinx.android.synthetic.main.activity_order.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException

class OrderActivity : AppCompatActivity() {
    private var orderID: String? = null
    private var order: Order? = null
    private var ordersRef: DatabaseReference? = null
    private var mDatabase: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        setSupportActionBar(toolbar_order)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        orderID = intent.getStringExtra(ORDER_ID)
        ordersRef = FirebaseDatabase.getInstance().reference.child("orders")
        mDatabase = FirebaseDatabase.getInstance().reference

        findOrder()
    }

    override fun onSupportNavigateUp():Boolean {
        onBackPressed()
        return true
    }

    private fun updateUI(){
        protein_order.text = order!!.protein
        if(FirebaseAuth.getInstance().currentUser!!.uid == order!!.providerID){
            btn_check_order.visibility = View.VISIBLE
            btn_check_order.isChecked = order!!.delivered!!
            btn_check_order.setOnClickListener {
                if (btn_check_order.isChecked){
                    order!!.delivered = true
                    updateOrder()
                } else {
                    order!!.delivered = false
                    updateOrder()
                }
            }
        } else {
            btn_check_order.visibility = View.INVISIBLE
        }

        try {
            doAsync {
                val adapterSides = ShowMenuAdapter(order!!.side)
                uiThread {
                    recycler_view_sides_order.adapter = adapterSides
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        size_order.text = order!!.size

    }

    private fun showPopUp() {
        alert("O status do pedido foi atualizado com sucesso!") {
            title ="Entrega"
            positiveButton("OK") {
                finish()
            }
        }.show()
    }

    private fun updateOrder(){
        mDatabase!!.child("orders").child(order!!.orderID!!).setValue(order).addOnCompleteListener {
            showPopUp()
        }
    }

    private fun findOrder(){
        val query = ordersRef!!.orderByKey().equalTo(orderID)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println(p0.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val data = snapshot.children.first()
                    order = data.getValue(Order::class.java)
                    updateUI()
                }
            }
        })
    }


}

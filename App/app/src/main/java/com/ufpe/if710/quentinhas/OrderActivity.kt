package com.ufpe.if710.quentinhas

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.google.firebase.database.*
import com.ufpe.if710.quentinhas.OrderAdapter.Companion.ORDER_ID
import com.ufpe.if710.quentinhas.model.Order
import kotlinx.android.synthetic.main.activity_order.*
import java.util.ArrayList

class OrderActivity : AppCompatActivity() {
    private var orderID: String? = null
    private var order: Order? = null
    private var ordersRef: DatabaseReference? = null

    private var parentLinearLayout: LinearLayout? = null
    private var listTextViewSide: ArrayList<TextView> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        setSupportActionBar(toolbar_order)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        orderID = intent.getStringExtra(ORDER_ID)
        ordersRef = FirebaseDatabase.getInstance().reference.child("orders")

        findOrder()
    }

    override fun onSupportNavigateUp():Boolean {
        onBackPressed()
        return true
    }

    private fun updateUI(){
        protein_order.text = order!!.protein

        for (side in order!!.side){
            parentLinearLayout = findViewById(R.id.linear_layout_side_order_list)
            listTextViewSide.add(createTextView())
        }

        size_order.text = order!!.size

        for (i in listTextViewSide.indices){
            listTextViewSide[i].text = order!!.side[i]
        }

    }

    private fun createTextView(): TextView {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.new_text_view, null)
        parentLinearLayout!!.addView(rowView, parentLinearLayout!!.childCount)
        return rowView.findViewById(R.id.text_view)
    }

    private fun findOrder(){
        val query = ordersRef!!.orderByKey().equalTo(orderID)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println(p0.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                listTextViewSide.clear()
                if(snapshot.exists()){
                    val data = snapshot.children.first()
                    order = data.getValue(Order::class.java)
                    updateUI()
                }
            }
        })
    }


}

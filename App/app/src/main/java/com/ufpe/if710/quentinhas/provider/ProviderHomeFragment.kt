package com.ufpe.if710.quentinhas.provider

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.ufpe.if710.quentinhas.OrderAdapter

import com.ufpe.if710.quentinhas.R
import com.ufpe.if710.quentinhas.model.Order
import com.ufpe.if710.quentinhas.model.User
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ProviderHomeFragment : Fragment() {
    private var mView: View? = null
    private var recyclerView: RecyclerView? = null

    private var ordersList: ArrayList<Order> = arrayListOf()

    private var providerID: String? = null
    private var usersRef: DatabaseReference? = null
    private var user: User? = null
    private var key: String? = null

    private var ordersRef: DatabaseReference? = null

    private var nameUserTextView: TextView? = null
    private var nameRestaurantTextView: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_provider_home, container, false)

        recyclerView = mView!!.findViewById(R.id.recycler_view_orders)
        nameUserTextView = mView!!.findViewById(R.id.title_welcome)
        nameRestaurantTextView = mView!!.findViewById(R.id.subtitle_welcome)

        providerID = FirebaseAuth.getInstance().currentUser!!.uid
        usersRef = FirebaseDatabase.getInstance().reference.child("users")
        ordersRef = FirebaseDatabase.getInstance().reference.child("orders")

        findUser()

        return mView
    }

    private fun updateUI(){
        nameUserTextView!!.text = "${resources.getString(R.string.welcome)} ${user!!.name}"
        nameRestaurantTextView!!.text = "${resources.getString(R.string.welcome_restaurant)} ${user!!.restaurant}"

        try {
            doAsync {
                val adapter = OrderAdapter(ordersList)
                uiThread {
                    recyclerView!!.adapter = adapter
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
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
                    findOrders()
                }
                //
           }
        })
    }

    private fun findOrders(){
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        var dateThen: Date
        val then = Calendar.getInstance()
        val now = Calendar.getInstance()
        val query = ordersRef!!.orderByChild("providerID").equalTo(providerID)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println(p0.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                ordersList.clear()
                if (snapshot.exists()){
                    for (i in snapshot.children){
                        val order = i.getValue(Order::class.java)
                        if(order != null){
                            dateThen = sdf.parse(order.date)
                            then.time = dateThen
                            if (then.get(Calendar.DATE) != now.get(Calendar.DATE)){
                                deleteOrder(order)
                            } else {
                                ordersList.add(order)
                                updateUI()
                            }
                        }
                    }
                }
            }
        })
    }

    private fun deleteOrder(order: Order){
        val mDatabase = FirebaseDatabase.getInstance().reference
        mDatabase.child("orders").child(order.orderID!!).removeValue()
    }

    companion object {
        fun newInstance(): ProviderHomeFragment = ProviderHomeFragment()
    }
}

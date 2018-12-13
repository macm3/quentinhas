package com.ufpe.if710.quentinhas.client

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.util.Log
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

class ClientHomeFragment : Fragment(){
    private var usersRef: DatabaseReference? = null
    private var user: User? = null
    private var key: String? = null
    private var clientID: String? = null
    private var ordersRef: DatabaseReference? = null

    private var ordersList: ArrayList<Order> = arrayListOf()
    private var recyclerView: RecyclerView? = null

    private var mView: View? = null
    private var title: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_client_home, container, false)
        recyclerView = mView!!.findViewById(R.id.recycler_view_my_orders)

        usersRef = FirebaseDatabase.getInstance().reference.child("users")
        ordersRef = FirebaseDatabase.getInstance().reference.child("orders")
        clientID = FirebaseAuth.getInstance().currentUser!!.uid

        title = mView!!.findViewById(R.id.title_welcome_client)

        findUser()

        return mView
    }

    private fun updateUI(){
        title!!.text = "${resources.getString(R.string.welcome)} ${user!!.name}"
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
        val query = usersRef!!.orderByKey().equalTo(clientID)

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
            }
        })
    }


    private fun findOrders(){
        val query = ordersRef!!.orderByChild("clientID").equalTo(clientID)

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
                            ordersList.add(order)
                            updateUI()
                        }
                    }
                }
                updateUI()
            }
        })
    }


    companion object {
        fun newInstance(): ClientHomeFragment = ClientHomeFragment()
    }
}
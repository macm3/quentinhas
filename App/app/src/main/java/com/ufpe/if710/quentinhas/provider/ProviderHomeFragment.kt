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

class ProviderHomeFragment : Fragment() {
    private var mView: View? = null
    private var recyclerView: RecyclerView? = null

    private var ordersList: ArrayList<Order> = arrayListOf()

    private var providerID: String? = null
    private var usersRef: DatabaseReference? = null
    private var user: User? = null
    private var key: String? = null

    private var nameUserTextView: TextView? = null
    private var nameRestaurantTextView: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_provider_home, container, false)

        recyclerView = mView!!.findViewById(R.id.recycler_view_orders)
        nameUserTextView = mView!!.findViewById(R.id.title_welcome)
        nameRestaurantTextView = mView!!.findViewById(R.id.subtitle_welcome)

        providerID = FirebaseAuth.getInstance().currentUser!!.uid
        usersRef = FirebaseDatabase.getInstance().reference.child("users")
        findUser()

        return mView
    }

    private fun updateUI(){
        nameUserTextView!!.text = "${nameUserTextView!!.text} ${user!!.name}"
        nameRestaurantTextView!!.text = "${nameRestaurantTextView!!.text} ${user!!.restaurant}"
        ordersList.add(Order("id", "ls1oih4YBbWLdsFENx6U4lilGuz1", "WSgK00WT3OYdhtzT8alCosLzcOD3",
            "Carne", arrayListOf("Arroz"), "Pequena / R$4,00"))
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
                    updateUI()
                }
                //
           }
        })
    }

    companion object {
        fun newInstance(): ProviderHomeFragment = ProviderHomeFragment()
    }
}

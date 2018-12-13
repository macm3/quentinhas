package com.ufpe.if710.quentinhas

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ufpe.if710.quentinhas.model.Order
import com.ufpe.if710.quentinhas.model.User

class OrderAdapter(private val items: List<Order>) : RecyclerView.Adapter<OrderAdapter.MyViewHolder>()  {
    private var client: User? = null
    private var provider: User? = null

    class MyViewHolder(val view: LinearLayout) : RecyclerView.ViewHolder(view){
        var title: TextView = view.findViewById(R.id.name_client_order)
        var subtitle: TextView = view.findViewById(R.id.size_order)
        var btn: Button = view.findViewById(R.id.btn_more_details_order)
        var check: TextView = view.findViewById(R.id.delivered_order)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val linearLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_order, parent, false)  as LinearLayout
        return MyViewHolder(linearLayout)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val context = holder.view.context
        retrieveUser(items[position].clientID!!, holder, position)
        if (items[position].delivered!!){
            holder.check.visibility = View.VISIBLE
        } else {
            holder.check.visibility = View.GONE
        }

        holder.btn.setOnClickListener {
            val intent = Intent(context, OrderActivity::class.java)
            intent.putExtra(ORDER_ID, items[position].orderID)
            context.startActivity(intent)
        }
    }

    private fun updateUIProvider(holder: MyViewHolder, position: Int){
        if (FirebaseAuth.getInstance().currentUser!!.uid != items[position].clientID){
            holder.title.text = client!!.name!!
            holder.subtitle.text = items[position].size
        }
    }

    private fun updateUIClient(holder: MyViewHolder, position: Int){
        if (FirebaseAuth.getInstance().currentUser!!.uid == items[position].clientID){
            holder.title.text = provider!!.restaurant!!
            holder.subtitle.text = items[position].date
        }
    }

    private fun retrieveUser(userID: String, holder: MyViewHolder, position: Int){
        val usersRef = FirebaseDatabase.getInstance().reference.child("users")
        var query = usersRef.orderByKey().equalTo(userID)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println(p0.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val data = snapshot.children.first()
                    client = data.getValue(User::class.java)
                    updateUIProvider(holder, position)
                }
            }
        })

        query = usersRef.orderByKey().equalTo(items[position].providerID)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println(p0.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val data = snapshot.children.first()
                    provider = data.getValue(User::class.java)
                    updateUIClient(holder, position)
                }
            }
        })

    }

    companion object {
        val ORDER_ID = "Order ID"
    }
}
package com.ufpe.if710.quentinhas

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ufpe.if710.quentinhas.model.Order
import com.ufpe.if710.quentinhas.model.User

class OrderAdapter(private val items: List<Order>) : RecyclerView.Adapter<OrderAdapter.MyViewHolder>()  {
    private var user: User? = null

    class MyViewHolder(val view: LinearLayout) : RecyclerView.ViewHolder(view){
        var name: TextView = view.findViewById(R.id.name_client_order)
        var size: TextView = view.findViewById(R.id.size_order)
        var btn: Button = view.findViewById(R.id.btn_more_details_order)
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
        retrieveUser(items[position].clientID!!, holder)

        holder.size.text = items[position].size

        holder.btn.setOnClickListener {
            val intent = Intent(context, OrderActivity::class.java)
            intent.putExtra(ORDER_ID, items[position].orderID)
            context.startActivity(intent)
        }
    }

    private fun updateUI(holder: MyViewHolder){
        holder.name.text = user!!.name!!
    }

    private fun retrieveUser(userID: String, holder: MyViewHolder){
        val usersRef = FirebaseDatabase.getInstance().reference.child("users")
        val query = usersRef.orderByKey().equalTo(userID)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println(p0.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val data = snapshot.children.first()
                    user = data.getValue(User::class.java)
                    updateUI(holder)
                }
            }
        })
    }


    companion object {
        val ORDER_ID = "Order ID"
    }
}
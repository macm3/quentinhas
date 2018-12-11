package com.ufpe.if710.quentinhas

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.ufpe.if710.quentinhas.R
import com.ufpe.if710.quentinhas.model.Menu

class OrderAdapter(private val items: List<Order>) : RecyclerView.Adapter<OrderAdapter.MyViewHolder>()  {
    class MyViewHolder(val view: LinearLayout) : RecyclerView.ViewHolder(view){
        var name: TextView = view.findViewById(R.id.name_client_order)
        var restaurant: TextView = view.findViewById(R.id.size_order)
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
        holder.name = items[position].clientName

        holder.btn.setOnClickListener {
            val intent = Intent(context, OrderActivity::class.java)
            intent.putExtra(ORDER_ID, items[position].orderID)
            context.startActivity(intent)
        }
    }

    companion object {
        val ORDER_ID = "Order ID"
    }
}
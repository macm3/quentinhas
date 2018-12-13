package com.ufpe.if710.quentinhas.order

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.ufpe.if710.quentinhas.R

class ShowMenuAdapter (private val items: List<String>) : RecyclerView.Adapter<ShowMenuAdapter.MyViewHolder>()  {

    class MyViewHolder(val view: LinearLayout) : RecyclerView.ViewHolder(view){
        var text: TextView = view.findViewById(R.id.text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val linearLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.new_text_view, parent, false)  as LinearLayout
        return MyViewHolder(linearLayout)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.text.text = items[position]
    }

}
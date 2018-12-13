package com.ufpe.if710.quentinhas.order

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.ufpe.if710.quentinhas.R
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.PROVIDER
import com.ufpe.if710.quentinhas.model.User
import com.ufpe.if710.quentinhas.provider.ProviderActivity

class ProviderAdapter (private val items: List<User>) : RecyclerView.Adapter<ProviderAdapter.MyViewHolder>()  {

    class MyViewHolder(val view: LinearLayout) : RecyclerView.ViewHolder(view){
        var name: TextView = view.findViewById(R.id.title_menu)
        var btn: Button = view.findViewById(R.id.btn_more_details_menu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val linearLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_menu, parent, false)  as LinearLayout
        return MyViewHolder(linearLayout)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val context = holder.view.context
        holder.name.text = items[position].restaurant!!

        holder.btn.setOnClickListener {
            val intent = Intent(context, ProviderActivity::class.java)
            intent.putExtra(PROVIDER, items[position].userID)
            context.startActivity(intent)
        }

    }

}
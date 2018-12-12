package com.ufpe.if710.quentinhas.order

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import com.ufpe.if710.quentinhas.R
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.PROVIDER
import com.ufpe.if710.quentinhas.model.User

class ProviderAdapter (private val items: List<User>) : RecyclerView.Adapter<ProviderAdapter.MyViewHolder>()  {

    class MyViewHolder(val view: LinearLayout) : RecyclerView.ViewHolder(view){
        var name: TextView = view.findViewById(R.id.provider_name)
        var checkBox: CheckBox = view.findViewById(R.id.checkbox_provider)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val linearLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_provider, parent, false)  as LinearLayout
        return MyViewHolder(linearLayout)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val context = holder.view.context
        holder.name.text = items[position].restaurant!!

        holder.checkBox.setOnClickListener {
            if (holder.checkBox.isChecked){
                val intent = Intent(context, ChooseMenuActivity::class.java)
                intent.putExtra(PROVIDER, items[position].userID)
                context.startActivity(intent)
            }
        }
    }

}
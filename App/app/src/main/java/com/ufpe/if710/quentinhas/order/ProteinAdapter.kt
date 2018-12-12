package com.ufpe.if710.quentinhas.order

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import com.ufpe.if710.quentinhas.R
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.MENU
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.PROTEIN
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.PROVIDER
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.SIZE

class ProteinAdapter (private val items: List<String>, private val providerID: String, private val menuID: String, private val size: String) : RecyclerView.Adapter<ProteinAdapter.MyViewHolder>()  {

    class MyViewHolder(val view: LinearLayout) : RecyclerView.ViewHolder(view){
        var protein: TextView = view.findViewById(R.id.item_name)
        var checkBox: CheckBox = view.findViewById(R.id.item_checkbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val linearLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)  as LinearLayout
        return MyViewHolder(linearLayout)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val context = holder.view.context
        holder.protein.text = items[position]

        holder.checkBox.setOnClickListener {
            if (holder.checkBox.isChecked){
                val intent = Intent(context, ChooseSidesActivity::class.java)
                intent.putExtra(PROVIDER, providerID)
                intent.putExtra(MENU, menuID)
                intent.putExtra(SIZE, size)
                intent.putExtra(PROTEIN, items[position])
                context.startActivity(intent)
            }
        }
    }

}
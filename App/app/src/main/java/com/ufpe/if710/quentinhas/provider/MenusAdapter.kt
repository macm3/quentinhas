package com.ufpe.if710.quentinhas.provider

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NO_HISTORY
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.ufpe.if710.quentinhas.R
import com.ufpe.if710.quentinhas.model.Menu

class MenusAdapter(private val items: List<Menu>) : RecyclerView.Adapter<MenusAdapter.MyViewHolder>()  {
    class MyViewHolder(val view: LinearLayout) : RecyclerView.ViewHolder(view){
        var title: TextView = view.findViewById(R.id.title_menu)
        var text: TextView = view.findViewById(R.id.text_menu)
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
        holder.title.text = items[position].title
        var proteins: String? = ""
        for (i in items[position].protein.indices){
            proteins = "$proteins\n${items[position].protein[i]}"
        }
        holder.text.text = proteins

        holder.btn.setOnClickListener {
            val intent = Intent(context, MenuActivity::class.java)
            intent.putExtra(MENU_ID, items[position].menuID)
            intent.flags = FLAG_ACTIVITY_NO_HISTORY
            context.startActivity(intent)
        }
    }

    companion object {
        val MENU_ID = "Menu ID"
    }
}
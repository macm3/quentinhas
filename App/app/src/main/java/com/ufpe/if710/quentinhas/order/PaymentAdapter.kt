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
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.NOTES
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.PAYMENT
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.PROTEIN
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.PROVIDER
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.SIDES
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.SIZE
import com.ufpe.if710.quentinhas.model.User

class PaymentAdapter (private val items: List<User.PaymentMethod>, private val providerID: String, private val menuID: String, private val size: String, private val protein: String, private val sides: ArrayList<String>, private val notes: String) : RecyclerView.Adapter<PaymentAdapter.MyViewHolder>()  {

    class MyViewHolder(val view: LinearLayout) : RecyclerView.ViewHolder(view){
        var paymentMethod: TextView = view.findViewById(R.id.item_name)
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
        when(items[position]){
            User.PaymentMethod.CASH -> {
                holder.paymentMethod.text = context.resources.getString(R.string.toggle_cash)
            }
            User.PaymentMethod.PAGSEGURO -> {
                holder.paymentMethod.text = context.resources.getString(R.string.toggle_pagseguro)
            }
        }

        holder.checkBox.setOnClickListener {
            if (holder.checkBox.isChecked){
                val intent = Intent(context, SendOrderActivity::class.java)
                intent.putExtra(PROVIDER, providerID)
                intent.putExtra(MENU, menuID)
                intent.putExtra(SIZE, size)
                intent.putExtra(PROTEIN, protein)
                intent.putExtra(SIDES, sides)
                intent.putExtra(NOTES, notes)
                intent.putExtra(PAYMENT, holder.paymentMethod.text.toString())
                context.startActivity(intent)
            }
        }
    }

}
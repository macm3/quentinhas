package com.ufpe.if710.quentinhas.provider

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.ufpe.if710.quentinhas.R
import com.ufpe.if710.quentinhas.model.Order
import kotlinx.android.synthetic.main.activity_charts.*
import java.text.SimpleDateFormat
import java.util.*

class ChartsActivity : AppCompatActivity() {
    private var providerID: String? = null
    private var ordersRef: DatabaseReference? = null

    //listas para chart 1
    private var ordersListSunday: ArrayList<Order> = arrayListOf()
    private var ordersListMonday: ArrayList<Order> = arrayListOf()
    private var ordersListTuesday: ArrayList<Order> = arrayListOf()
    private var ordersListWednesday: ArrayList<Order> = arrayListOf()
    private var ordersListThursday: ArrayList<Order> = arrayListOf()
    private var ordersListFriday: ArrayList<Order> = arrayListOf()
    private var ordersListSaturday: ArrayList<Order> = arrayListOf()

    //listas para chart 2
    private var deliveredListSunday: ArrayList<Order> = arrayListOf()
    private var deliveredListMonday: ArrayList<Order> = arrayListOf()
    private var deliveredListTuesday: ArrayList<Order> = arrayListOf()
    private var deliveredListWednesday: ArrayList<Order> = arrayListOf()
    private var deliveredListThursday: ArrayList<Order> = arrayListOf()
    private var deliveredListFriday: ArrayList<Order> = arrayListOf()
    private var deliveredListSaturday: ArrayList<Order> = arrayListOf()

    //listas para chart 3
    private var wastedListSunday: ArrayList<Order> = arrayListOf()
    private var wastedListMonday: ArrayList<Order> = arrayListOf()
    private var wastedListTuesday: ArrayList<Order> = arrayListOf()
    private var wastedListWednesday: ArrayList<Order> = arrayListOf()
    private var wastedListThursday: ArrayList<Order> = arrayListOf()
    private var wastedListFriday: ArrayList<Order> = arrayListOf()
    private var wastedListSaturday: ArrayList<Order> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charts)

        providerID = FirebaseAuth.getInstance().currentUser!!.uid
        ordersRef = FirebaseDatabase.getInstance().reference.child("orders")

        go_chart_orders.setOnClickListener {
            findOrders(1)
        }

        go_chart_delivered.setOnClickListener {
            findOrders(2)
        }

        go_chart_wasted.setOnClickListener {
            findOrders(3)
        }
    }

    private fun numbersOrders(){
        val intent = Intent(this, ChartOrdersActivity::class.java)
        intent.putExtra(SUNDAY, ordersListSunday.size)
        intent.putExtra(MONDAY, ordersListMonday.size)
        intent.putExtra(TUESDAY, ordersListTuesday.size)
        intent.putExtra(WEDNESDAY, ordersListWednesday.size)
        intent.putExtra(THURSDAY, ordersListThursday.size)
        intent.putExtra(FRIDAY, ordersListFriday.size)
        intent.putExtra(SATURDAY, ordersListSaturday.size)
        intent.putExtra(TITLE, resources.getString(R.string.chart_orders))
        startActivity(intent)
    }

    private fun numbersDelivered(){
        val intent = Intent(this, ChartOrdersActivity::class.java)
        intent.putExtra(SUNDAY, deliveredListSunday.size)
        intent.putExtra(MONDAY, deliveredListMonday.size)
        intent.putExtra(TUESDAY, deliveredListTuesday.size)
        intent.putExtra(WEDNESDAY, deliveredListWednesday.size)
        intent.putExtra(THURSDAY, deliveredListThursday.size)
        intent.putExtra(FRIDAY, deliveredListFriday.size)
        intent.putExtra(SATURDAY, deliveredListSaturday.size)
        intent.putExtra(TITLE, resources.getString(R.string.chart_delivered))
        startActivity(intent)
    }

    private fun numbersWasted(){
        val intent = Intent(this, ChartOrdersActivity::class.java)
        intent.putExtra(SUNDAY, wastedListSunday.size)
        intent.putExtra(MONDAY, wastedListMonday.size)
        intent.putExtra(TUESDAY, wastedListTuesday.size)
        intent.putExtra(WEDNESDAY, wastedListWednesday.size)
        intent.putExtra(THURSDAY, wastedListThursday.size)
        intent.putExtra(FRIDAY, wastedListFriday.size)
        intent.putExtra(SATURDAY, wastedListSaturday.size)
        intent.putExtra(TITLE, resources.getString(R.string.chart_wasted))
        startActivity(intent)
    }

    private fun findOrders(n: Int){
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        var dateThen: Date
        val then = Calendar.getInstance()
        val query = ordersRef!!.orderByChild("providerID").equalTo(providerID)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println(p0.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                clear()
                if (snapshot.exists()){
                    for (i in snapshot.children){
                        val order = i.getValue(Order::class.java)
                        if(order != null){
                            dateThen = sdf.parse(order.date)
                            then.time = dateThen
                            if (order.delivered!!){
                                addDateDelivered(then, order)
                            } else {
                                addDateWasted(then, order)
                            }
                            addDateOrders(then, order)
                        }
                    }
                    when(n){
                        1 -> {
                            numbersOrders()
                        }
                        2 -> {
                            numbersDelivered()
                        }
                        3 -> {
                            numbersWasted()
                        }
                        else -> {}
                    }
                }
            }
        })
    }

    private fun clear(){
        ordersListSunday.clear()
        ordersListMonday.clear()
        ordersListTuesday.clear()
        ordersListWednesday.clear()
        ordersListThursday.clear()
        ordersListFriday.clear()
        ordersListSaturday.clear()
        deliveredListSunday.clear()
        deliveredListMonday.clear()
        deliveredListTuesday.clear()
        deliveredListWednesday.clear()
        deliveredListThursday.clear()
        deliveredListFriday.clear()
        deliveredListSaturday.clear()
        wastedListSunday.clear()
        wastedListMonday.clear()
        wastedListTuesday.clear()
        wastedListWednesday.clear()
        wastedListThursday.clear()
        wastedListFriday.clear()
        wastedListSaturday.clear()
    }

    private fun addDateOrders(c: Calendar, order: Order){
        when(c.get(Calendar.DAY_OF_WEEK)){
            Calendar.SUNDAY -> {
                ordersListSunday.add(order)
            }
            Calendar.MONDAY -> {
                ordersListMonday.add(order)
            }
            Calendar.TUESDAY -> {
                ordersListTuesday.add(order)
            }
            Calendar.WEDNESDAY -> {
                ordersListWednesday.add(order)
            }
            Calendar.THURSDAY -> {
                ordersListThursday.add(order)
            }
            Calendar.FRIDAY -> {
                ordersListFriday.add(order)
            }
            Calendar.SATURDAY -> {
                ordersListSaturday.add(order)
            }
        }
    }

    private fun addDateDelivered(c: Calendar, order: Order){
        when(c.get(Calendar.DAY_OF_WEEK)){
            Calendar.SUNDAY -> {
                deliveredListSunday.add(order)
            }
            Calendar.MONDAY -> {
                deliveredListMonday.add(order)
            }
            Calendar.TUESDAY -> {
                deliveredListTuesday.add(order)
            }
            Calendar.WEDNESDAY -> {
                deliveredListWednesday.add(order)
            }
            Calendar.THURSDAY -> {
                deliveredListThursday.add(order)
            }
            Calendar.FRIDAY -> {
                deliveredListFriday.add(order)
            }
            Calendar.SATURDAY -> {
                deliveredListSaturday.add(order)
            }
        }
    }

    private fun addDateWasted(c: Calendar, order: Order){
        when(c.get(Calendar.DAY_OF_WEEK)){
            Calendar.SUNDAY -> {
                wastedListSunday.add(order)
            }
            Calendar.MONDAY -> {
                wastedListMonday.add(order)
            }
            Calendar.TUESDAY -> {
                wastedListTuesday.add(order)
            }
            Calendar.WEDNESDAY -> {
                wastedListWednesday.add(order)
            }
            Calendar.THURSDAY -> {
                wastedListThursday.add(order)
            }
            Calendar.FRIDAY -> {
                wastedListFriday.add(order)
            }
            Calendar.SATURDAY -> {
                wastedListSaturday.add(order)
            }
        }
    }

    companion object {
        val SUNDAY = "Domingo"
        val MONDAY = "Segunda"
        val TUESDAY = "Terça"
        val WEDNESDAY = "Quarta"
        val THURSDAY = "Quinta"
        val FRIDAY = "Sexta"
        val SATURDAY = "Sábado"
        val TITLE = "Título"
    }
}

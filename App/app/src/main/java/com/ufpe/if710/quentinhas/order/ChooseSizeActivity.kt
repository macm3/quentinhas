package com.ufpe.if710.quentinhas.order

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ufpe.if710.quentinhas.R
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.MENU
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.PROVIDER
import com.ufpe.if710.quentinhas.model.Menu
import kotlinx.android.synthetic.main.activity_choose_size.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException

class ChooseSizeActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var sizeList: ArrayList<String> = arrayListOf()

    private var providerID: String? = null
    private var menuID: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_size)

        setSupportActionBar(toolbar_order_size)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        recyclerView = findViewById(R.id.recycler_view_choose_size)
        providerID = intent.extras!!.getString(PROVIDER)
        menuID = intent.extras!!.getString(MENU)

        findMenu()
    }

    private fun updateUI(){
        try {
            doAsync {
                val adapter = SizeAdapter(sizeList, providerID!!, menuID!!)
                uiThread {
                    recyclerView!!.adapter = adapter
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun findMenu(){
        val menusRef = FirebaseDatabase.getInstance().reference.child("menus")
        val query = menusRef.orderByKey().equalTo(menuID)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                sizeList.clear()
                if (snapshot.exists()){
                    val menu = snapshot.children.first().getValue(Menu::class.java)
                    if (menu != null){
                        sizeList = menu.size
                    }
                }
                updateUI()
            }

        })
    }
}

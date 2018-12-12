package com.ufpe.if710.quentinhas.order

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ufpe.if710.quentinhas.OrderAdapter
import com.ufpe.if710.quentinhas.R
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.PROVIDER
import com.ufpe.if710.quentinhas.model.Menu
import com.ufpe.if710.quentinhas.model.User
import com.ufpe.if710.quentinhas.provider.MenusAdapter
import kotlinx.android.synthetic.main.activity_order.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException

class ChooseMenuActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var menusList: ArrayList<Menu> = arrayListOf()

    private var providerID: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_menu)

        setSupportActionBar(toolbar_order)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        recyclerView = findViewById(R.id.recycler_view_menus)
        providerID = intent.extras!!.getString(PROVIDER)

        findMenus()
    }

    private fun updateUI(){
        try {
            doAsync {
                val adapter = MenusAdapter(menusList)
                uiThread {
                    recyclerView!!.adapter = adapter
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun findMenus(){
        val menusRef = FirebaseDatabase.getInstance().reference.child("menus")
        val query = menusRef.orderByKey()

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot.children
                children.forEach{
                    val menu = it.getValue(Menu::class.java)
                    if (menu != null && menu.providerID == providerID){
                        menusList.add(menu)
                    }
                }
                updateUI()
            }

        })
    }

    override fun onSupportNavigateUp():Boolean {
        onBackPressed()
        return true
    }

}
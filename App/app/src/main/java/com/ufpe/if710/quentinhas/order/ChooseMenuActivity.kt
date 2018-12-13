package com.ufpe.if710.quentinhas.order

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ufpe.if710.quentinhas.R
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.PROVIDER
import com.ufpe.if710.quentinhas.model.Menu
import com.ufpe.if710.quentinhas.provider.MenusAdapter
import kotlinx.android.synthetic.main.activity_choose_menu.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ChooseMenuActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var menusList: ArrayList<Menu> = arrayListOf()

    private var providerID: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_menu)

        setSupportActionBar(toolbar_order_menu)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        recyclerView = findViewById(R.id.recycler_view_menus)
        providerID = intent.extras!!.getString(PROVIDER)

        findMenus()
    }

    private fun updateUI(){
        if (menusList.isEmpty()){
            no_menu.visibility = View.VISIBLE
        } else {
            no_menu.visibility = View.INVISIBLE
        }
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
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        var dateThen: Date
        val then = Calendar.getInstance()
        val now = Calendar.getInstance()

        val menusRef = FirebaseDatabase.getInstance().reference.child("menus")
        val query = menusRef.orderByKey()

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val children = snapshot.children
                    children.forEach{
                        val menu = it.getValue(Menu::class.java)
                        if (menu != null && menu.providerID == providerID){
                            if (menu.day != null){
                                dateThen = sdf.parse(menu.day)
                                then.time = dateThen
                                if (then.get(Calendar.DATE) == now.get(Calendar.DATE)){
                                    menusList.add(menu)
                                }
                            }
                        }
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

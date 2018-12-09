package com.ufpe.if710.quentinhas.provider

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ufpe.if710.quentinhas.R
import com.ufpe.if710.quentinhas.model.Menu
import com.ufpe.if710.quentinhas.provider.MenusAdapter.Companion.MENU_ID
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {
    private var menuID: String? = null
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        setSupportActionBar(toolbar_menu)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        menuID = intent.extras!!.getString(MENU_ID)
        findMenu()
    }

    private fun updateUI(){
        toolbar_menu_title.text = menu!!.title

    }

    private fun findMenu(){
        val menusRef = FirebaseDatabase.getInstance().reference.child("menus")
        val query = menusRef.orderByKey().equalTo(menuID)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.children.first()
                menu = data.getValue(Menu::class.java)
                updateUI()
            }
        })
    }
}

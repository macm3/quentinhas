package com.ufpe.if710.quentinhas.provider

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.ufpe.if710.quentinhas.R
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.MENU
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.PROVIDER
import com.ufpe.if710.quentinhas.model.Menu
import com.ufpe.if710.quentinhas.order.ChooseSizeActivity
import com.ufpe.if710.quentinhas.order.ShowMenuAdapter
import com.ufpe.if710.quentinhas.provider.MenusAdapter.Companion.MENU_ID
import kotlinx.android.synthetic.main.activity_menu.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MenuActivity : AppCompatActivity() {
    private var menuID: String? = null
    private var menu: Menu? = null
    private var menusRef: DatabaseReference? = null

    private var calendar = Calendar.getInstance()
    private var dateSetListener: DatePickerDialog.OnDateSetListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        setSupportActionBar(toolbar_menu)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        menusRef = FirebaseDatabase.getInstance().reference.child("menus")

        menuID = intent.extras!!.getString(MENU_ID)
        findMenu()

        btn_schedule_menu.setOnClickListener {
            showDatePickerDialog()
        }

        dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateMenu()
        }

        btn_edit_menu.setOnClickListener {
            val intent = Intent(this, EditMenuActivity::class.java)
            intent.putExtra(MENU_ID, menuID)
            startActivity(intent)
        }

        btn_choose_menu.setOnClickListener {
            val intent = Intent(this, ChooseSizeActivity::class.java)
            intent.putExtra(PROVIDER, menu!!.providerID)
            intent.putExtra(MENU, menu!!.menuID)
            startActivity(intent)
        }

    }

    override fun onSupportNavigateUp():Boolean {
        onBackPressed()
        return true
    }

    private fun showDatePickerDialog(){
        DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT,
            dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun showPopUp(date: String) {
        alert("Seu cardápio foi agendado para $date") {
            title ="Agendamento"
            positiveButton("OK") {
                finish()
            }
        }.show()
    }

    private fun updateMenu(){
        val myFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        val date = sdf.format(calendar.time)
        menu!!.day = date
        menusRef!!.child(menu!!.menuID!!).setValue(menu).addOnCompleteListener {
            showPopUp(date)
        }
    }

    private fun updateUI(){
        toolbar_menu_title.text = menu!!.title
        if (menu!!.providerID == FirebaseAuth.getInstance().uid){
            menu_provider.visibility = View.VISIBLE
            menu_client.visibility = View.GONE
        } else {
            menu_provider.visibility = View.GONE
            menu_client.visibility = View.VISIBLE
        }

        try {
            doAsync {
                val adapterProtein = ShowMenuAdapter(menu!!.protein)
                val adapterSides = ShowMenuAdapter(menu!!.side)
                val adapterSizes = ShowMenuAdapter(menu!!.size)
                uiThread {
                    recycler_view_proteins_menu.adapter = adapterProtein
                    recycler_view_sides_menu.adapter = adapterSides
                    recycler_view_sizes_menu.adapter = adapterSizes
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
                if(snapshot.exists()){
                    val data = snapshot.children.first()
                    menu = data.getValue(Menu::class.java)
                    updateUI()
                }
            }
        })
    }
}

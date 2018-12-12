package com.ufpe.if710.quentinhas.order

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ufpe.if710.quentinhas.R
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.MENU
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.PROTEIN
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.PROVIDER
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.SIZE
import com.ufpe.if710.quentinhas.model.Menu
import kotlinx.android.synthetic.main.activity_choose_sides.*

class ChooseSidesActivity : AppCompatActivity() {
    private var sidesList: ArrayList<String> = arrayListOf()

    private var providerID: String? = null
    private var menuID: String? = null
    private var size: String? = null
    private var protein: String? = null

    private var parentLinearLayout: LinearLayout? = null
    private var checkBoxList: ArrayList<CheckBox> = arrayListOf()
    private var sides: ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_sides)

        setSupportActionBar(toolbar_order_sides)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        parentLinearLayout = findViewById(R.id.ll_order_sides)

        providerID = intent.extras!!.getString(PROVIDER)
        menuID = intent.extras!!.getString(MENU)
        size = intent.extras!!.getString(SIZE)
        protein = intent.extras!!.getString(PROTEIN)

        findMenu()
    }

    private fun updateUI(){
        for (side in sidesList){
            createView().text = side
        }

        for (i in checkBoxList.indices){
            checkBoxList[i].setOnClickListener {
                if (checkBoxList[i].isChecked){
                    sides.add(sidesList[i])
                    Log.d("xablau add", sides.toString())
                } else {
                    sides.remove(sidesList[i])
                    Log.d("xablau remove", sides.toString())
                }
            }
        }
    }

    private fun createView(): TextView {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.new_order_field, null)
        parentLinearLayout!!.addView(rowView, parentLinearLayout!!.childCount - 1)
        val checkBox = rowView.findViewById<CheckBox>(R.id.checkbox_order)
        checkBoxList.add(checkBox)
        return rowView.findViewById(R.id.text_view_order)
    }

    private fun findMenu(){
        val menusRef = FirebaseDatabase.getInstance().reference.child("menus")
        val query = menusRef.orderByKey().equalTo(menuID)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                sidesList.clear()
                if (snapshot.exists()){
                    val menu = snapshot.children.first().getValue(Menu::class.java)
                    if (menu != null){
                        sidesList = menu.side
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

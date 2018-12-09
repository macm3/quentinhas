package com.ufpe.if710.quentinhas.provider

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.LinearLayout
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

    private var listProtein: ArrayList<String> = arrayListOf()
    private var listSide: ArrayList<String> = arrayListOf()
    private var listSize: ArrayList<String> = arrayListOf()

    private var parentLinearLayout: LinearLayout? = null
    private var listTextViewProtein: ArrayList<TextView> = arrayListOf()
    private var listTextViewSide: ArrayList<TextView> = arrayListOf()
    private var listTextViewSize: ArrayList<TextView> = arrayListOf()

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

        for (protein in menu!!.protein){
            parentLinearLayout = findViewById(R.id.linear_layout_protein_list)
            listTextViewProtein.add(createTextView())
        }

        for (side in menu!!.side){
            parentLinearLayout = findViewById(R.id.linear_layout_side_list)
            listTextViewSide.add(createTextView())
        }

        for (size in menu!!.size){
            parentLinearLayout = findViewById(R.id.linear_layout_size_list)
            listTextViewSize.add(createTextView())
        }

        for (i in listTextViewProtein.indices){
            listTextViewProtein[i].text = menu!!.protein[i]
        }

        for (i in listTextViewSide.indices){
            listTextViewSide[i].text = menu!!.side[i]
        }

        for (i in listTextViewSize.indices){
            listTextViewSize[i].text = menu!!.size[i]
        }
    }

    private fun createTextView(): TextView{
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.new_text_view, null)
        parentLinearLayout!!.addView(rowView, parentLinearLayout!!.childCount)
        return rowView.findViewById(R.id.text_view)
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

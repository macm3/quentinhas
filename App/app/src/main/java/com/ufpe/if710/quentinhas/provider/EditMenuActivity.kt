package com.ufpe.if710.quentinhas.provider

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.EditText
import com.google.firebase.database.*
import com.ufpe.if710.quentinhas.R
import com.ufpe.if710.quentinhas.model.Menu
import kotlinx.android.synthetic.main.activity_edit_menu.*
import kotlinx.android.synthetic.main.activity_menu.*
import java.util.ArrayList

class EditMenuActivity : AppCompatActivity() {
    private var menuID: String? = null
    private var menu: Menu? = null
    private var menusRef: DatabaseReference? = null

    private var parentLinearLayout: LinearLayout? = null
    private var listEditTextProtein: ArrayList<EditText> = arrayListOf()
    private var listEditTextSide: ArrayList<EditText> = arrayListOf()
    private var listEditTextSize: ArrayList<EditText> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_menu)

        setSupportActionBar(toolbar_edit_menu)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        menusRef = FirebaseDatabase.getInstance().reference.child("menus")

        menuID = intent.extras!!.getString(MenusAdapter.MENU_ID)
        findMenu()

        btn_field_edit_protein.setOnClickListener {
            parentLinearLayout = findViewById(R.id.linear_layout_edit_protein)
            listEditTextProtein.add(createEditText())
        }

        btn_field_edit_side.setOnClickListener {
            parentLinearLayout = findViewById(R.id.linear_layout_edit_side)
            listEditTextSide.add(createEditText())
        }

        btn_field_edit_size.setOnClickListener {
            parentLinearLayout = findViewById(R.id.linear_layout_edit_size)
            listEditTextSize.add(createEditText())
        }

    }

    override fun onSupportNavigateUp():Boolean {
        onBackPressed()
        return true
    }

    private fun updateUI(){
        title_edit_menu.setText(menu!!.title!!.substringAfter("Cardápio "))

        for (protein in menu!!.protein){
            parentLinearLayout = findViewById(R.id.linear_layout_edit_protein)
            listEditTextProtein.add(createEditText())
        }

        for (side in menu!!.side){
            parentLinearLayout = findViewById(R.id.linear_layout_edit_side)
            listEditTextSide.add(createEditText())
        }

        for (size in menu!!.size){
            parentLinearLayout = findViewById(R.id.linear_layout_edit_size)
            listEditTextSize.add(createEditText())
        }

        for (i in listEditTextProtein.indices){
            listEditTextProtein[i].setText(menu!!.protein[i])
        }

        for (i in listEditTextSide.indices){
            listEditTextSide[i].setText(menu!!.side[i])
        }

        for (i in listEditTextSize.indices){
            listEditTextSize[i].setText(menu!!.size[i])
        }
    }

    private fun createEditText(): EditText {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.new_field, null)
        parentLinearLayout!!.addView(rowView, parentLinearLayout!!.childCount - 1)
        return rowView.findViewById(R.id.edit_text)
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

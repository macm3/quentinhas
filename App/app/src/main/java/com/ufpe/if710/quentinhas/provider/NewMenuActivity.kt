package com.ufpe.if710.quentinhas.provider

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ufpe.if710.quentinhas.R
import kotlinx.android.synthetic.main.activity_new_menu.*
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.ufpe.if710.quentinhas.model.Menu
import com.ufpe.if710.quentinhas.model.User

class NewMenuActivity : AppCompatActivity() {
    private var parentLinearLayout: LinearLayout? = null
    private var listEditTextProtein: ArrayList<EditText> = arrayListOf()
    private var listEditTextSide: ArrayList<EditText> = arrayListOf()
    private var listEditTextSize: ArrayList<EditText> = arrayListOf()

    private var providerID: String? = null
    private var mDatabase: DatabaseReference? = null
    private var usersRef: DatabaseReference? = null
    private var user: User? = null
    private var key: String? = null

    private var menuID: String? = null

    private var listProtein: ArrayList<String> = arrayListOf()
    private var listSide: ArrayList<String> = arrayListOf()
    private var listSize: ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_menu)

        setSupportActionBar(toolbar_new_menu)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        mDatabase = FirebaseDatabase.getInstance().reference
        providerID = FirebaseAuth.getInstance().currentUser!!.uid
        usersRef = FirebaseDatabase.getInstance().reference.child("users")

        btn_field_protein.setOnClickListener {
            parentLinearLayout = findViewById(R.id.linear_layout_protein)
            listEditTextProtein.add(onAddField())
        }

        btn_field_side.setOnClickListener {
            parentLinearLayout = findViewById(R.id.linear_layout_side)
            listEditTextSide.add(onAddField())
        }

        btn_field_size.setOnClickListener {
            parentLinearLayout = findViewById(R.id.linear_layout_size)
            listEditTextSize.add(onAddField())
        }

        btn_save_new_menu.setOnClickListener {
            createLists()
            findUser()
        }
    }

    private fun onAddField(): EditText {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val rowView = inflater.inflate(R.layout.new_field, null)
        parentLinearLayout!!.addView(rowView, parentLinearLayout!!.childCount - 1)
        return rowView.findViewById(R.id.edit_text)
    }

    fun onDelete(v: View){
        parentLinearLayout = v.parent.parent as LinearLayout
        val childLayout = v.parent as RelativeLayout
        val editText = (v.parent as RelativeLayout).findViewById<EditText>(R.id.edit_text)
        if (listEditTextProtein.contains(editText)) {
            listEditTextProtein.remove(editText)
        }
        if (listEditTextSide.contains(editText)) {
            listEditTextSide.remove(editText)
        }
        if (listEditTextSize.contains(editText)) {
            listEditTextSize.remove(editText)
        }
        parentLinearLayout!!.removeView(childLayout)
    }

    private fun createLists(){
        for (protein in listEditTextProtein){
            listProtein.add(protein.text.toString())
        }

        for (side in listEditTextSide){
            listSide.add(side.text.toString())
        }

        for (size in listEditTextSize){
            listSize.add(size.text.toString())
        }
    }

    private fun saveMenu(){
        menuID = mDatabase!!.child("menus").push().key
        val menu = Menu(menuID, providerID, "Cardápio ${title_new_menu.text}", listProtein, listSide, listSize, null)

        mDatabase!!.child("menus").child(menuID!!).setValue(menu).addOnCompleteListener {
            updateUser()
        }
    }

    private fun updateUser(){
        user!!.menus.add(menuID!!)
        usersRef!!.child(key!!).setValue(user).addOnCompleteListener {
            Toast.makeText(applicationContext, "Menu salvo!", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun findUser(){
        val query = usersRef!!.orderByKey().equalTo(providerID)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println(p0.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val data = snapshot.children.first()
                    user = data.getValue(User::class.java)
                    key = data.key
                    saveMenu()
                }
            }
        })
    }

    override fun onSupportNavigateUp():Boolean {
        onBackPressed()
        return true
    }
}

package com.ufpe.if710.quentinhas.provider

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.EditText
import android.widget.RelativeLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.ufpe.if710.quentinhas.R
import com.ufpe.if710.quentinhas.model.Menu
import com.ufpe.if710.quentinhas.model.User
import kotlinx.android.synthetic.main.activity_edit_menu.*
import org.jetbrains.anko.alert
import java.util.ArrayList

class EditMenuActivity : AppCompatActivity() {
    private var menuID: String? = null
    private var menu: Menu? = null
    private var menusRef: DatabaseReference? = null
    private var mDatabase: DatabaseReference? = null
    private var providerID: String? = null
    private var usersRef: DatabaseReference? = null
    private var user: User? = null
    private var key: String? = null

    private var parentLinearLayout: LinearLayout? = null
    private var listEditTextProtein: ArrayList<EditText> = arrayListOf()
    private var listEditTextSide: ArrayList<EditText> = arrayListOf()
    private var listEditTextSize: ArrayList<EditText> = arrayListOf()

    private var listProtein: ArrayList<String> = arrayListOf()
    private var listSide: ArrayList<String> = arrayListOf()
    private var listSize: ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_menu)

        setSupportActionBar(toolbar_edit_menu)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        menusRef = FirebaseDatabase.getInstance().reference.child("menus")
        mDatabase = FirebaseDatabase.getInstance().reference
        providerID = FirebaseAuth.getInstance().currentUser!!.uid
        usersRef = FirebaseDatabase.getInstance().reference.child("users")

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

        btn_save_edit_menu.setOnClickListener {
            createLists()
            findUser()
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
        menu!!.title = "Cardápio ${title_edit_menu.text}"
        menu!!.protein = listProtein
        menu!!.side = listSide
        menu!!.size = listSize

        mDatabase!!.child("menus").child(menuID!!).setValue(menu).addOnCompleteListener {
            showPopUp()
        }
    }

    private fun showPopUp() {
        alert("Seu cardápio foi editado com sucesso") {
            title ="Edição"
            positiveButton("OK") {
                val intent = Intent(applicationContext, MenuActivity::class.java)
                intent.putExtra(MenusAdapter.MENU_ID, menuID)
                intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                startActivity(intent)
                finish()
            }
        }.show()
    }

    private fun findUser() {
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

package com.ufpe.if710.quentinhas.provider

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.LinearLayout
import android.widget.Toast
import com.google.firebase.database.*
import com.ufpe.if710.quentinhas.R
import com.ufpe.if710.quentinhas.model.Menu
import com.ufpe.if710.quentinhas.provider.MenusAdapter.Companion.MENU_ID
import kotlinx.android.synthetic.main.activity_menu.*
import java.text.SimpleDateFormat
import java.util.*

class MenuActivity : AppCompatActivity() {
    private var menuID: String? = null
    private var menu: Menu? = null
    private var mDatabase: DatabaseReference? = null
    private var menusRef: DatabaseReference? = null

    private var listProtein: ArrayList<String> = arrayListOf()
    private var listSide: ArrayList<String> = arrayListOf()
    private var listSize: ArrayList<String> = arrayListOf()

    private var parentLinearLayout: LinearLayout? = null
    private var listTextViewProtein: ArrayList<TextView> = arrayListOf()
    private var listTextViewSide: ArrayList<TextView> = arrayListOf()
    private var listTextViewSize: ArrayList<TextView> = arrayListOf()

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
    }

    private fun showDatePickerDialog(){
        DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT,
            dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun showPopUp(date: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Agendamento")
        builder.setMessage("Seu cardápio foi agendado para $date")
        builder.setPositiveButton("OK"){_, _ ->
            finish()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
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

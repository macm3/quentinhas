package com.ufpe.if710.quentinhas.provider

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ufpe.if710.quentinhas.R
import kotlinx.android.synthetic.main.activity_new_menu.*
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.LinearLayout





class NewMenuActivity : AppCompatActivity() {
    private var parentLinearLayout: LinearLayout? = null
    private var listEditTextProtein: ArrayList<EditText> = arrayListOf()
    private var listEditTextSide: ArrayList<EditText> = arrayListOf()
    private var listEditTextSize: ArrayList<EditText> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_menu)

        setSupportActionBar(toolbar_new_menu)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

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

    }

    private fun onAddField(): EditText {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.new_field, null)
        // Add the new row before the add field button.
        parentLinearLayout!!.addView(rowView, parentLinearLayout!!.childCount - 1)
        return rowView.findViewById(R.id.edit_text)
    }

    override fun onSupportNavigateUp():Boolean {
        onBackPressed()
        return true
    }
}

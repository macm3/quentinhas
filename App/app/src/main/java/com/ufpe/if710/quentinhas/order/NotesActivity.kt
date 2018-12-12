package com.ufpe.if710.quentinhas.order

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ufpe.if710.quentinhas.R
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.MENU
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.NOTES
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.PROTEIN
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.PROVIDER
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.SIDES
import com.ufpe.if710.quentinhas.client.ClientOrderFragment.Companion.SIZE
import kotlinx.android.synthetic.main.activity_notes.*

class NotesActivity : AppCompatActivity() {
    private var notes: String? = null

    private var providerID: String? = null
    private var menuID: String? = null
    private var size: String? = null
    private var protein: String? = null
    private var sides: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        providerID = intent.extras!!.getString(PROVIDER)
        menuID = intent.extras!!.getString(MENU)
        size = intent.extras!!.getString(SIZE)
        protein = intent.extras!!.getString(PROTEIN)
        sides = intent.extras!!.getStringArrayList(SIDES)

        btn_save_notes.setOnClickListener {
            notes = edit_notes.text.toString()
            val intent = Intent(this, ChoosePaymentActivity::class.java)
            intent.putExtra(PROVIDER, providerID)
            intent.putExtra(MENU, menuID)
            intent.putExtra(SIZE, size)
            intent.putExtra(PROTEIN, protein)
            intent.putExtra(SIDES, sides)
            intent.putExtra(NOTES, notes)
            startActivity(intent)
        }
    }
}

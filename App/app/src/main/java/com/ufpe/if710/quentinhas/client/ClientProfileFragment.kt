package com.ufpe.if710.quentinhas.client

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.ufpe.if710.quentinhas.R
import com.ufpe.if710.quentinhas.model.User

class ClientProfileFragment : Fragment() {
    private val handle = Handler()

    private var mAuth: FirebaseAuth? = null
    private var currentUser: FirebaseUser? = null
    private var usersRef: DatabaseReference? = null

    private var user: User? = null
    private var key: String? = null

    private var mView: View? = null
    private var nameField: EditText? = null
    private var emailField: EditText? = null
    private var phoneField: EditText? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_client_profile, container, false)

        nameField = mView!!.findViewById(R.id.profile_client_name)
        emailField = mView!!.findViewById(R.id.profile_client_email)
        phoneField = mView!!.findViewById(R.id.profile_client_phone)

        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth!!.currentUser
        usersRef = FirebaseDatabase.getInstance().reference.child("users")

        findUser()

        val card = mView!!.findViewById<CardView>(R.id.cardView_profile_client)
        val btn = mView!!.findViewById<Button>(R.id.btn_save_profile_client)

        btn.setOnClickListener {
            saveInfo()
        }

        card.setOnClickListener {
            card.setCardBackgroundColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
            handle.postDelayed({card.setCardBackgroundColor(ContextCompat.getColor(context!!, R.color.colorWhite)) }, 500)
            sendEmail()
        }

        return mView
    }

    private fun findUser(){
        val userID = currentUser!!.uid
        val query = usersRef!!.orderByKey().equalTo(userID)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println(p0.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val data = snapshot.children.first()
                    user = data.getValue(User::class.java)
                    key = data.key
                    updateUI()
                }
            }
        })
    }

    private fun updateUI(){
        nameField!!.setText(user!!.name)
        emailField!!.setText(user!!.email)
        phoneField!!.setText(user!!.phone)
    }

    private fun saveInfo(){
        if (nameField!!.text.toString() != ""){
            user!!.name = nameField!!.text.toString()
        }
        if (emailField!!.text.toString() != ""){
            user!!.email = emailField!!.text.toString()
        }
        if (phoneField!!.text.toString() != ""){
            user!!.phone = phoneField!!.text.toString()
        }

        usersRef!!.child(key!!).setValue(user).addOnCompleteListener {
            updateUI()
            Toast.makeText(context, "Dados atualizados!", Toast.LENGTH_LONG).show()
        }
    }

    private fun sendEmail(){
        val emailAddress = currentUser!!.email

        mAuth!!.sendPasswordResetEmail(emailAddress!!).addOnCompleteListener {
            Toast.makeText(context, "Enviamos um email para você", Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        fun newInstance(): ClientProfileFragment = ClientProfileFragment()
    }

}
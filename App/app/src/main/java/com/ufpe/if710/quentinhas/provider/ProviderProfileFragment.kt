package com.ufpe.if710.quentinhas.provider


import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ufpe.if710.quentinhas.R
import com.ufpe.if710.quentinhas.model.User


class ProviderProfileFragment : Fragment() {
    private val handle = Handler()

    private var mAuth: FirebaseAuth? = null
    private var currentUser: FirebaseUser? = null

    private var user: User? = null

    private var mView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth!!.currentUser

        findUser()

        mView = inflater.inflate(R.layout.fragment_profile_provider, container, false)

        val card = mView!!.findViewById<CardView>(R.id.cardView_2_profile_provider)
        val btn = mView!!.findViewById<Button>(R.id.btn_save)

        btn.setOnClickListener {

        }

        card.setOnClickListener {
            card.setCardBackgroundColor(resources.getColor(R.color.colorPrimary))
            handle.postDelayed({card.setCardBackgroundColor(resources.getColor(R.color.colorWhite)) }, 500)
            sendEmail()
        }
        return mView
    }

    private fun sendEmail(){
        val auth = FirebaseAuth.getInstance()
        val emailAddress = auth.currentUser!!.email

        auth.sendPasswordResetEmail(emailAddress!!).addOnCompleteListener {
            Toast.makeText(context, "Enviamos um email para você", Toast.LENGTH_LONG).show()
        }
    }

    private fun updateUI(){
        val restaurantField = mView!!.findViewById<EditText>(R.id.field_1_card_1_profile_provider)
        val nameField = mView!!.findViewById<EditText>(R.id.field_2_card_1_profile_provider)
        val emailField = mView!!.findViewById<EditText>(R.id.field_3_card_1_profile_provider)
        val phoneField = mView!!.findViewById<EditText>(R.id.field_4_card_1_profile_provider)

        restaurantField.setText(user!!.restaurant)
        nameField.setText(user!!.name)
        emailField.setText(user!!.email)
        phoneField.setText(user!!.phone)
    }

    private fun findUser(){
        val userID = currentUser!!.uid
        val usersRef = FirebaseDatabase.getInstance().reference.child("users")
        val query = usersRef.orderByKey()

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println(p0.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot.children
                children.forEach {
                    if (it.key.equals(userID)){
                        user = it.getValue(User::class.java)
                        updateUI()
                    }
                }
            }
        })
    }

    companion object {
        fun newInstance(): ProviderProfileFragment =
            ProviderProfileFragment()
    }

}

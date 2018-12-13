package com.ufpe.if710.quentinhas.provider


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


class ProviderProfileFragment : Fragment() {
    private val handle = Handler()

    private var mAuth: FirebaseAuth? = null
    private var currentUser: FirebaseUser? = null
    private var usersRef: DatabaseReference? = null

    private var user: User? = null
    private var key: String? = null

    private var mView: View? = null
    private var restaurantField: EditText? = null
    private var nameField: EditText? = null
    private var emailField: EditText? = null
    private var phoneField: EditText? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_profile_provider, container, false)

        restaurantField = mView!!.findViewById(R.id.field_1_card_1_profile_provider)
        nameField = mView!!.findViewById(R.id.field_2_card_1_profile_provider)
        emailField = mView!!.findViewById(R.id.field_3_card_1_profile_provider)
        phoneField = mView!!.findViewById(R.id.field_4_card_1_profile_provider)

        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth!!.currentUser
        usersRef = FirebaseDatabase.getInstance().reference.child("users")

        findUser()

        val card = mView!!.findViewById<CardView>(R.id.cardView_2_profile_provider)
        val btn = mView!!.findViewById<Button>(R.id.btn_save_profile)

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

    private fun sendEmail(){
        val auth = FirebaseAuth.getInstance()
        val emailAddress = auth.currentUser!!.email

        auth.sendPasswordResetEmail(emailAddress!!).addOnCompleteListener {
            Toast.makeText(context, "Enviamos um email para você", Toast.LENGTH_LONG).show()
        }
    }

    private fun updateUI(){
        restaurantField!!.setText(user!!.restaurant)
        nameField!!.setText(user!!.name)
        emailField!!.setText(user!!.email)
        phoneField!!.setText(user!!.phone)
    }

    private fun saveInfo(){
        if (restaurantField!!.text.toString() != ""){
            user!!.restaurant = restaurantField!!.text.toString()
        }
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

    companion object {
        fun newInstance(): ProviderProfileFragment =
            ProviderProfileFragment()
    }

}

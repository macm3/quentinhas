package com.ufpe.if710.quentinhas.provider

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.ufpe.if710.quentinhas.R
import com.ufpe.if710.quentinhas.model.User
import kotlinx.android.synthetic.main.activity_provider_configurations.*
import java.text.SimpleDateFormat
import java.util.*


class ProviderConfigurationsActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private var currentUser: FirebaseUser? = null
    private var usersRef: DatabaseReference? = null

    private var user: User? = null
    private var key: String? = null

    private var paymentMethods: ArrayList<User.PaymentMethod>? = null
    private var endTime: String? = null
    private var pickupTime: String? = null
    private var address: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_provider_configurations)

        setSupportActionBar(toolbar_configurations)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        picker_end.setIs24HourView(true)
        picker_pickup.setIs24HourView(true)

        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth!!.currentUser
        usersRef = FirebaseDatabase.getInstance().reference.child("users")

        findUser()

        btn_save_payment.setOnClickListener {
            savePaymentMethod()
        }

        btn_save_end_time.setOnClickListener {
            saveEndTime()
        }

        btn_save_pickup.setOnClickListener {
            savePickupTime()
        }

        btn_save_pickup_address.setOnClickListener {
            savePickUpAddress()
        }
    }

    override fun onSupportNavigateUp():Boolean {
        onBackPressed()
        return true
    }

    private fun updateUI(){
        paymentMethods = user!!.paymentMethods
        endTime = user!!.endTime
        pickupTime = user!!.pickupTime
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        var date: Date
        val c = Calendar.getInstance()
        address = user!!.address

        check_cash.isChecked = paymentMethods!!.contains(User.PaymentMethod.CASH)
        check_pagseguro.isChecked = paymentMethods!!.contains(User.PaymentMethod.PAGSEGURO)

        if (endTime != null){
            date = sdf.parse(endTime)
            c.time = date

            picker_end.currentHour = c.get(Calendar.HOUR_OF_DAY)
            picker_end.currentMinute = c.get(Calendar.MINUTE)
        }
        if (pickupTime != null){
            date = sdf.parse(pickupTime)
            c.time = date

            picker_pickup.currentHour = c.get(Calendar.HOUR_OF_DAY)
            picker_pickup.currentMinute = c.get(Calendar.MINUTE)
        }
        if (address != null){
            pickup_address.setText(address)
        }
    }

    private fun savePaymentMethod(){
        if (check_cash.isChecked){
            if (!user!!.paymentMethods.contains(User.PaymentMethod.CASH)){
                user!!.paymentMethods.add(User.PaymentMethod.CASH)
            }
        } else {
            user!!.paymentMethods.remove(User.PaymentMethod.CASH)
        }
        if (check_pagseguro.isChecked){
            if (!user!!.paymentMethods.contains(User.PaymentMethod.PAGSEGURO)){
                user!!.paymentMethods.add(User.PaymentMethod.PAGSEGURO)
            }
        } else {
            user!!.paymentMethods.remove(User.PaymentMethod.PAGSEGURO)
        }

        updateFirebase()
    }

    private fun saveEndTime(){
        user!!.endTime = "${picker_end.currentHour}:${picker_end.currentMinute}"
        updateFirebase()
    }

    private fun savePickupTime(){
        user!!.pickupTime = "${picker_pickup.currentHour}:${picker_pickup.currentMinute}"
        updateFirebase()
    }

    private fun savePickUpAddress(){
        if (pickup_address.text.toString() != ""){
            user!!.address = pickup_address.text.toString()
            updateFirebase()
        }
    }

    private fun updateFirebase(){
        usersRef!!.child(key!!).setValue(user).addOnCompleteListener {
            updateUI()
            Toast.makeText(this, "Dados atualizados!", Toast.LENGTH_LONG).show()
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
}

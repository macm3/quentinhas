package com.ufpe.if710.quentinhas.client

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.ufpe.if710.quentinhas.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.ufpe.if710.quentinhas.model.User
import com.google.firebase.database.FirebaseDatabase
import com.ufpe.if710.quentinhas.provider.MenuActivity
import com.google.firebase.database.ValueEventListener

class ProviderAdapter (private val items: List<User>) : RecyclerView.Adapter<ProviderAdapter.MyViewHolder>()  {

    private var mAuth: FirebaseAuth? = null
    private var currentUser: FirebaseUser? = null

    private var user: User? = null

    class MyViewHolder(val view: LinearLayout) : RecyclerView.ViewHolder(view){
        var name: TextView = view.findViewById(R.id.provider_name)
        var btn: Button = view.findViewById(R.id.btn_getMenu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val linearLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_menu, parent, false)  as LinearLayout

        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth!!.currentUser

        return MyViewHolder(linearLayout)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private fun showProviders(){
        val userID = currentUser!!.uid
        val usersRef = FirebaseDatabase.getInstance().reference.child("users")
        val query = usersRef.orderByKey()

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println(p0.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot.children
                val itsProvider =
                println("count: "+snapshot.children.count().toString())
                children.forEach {
//                    if (it.key.equals(userID)){
//                        user = it.getValue(User::class.java)
//                        name = user!!.name
//                        email = user!!.email
//                        phone = user!!.phone
//                        Log.d("encontrado", "$name $email $phone")
//                    }
                }
            }
        })
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val context = holder.view.context
//        holder.title.text = items[position].title
//        var proteins: String? = ""
//        for (i in items[position].protein.indices){
//            proteins = "$proteins\n${items[position].protein[i]}"
//        }
//        holder.text.text = proteins
//
//        holder.btn.setOnClickListener {
//            val intent = Intent(context, MenuActivity::class.java)
//            intent.putExtra(MENU_ID, items[position].menuID)
//            context.startActivity(intent)
//        }
    }

    companion object {
        val MENU_ID = "Menu ID"
    }
}
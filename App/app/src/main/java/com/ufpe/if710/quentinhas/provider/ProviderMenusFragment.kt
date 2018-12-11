package com.ufpe.if710.quentinhas.provider


import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

import com.ufpe.if710.quentinhas.R
import com.ufpe.if710.quentinhas.model.Menu
import com.ufpe.if710.quentinhas.model.User
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException

class ProviderMenusFragment : Fragment() {
    private var mView: View? = null
    private var recyclerView: RecyclerView? = null

    private var menusList: ArrayList<Menu> = arrayListOf()

    private var providerID: String? = null
    private var usersRef: DatabaseReference? = null
    private var user: User? = null
    private var key: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_provider_menus, container, false)

        recyclerView = mView!!.findViewById(R.id.recycler_view_menus)

        providerID = FirebaseAuth.getInstance().currentUser!!.uid
        usersRef = FirebaseDatabase.getInstance().reference.child("users")
        findUser()

        val fab = mView!!.findViewById<FloatingActionButton>(R.id.fab_new_menu)
        fab.setOnClickListener {
            startActivity(Intent(context, NewMenuActivity::class.java))
        }
        
        updateUI()
        return mView
    }

    private fun updateUI(){
        try {
            doAsync {
                val adapter = MenusAdapter(menusList)
                uiThread {
                    recyclerView!!.adapter = adapter
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
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
                    findMenus()
                }
            }
        })
    }

    private fun findMenus(){
        val menusRef = FirebaseDatabase.getInstance().reference.child("menus")
        val query = menusRef.orderByKey()

        query.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot.children
                children.forEach{
                    if (user!!.menus.contains(it.key)){
                        menusList.add(it.getValue(Menu::class.java)!!)
                    }
                }
                updateUI()
            }

        })
    }

    companion object {
        fun newInstance(): ProviderMenusFragment = ProviderMenusFragment()
    }
}

package com.ufpe.if710.quentinhas.provider


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ufpe.if710.quentinhas.R
import com.ufpe.if710.quentinhas.model.Menu
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException

class ProviderMenusFragment : Fragment() {
    private var mView: View? = null
    private var recyclerView: RecyclerView? = null

    private var menusList: ArrayList<Menu> = arrayListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_provider_menus, container, false)

        recyclerView = mView!!.findViewById(R.id.recycler_view_menus)
        menusList.add(Menu("1", "titulo", arrayListOf("proteina 1", "proteina 2", "proteina 3")))
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

    companion object {
        fun newInstance(): ProviderMenusFragment = ProviderMenusFragment()
    }
}

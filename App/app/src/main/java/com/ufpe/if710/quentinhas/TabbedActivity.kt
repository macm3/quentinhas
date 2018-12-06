package com.ufpe.if710.quentinhas

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toolbar
import kotlinx.android.synthetic.main.activity_tabbed.*

class TabbedActivity : AppCompatActivity() {
    var toolbar: ActionBar? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                toolbar!!.title = resources.getString(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_menus -> {
                toolbar!!.title = resources.getString(R.string.title_menus)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                toolbar!!.title = resources.getString(R.string.title_profile)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabbed)

        toolbar = supportActionBar!!
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_config, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId)  {
        R.id.action_config -> {
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

}

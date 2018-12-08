package com.ufpe.if710.quentinhas.provider

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.ufpe.if710.quentinhas.R
import kotlinx.android.synthetic.main.activity_tabbed_provider.*

class ProviderTabbedActivity : AppCompatActivity() {
    var toolbar: ActionBar? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                toolbar!!.title = resources.getString(R.string.title_home)
                val homeFragment = ProviderHomeFragment.newInstance()
                openFragment(homeFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_menus -> {
                toolbar!!.title = resources.getString(R.string.title_menus)
                val menuFragment = ProviderMenusFragment.newInstance()
                openFragment(menuFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                toolbar!!.title = resources.getString(R.string.title_profile)
                val profileFragment = ProviderProfileFragment.newInstance()
                openFragment(profileFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabbed_provider)

        toolbar = supportActionBar!!
        toolbar!!.title = resources.getString(R.string.title_home)

        openFragment(ProviderHomeFragment.newInstance())
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_config, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId)  {
        R.id.action_config -> {
            startActivity(Intent(this, ProviderConfigurationsActivity::class.java))
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

}

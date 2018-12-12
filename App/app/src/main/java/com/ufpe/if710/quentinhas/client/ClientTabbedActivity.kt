package com.ufpe.if710.quentinhas.client

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import com.ufpe.if710.quentinhas.R
import kotlinx.android.synthetic.main.activity_tabbed_client.*

class ClientTabbedActivity : AppCompatActivity(){
    var toolbar: ActionBar? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home_client -> {
                toolbar!!.title = resources.getString(R.string.title_home)
                val homeFragment = ClientHomeFragment.newInstance()
                openFragment(homeFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_order_client -> {
                toolbar!!.title = resources.getString(R.string.toolbar_order_title)
                val requestFragment = ClientOrderFragment.newInstance()
                openFragment(requestFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile_client -> {
                toolbar!!.title = resources.getString(R.string.title_profile)
                val profileFragment = ClientProfileFragment.newInstance()
                openFragment(profileFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container_client, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabbed_client)

        toolbar = supportActionBar!!
        toolbar!!.title = resources.getString(R.string.title_home)

        openFragment(ClientHomeFragment.newInstance())
        navigation_client.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
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
    }*/


}
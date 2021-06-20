package com.viettel.appdrawer

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.viettel.appdrawer.ui.AppDrawerFragment
import com.viettel.appdrawer.ui.InformationFragment

class MainActivity : AppCompatActivity() {
    private lateinit var toolbar : ActionBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = supportActionBar!!
        toolbar.setBackgroundDrawable(ColorDrawable(Color.parseColor("#EA1C2D")))

        val navigation = this.findViewById<BottomNavigationView >(R.id.bottom_navigation)
        navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.info -> {
                    toolbar.title = "Information"
                    loadFragment(InformationFragment())
                }
                R.id.app_drawer -> {
                    toolbar.title = "App Drawer"
                    loadFragment(AppDrawerFragment())
                }
            }
            false
        }

        toolbar.title = "Information"
        loadFragment(InformationFragment())

    }

    private fun loadFragment(fragment: Fragment?) : Boolean {
        if (fragment != null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
            return true
        }
        return false
    }
}
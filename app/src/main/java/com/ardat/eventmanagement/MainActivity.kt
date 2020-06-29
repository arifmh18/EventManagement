package com.ardat.eventmanagement

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ardat.eventmanagement.Fragment.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.home_menu -> {
                val fragmentManager = supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                val fragment = EventFragment()
                fragmentTransaction.replace(R.id.main_fragment, fragment)
                fragmentTransaction.commit()
            }
            R.id.acara -> {
                val fragmentManager = supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                val fragment = AgendaFragment()
                fragmentTransaction.replace(R.id.main_fragment, fragment)
                fragmentTransaction.commit()
            }
            R.id.team -> {
                val fragmentManager = supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                val fragment = TeamFragment()
                fragmentTransaction.replace(R.id.main_fragment, fragment)
                fragmentTransaction.commit()
            }
            R.id.keuangan -> {
                val fragmentManager = supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                val fragment = FinanceFragment()
                fragmentTransaction.replace(R.id.main_fragment, fragment)
                fragmentTransaction.commit()
            }
            R.id.peralatan -> {
                val fragmentManager = supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                val fragment = EquipmentFragment()
                fragmentTransaction.replace(R.id.main_fragment, fragment)
                fragmentTransaction.commit()
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = EventFragment()
        fragmentTransaction.replace(R.id.main_fragment, fragment)
        fragmentTransaction.commit()

    }
}
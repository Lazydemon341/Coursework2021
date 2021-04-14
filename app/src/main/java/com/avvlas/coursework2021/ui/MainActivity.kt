package com.avvlas.coursework2021.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.ui.addmacro.AddMacroFragment
import com.avvlas.coursework2021.ui.macroslist.MacrosListFragment
import com.avvlas.coursework2021.utils.Utils.currentNavigationFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(findViewById(R.id.app_toolbar))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val currentFragment = supportFragmentManager.currentNavigationFragment
                if (currentFragment is AddMacroFragment){
                    currentFragment.onBackPressed()
                }
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
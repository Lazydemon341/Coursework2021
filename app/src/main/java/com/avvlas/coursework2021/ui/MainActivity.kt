package com.avvlas.coursework2021.ui

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.ui.addmacro.AddMacroFragment
import com.avvlas.coursework2021.ui.macroslist.MacrosListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(findViewById(R.id.app_toolbar))

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, AddMacroFragment.newInstance())
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar, menu)
        return true
    }
}
package com.avvlas.coursework2021.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.avvlas.coursework2021.App
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.data.MacrosRepository
import com.avvlas.coursework2021.model.options.actions.SendSmsAction
import com.avvlas.coursework2021.ui.addmacro.AddMacroFragment
import com.avvlas.coursework2021.ui.macrodetails.MacroDetailsFragment
import com.avvlas.coursework2021.utils.Utils.currentNavigationFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    @Inject
    internal lateinit var macrosRepository: MacrosRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(findViewById(R.id.app_toolbar))

        if (savedInstanceState == null) {
            lifecycleScope.launch(Dispatchers.IO) {
                macrosRepository.getAll().forEach {
                    if (it.isActivated)
                        it.activate(this@MainActivity)
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() =
        when (val currentFragment = supportFragmentManager.currentNavigationFragment) {
            is AddMacroFragment -> currentFragment.onBackPressed()
            is MacroDetailsFragment -> currentFragment.onBackPressed()
            else -> super.onBackPressed()
        }

    override fun onDestroy() {
        Log.d("myTag", "Main Activity onDestroy called")
        super.onDestroy()
    }
}
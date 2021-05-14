package com.avvlas.coursework2021.ui

import android.app.Activity
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
import com.avvlas.coursework2021.model.Macro
import com.avvlas.coursework2021.model.options.triggers.LocationTrigger
import com.avvlas.coursework2021.ui.addmacro.AddMacroFragment
import com.avvlas.coursework2021.ui.macrodetails.MacroDetailsFragment
import com.avvlas.coursework2021.utils.Parcelables.toParcelable
import com.avvlas.coursework2021.utils.Utils.CREATOR
import com.avvlas.coursework2021.utils.Utils.currentNavigationFragment
import com.schibstedspain.leku.LATITUDE
import com.schibstedspain.leku.LONGITUDE
import com.schibstedspain.leku.TRANSITION_BUNDLE
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
            activateMacros()
        }
    }

    private fun activateMacros() {
        lifecycleScope.launch(Dispatchers.IO) {
            macrosRepository.getAll().forEach {
                if (it.isActivated)
                    it.activate(this@MainActivity)
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            Log.d(TAG, "OK")
            when (requestCode) {
                LocationTrigger.MAP_PICKER_REQUEST_CODE -> {
                    val latitude = data.getDoubleExtra(LATITUDE, 0.0)
                    Log.d(TAG, latitude.toString())
                    val longitude = data.getDoubleExtra(LONGITUDE, 0.0)
                    Log.d(TAG, longitude.toString())

                    val bundle = data.getBundleExtra(TRANSITION_BUNDLE)
                    val macro = bundle?.getByteArray(LocationTrigger.EXTRA_MACRO)
                        ?.toParcelable(Macro.CREATOR)
                    Log.d(TAG, macro.toString())
                    macro?.addTrigger(LocationTrigger())
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.d(TAG, "CANCELLED")
        }
    }

    override fun onDestroy() {
        Log.d(App.TAG, "Main Activity onDestroy called")
        super.onDestroy()
    }

    companion object {
        private const val TAG = "MainActivityTag"
    }
}
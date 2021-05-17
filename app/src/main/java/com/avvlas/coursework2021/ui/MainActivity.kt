package com.avvlas.coursework2021.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.list.isItemChecked
import com.afollestad.materialdialogs.list.listItemsMultiChoice
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.data.MacrosRepository
import com.avvlas.coursework2021.model.options.triggers.LocationTrigger
import com.avvlas.coursework2021.ui.about.AboutFragment
import com.avvlas.coursework2021.ui.addmacro.AddMacroFragment
import com.avvlas.coursework2021.ui.macrodetails.MacroDetailsFragment
import com.avvlas.coursework2021.utils.Utils.currentNavigationFragment
import com.google.android.gms.location.Geofence
import com.google.android.material.slider.Slider
import com.schibstedspain.leku.LATITUDE
import com.schibstedspain.leku.LONGITUDE
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
            R.id.about -> {
                supportFragmentManager.commit {
                    replace(R.id.nav_host_fragment, AboutFragment.getInstance())
                    addToBackStack(null)
                }
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
            Log.d(TAG, "RESULT OK")
            when (requestCode) {
                LocationTrigger.MAP_PICKER_REQUEST_CODE -> {
                    val latitude = data.getDoubleExtra(LATITUDE, 0.0)
                    Log.d(TAG, latitude.toString())
                    val longitude = data.getDoubleExtra(LONGITUDE, 0.0)
                    Log.d(TAG, longitude.toString())

                    val slider: Slider = object : Slider(this) {}.apply {
                        valueFrom = 10.0F
                        valueTo = 500.0F
                        stepSize = 5.0F
                        value = valueFrom
                    }

                    MaterialDialog(this)
                        .title(res = R.string.choose_radius_and_triggers)
                        .customView(view = slider)
                        .listItemsMultiChoice(
                            res = R.array.geofence_transitions,
                            allowEmptySelection = false
                        )
                        .positiveButton(res = R.string.ok) { dialog ->
                            when (val currentFragment =
                                supportFragmentManager.currentNavigationFragment) {
                                is AddMacroFragment -> {
                                    currentFragment.addTriggerToMacro(
                                        LocationTrigger(
                                            latitude = latitude,
                                            longitude = longitude,
                                            radius = slider.value,
                                            transitions = listOf(
                                                0,
                                                1
                                            ).filter { dialog.isItemChecked(it) }
                                                .map { if (it == 0) Geofence.GEOFENCE_TRANSITION_ENTER else Geofence.GEOFENCE_TRANSITION_EXIT }
                                        )
                                    )
                                }
                                is MacroDetailsFragment -> {
                                    // TODO: addTrigger(?)
                                }
                            }
                        }
                        .negativeButton(res = R.string.cancel)
                        .show()
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.d(TAG, "RESULT CANCELLED")
        }
    }

    override fun onDestroy() {
        Log.d(TAG, "MainActivity Destroyed")
        super.onDestroy()
    }

    companion object {
        private const val TAG = "MainActivityTag"
    }
}
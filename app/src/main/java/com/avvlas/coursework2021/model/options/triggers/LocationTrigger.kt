package com.avvlas.coursework2021.model.options.triggers

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.model.Macro
import com.schibstedspain.leku.LocationPickerActivity
import kotlinx.parcelize.Parcelize


@Parcelize
class LocationTrigger(
    @DrawableRes override val icon: Int = R.drawable.ic_baseline_menu_24, // TODO
    @StringRes override val title: Int = R.string.pick_date_and_time, // TODO
    var latitude: Double = 55.7520,
    var longitude: Double = 37.6175,
    var radius: Float = 10.0F,
    var transactions: List<Int> = listOf()
) : Trigger(icon, title) {

    override fun schedule(appContext: Context, macro: Macro) {
        Log.d("hi", "hi")
        // register geofence
    }

    override fun cancel(appContext: Context, macro: Macro) {
        // cancel geofence
    }

    override fun onClick(context: Context, macro: Macro) {
        if (!checkPermissions(context)) {
            requestPermissions(context)
        } else {
            val locationPickerIntent = LocationPickerActivity.Builder()
                .withLocation(latitude, longitude)
                .withGeolocApiKey(context.getString(R.string.google_maps_key))
                .withDefaultLocaleSearchZone()
                //.withSearchZone("es_ES")
                //.withSearchZone(SearchZoneRect(LatLng(26.525467, -18.910366), LatLng(43.906271, 5.394197)))
                //.shouldReturnOkOnBackPressed()
                //.withStreetHidden()
                //.withCityHidden()
                //.withZipCodeHidden()
                //.withSatelliteViewHidden()
                //.withGooglePlacesEnabled()
                .withGoogleTimeZoneEnabled()
                .withVoiceSearchHidden()
                .withUnnamedRoadHidden()
                .build(context.applicationContext)

            startActivityForResult(
                context as AppCompatActivity,
                locationPickerIntent,
                MAP_PICKER_REQUEST_CODE,
                null
            )
        }
    }

    private fun checkPermissions(context: Context): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions(context: Context) {
        ActivityCompat.requestPermissions(
            context as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_PERMISSIONS_REQUEST_CODE
        )
    }


    companion object {
        const val EXTRA_MACRO = "macro"
        const val MAP_PICKER_REQUEST_CODE = 12
        const val REQUEST_PERMISSIONS_REQUEST_CODE = 24
    }
}
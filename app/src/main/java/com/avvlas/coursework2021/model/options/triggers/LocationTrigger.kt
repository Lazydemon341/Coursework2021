package com.avvlas.coursework2021.model.options.triggers

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.model.Macro
import com.avvlas.coursework2021.utils.Parcelables.toByteArray
import com.schibstedspain.leku.LocationPickerActivity
import kotlinx.parcelize.Parcelize

@Parcelize
class LocationTrigger(
    @DrawableRes override val icon: Int = R.drawable.ic_baseline_menu_24, // TODO
    @StringRes override val title: Int = R.string.pick_date_and_time // TODO
) : Trigger(icon, title) {

    override fun schedule(appContext: Context, macro: Macro) {
        //TODO("Not yet implemented")
    }

    override fun cancel(context: Context, macro: Macro) {
        //TODO("Not yet implemented")
    }

    override fun onClick(context: Context, macro: Macro) {
        val locationPickerIntent = LocationPickerActivity.Builder()
            //.withLocation(41.4036299, 2.1743558)
            .withGeolocApiKey(context.getString(R.string.google_maps_key))
            //.withSearchZone("es_ES")
            //.withSearchZone(SearchZoneRect(LatLng(26.525467, -18.910366), LatLng(43.906271, 5.394197)))
            .withDefaultLocaleSearchZone()
            .shouldReturnOkOnBackPressed()
            //.withStreetHidden()
            //.withCityHidden()
            //.withZipCodeHidden()
            //.withSatelliteViewHidden()
            //.withGooglePlacesEnabled()
            .withGoogleTimeZoneEnabled()
            .withVoiceSearchHidden()
            .withUnnamedRoadHidden()
            .build(context.applicationContext)

        locationPickerIntent.putExtra(EXTRA_MACRO, macro.toByteArray())

        startActivityForResult(
            context as AppCompatActivity,
            locationPickerIntent,
            MAP_PICKER_REQUEST_CODE,
            null
        )
    }

    companion object {
        const val EXTRA_MACRO = "macro"
        const val MAP_PICKER_REQUEST_CODE = 12
    }
}
package com.avvlas.coursework2021.model.options.triggers

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.model.Macro
import com.avvlas.coursework2021.utils.Parcelables.toByteArray
import com.avvlas.coursework2021.utils.Parcelables.toParcelable
import com.avvlas.coursework2021.utils.Utils.CREATOR
import com.avvlas.coursework2021.utils.broadcastreceivers.AlarmReceiver
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import com.schibstedspain.leku.LocationPickerActivity
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize


@Parcelize
class LocationTrigger(
    @DrawableRes override val icon: Int = R.drawable.ic_baseline_location_on_24, // TODO
    @StringRes override val title: Int = R.string.location_trigger_title, // TODO
    var latitude: Double = 55.7520,
    var longitude: Double = 37.6175,
    var radius: Float = 10.0F,
    var transitions: List<Int> = listOf()
) : Trigger(icon, title) {

    @IgnoredOnParcel
    @Transient
    private var mGeofencePendingIntent: PendingIntent? = null

    @IgnoredOnParcel
    @Transient
    private var mGeofencingClient: GeofencingClient? = null

    override fun schedule(appContext: Context, macro: Macro) {
        mGeofencingClient = LocationServices.getGeofencingClient(appContext)
        addGeofences(appContext, macro)
    }

    override fun cancel(appContext: Context, macro: Macro) {
        mGeofencingClient = LocationServices.getGeofencingClient(appContext)
        removeGeofences(appContext, macro)
    }

    private fun removeGeofences(context: Context, macro: Macro) {
        if (!checkPermissions(context)) {
            // showSnackbar(getString(R.string.insufficient_permissions))
            return
        }
        mGeofencingClient!!.removeGeofences(getGeofencePendingIntent(context, macro))
    }

    @SuppressWarnings("MissingPermission")
    private fun addGeofences(context: Context, macro: Macro) {
        if (!checkPermissions(context)) {
            // showSnackbar(getString(R.string.insufficient_permissions))
            return
        }
        mGeofencingClient!!.addGeofences(
            getGeofencingRequest(macro),
            getGeofencePendingIntent(context, macro)
        )
    }

    private fun getGeofence(macro: Macro): Geofence =
        Geofence.Builder()
            .apply {
                setRequestId(macro.id.toString()) // Set the request ID of the geofence. This is a string to identify this geofence.
                setCircularRegion( // Set the circular region of this geofence.
                    latitude,
                    longitude,
                    radius
                )
                // TODO: never expire
                setExpirationDuration(300000)

                if (transitions.size == 1) {
                    setTransitionTypes(transitions[0])
                } else {
                    setTransitionTypes(
                        Geofence.GEOFENCE_TRANSITION_ENTER or
                                Geofence.GEOFENCE_TRANSITION_EXIT
                    )
                }
            }
            .build()

    private fun getGeofencingRequest(macro: Macro): GeofencingRequest =
        GeofencingRequest.Builder().apply {
            setInitialTrigger(Geofence.GEOFENCE_TRANSITION_ENTER)
            addGeofence(getGeofence(macro))
        }.build()


    private fun getGeofencePendingIntent(context: Context, macro: Macro): PendingIntent {
        // Reuse the PendingIntent if we already have it.
        if (mGeofencePendingIntent != null) {
            return mGeofencePendingIntent as PendingIntent
        }
        val intent = Intent(context, GeofenceReceiver::class.java)
        val bundle = Bundle().apply {
            putByteArray(EXTRA_MACRO, macro.toByteArray())
        }
        intent.putExtras(bundle)
        intent.action = this.javaClass.simpleName
        mGeofencePendingIntent =
            PendingIntent.getBroadcast(
                context,
                macro.id.toInt(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        return mGeofencePendingIntent as PendingIntent
    }

    override fun onClick(context: Context, macro: Macro) {
        requireFineLocationPermission(context)
    }

    private fun checkPermissions(context: Context): Boolean {
        val accessFineLocationState = ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val accessBackgroundLocationState = ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
        )
        return accessFineLocationState == PackageManager.PERMISSION_GRANTED &&
                accessBackgroundLocationState == PackageManager.PERMISSION_GRANTED
    }

    private fun requireFineLocationPermission(context: Context) {
        Permissions.check(
            context as AppCompatActivity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            null,
            null,
            object : PermissionHandler() {
                override fun onGranted() {
                    requireBackgroundLocationPermission(context)
                }
            }
        )
    }

    private fun requireBackgroundLocationPermission(context: Context) {
        Permissions.check(
            context as AppCompatActivity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            null,
            null,
            object : PermissionHandler() {
                override fun onGranted() {
                    startLocationPicker(context)
                }
            }
        )
    }

    private fun startLocationPicker(context: Context) {
        val locationPickerIntent = LocationPickerActivity.Builder()
            .withLocation(latitude, longitude)
            .withGeolocApiKey(context.getString(R.string.google_maps_key))
            .withDefaultLocaleSearchZone()
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


    companion object {
        private const val EXTRA_MACRO = "macro"
        const val MAP_PICKER_REQUEST_CODE = 12
        const val REQUEST_PERMISSIONS_REQUEST_CODE = 24
    }
}

class GeofenceReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("GeofenceReceiver", intent.action.toString())
        if (intent.action == LocationTrigger::class.java.simpleName) {
            val macro = intent.getByteArrayExtra(AlarmReceiver.MACRO)?.toParcelable(Macro.CREATOR)
            macro?.runActions(context)
        }
    }
}
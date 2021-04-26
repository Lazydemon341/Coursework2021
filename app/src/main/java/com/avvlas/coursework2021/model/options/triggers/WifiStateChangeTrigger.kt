package com.avvlas.coursework2021.model.options.triggers
//
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.util.Log
//import androidx.annotation.DrawableRes
//import androidx.work.*
//import com.avvlas.coursework2021.R
//import com.avvlas.coursework2021.model.Macro
//import com.avvlas.coursework2021.utils.Parcelables.toByteArray
//import com.avvlas.coursework2021.utils.Parcelables.toParcelable
//import com.avvlas.coursework2021.utils.Utils.CREATOR
//import kotlinx.parcelize.IgnoredOnParcel
//import kotlinx.parcelize.Parcelize
//
//
//@Parcelize
//class WifiStateChangeTrigger(
//    @DrawableRes override val icon: Int = R.drawable.ic_baseline_wifi_24,
//    override val title: String = "Wifi"
//) : Trigger(icon, title) {
//
//    @IgnoredOnParcel
//    private var receiver: BroadcastReceiver? = null
//
//    override fun schedule(context: Context, macro: Macro) {
//        val constraints: Constraints = Constraints.Builder()
//            .build()
//        val data = Data.Builder()
//            .putByteArray("MACRO", macro.toByteArray())
//            .build()
//        val onetimeJob = OneTimeWorkRequest.Builder(MyWorker::class.java)
//            .setInputData(data)
//            .setConstraints(constraints).build() // or PeriodicWorkRequest
//
//        WorkManager.getInstance(context).enqueue(onetimeJob)
//    }
//
//    override fun cancel(context: Context, macro: Macro) {
//        receiver?.let {
//            context.unregisterReceiver(it)
//            receiver = null
//        }
//    }
//
//    class MyWorker(context: Context, workerParams: WorkerParameters) :
//        Worker(context.applicationContext, workerParams) {
//
//        override fun doWork(): Result {
//            Log.d("Worker", "dowork called")
//            val macro = inputData.getByteArray("MACRO")?.toParcelable(Macro.CREATOR)
//            macro?.let {
//                for (action in it.actions)
//                    action.execute(applicationContext)
//            }
//            return Result.success()
//        }
//    }
//
//    override fun onClick(context: Context, macro: Macro) {
//        super.onClick(context, macro)
//    }
//}
package com.gmail.apigeoneer.aesteroids

import android.app.Application
import android.os.Build
import androidx.work.*
import com.gmail.apigeoneer.aesteroids.data.work.RefreshDataWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AsteroidApplication: Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    /**
     *
     */
    override fun onCreate() {
        super.onCreate()
        delayedInit()
    }

    private fun delayedInit() {
        applicationScope.launch {
            setupRecurringWork()
        }
    }

    /**
     * Create a work request :
     * Setup WorkManager b/g job to fetch new network data
     *  daily.
     */
    private fun setupRecurringWork() {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .apply {
                setRequiresDeviceIdle(true)
            }
            .build()

        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(
            1,
            TimeUnit.DAYS
        ).setConstraints(constraints).build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            RefreshDataWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }
}
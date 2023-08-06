package com.gmail.apigeoneer.aesteroids.data.work

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.gmail.apigeoneer.aesteroids.data.getDatabase
import com.gmail.apigeoneer.aesteroids.data.repositories.AsteroidRepository
import com.gmail.apigeoneer.aesteroids.data.repositories.PictureOfTheDayRepository
import retrofit2.HttpException

/**
 * Pre-fetch data when the app is in the background
 */
class RefreshDataWorker(appContext: Context, params: WorkerParameters)
    :CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val asteroidRepository = AsteroidRepository(database)
        val pictureOfTheDayRepository = PictureOfTheDayRepository(database)
        return try {
            asteroidRepository.refreshAsteroids()
            pictureOfTheDayRepository.refreshPictureOfTheDay()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}
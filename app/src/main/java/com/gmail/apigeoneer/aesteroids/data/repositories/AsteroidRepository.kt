package com.gmail.apigeoneer.aesteroids.data.repositories

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.gmail.apigeoneer.aesteroids.data.AsteroidsDatabase
import com.gmail.apigeoneer.aesteroids.data.domain.Asteroid
import com.gmail.apigeoneer.aesteroids.data.toDatabaseModel
import com.gmail.apigeoneer.aesteroids.data.toDomainModel
import com.gmail.apigeoneer.aesteroids.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

/**
 * Repository for fetching asteroids from the Network & storing them on disk.
 */
class AsteroidRepository(private val database: AsteroidsDatabase) {

    // Convert your LiveData list of DatabaseVideo objects to domain Video objects
    @RequiresApi(Build.VERSION_CODES.O)
    val asteroids: LiveData<List<Asteroid>> = Transformations
        .map(database.asteroidDao.getAsteroids()
        ) {
            it.toDomainModel()
        }

    /**
     * Update the offline cache
     */
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun refreshAsteroids() {
        /**
         * Make a network call to getAsteroids(), & use the await() function
         * to tell the coroutine to suspend until the data is available.
         * Then call insertAll() to insert the asteroids into the database.
         * * -> spread operator: allows you to pass in an array to a fun that expects varargs.
         */
        val startDateFormatted = getFormattedToday()
        val endDateFormatted = getFormattedSeventhDay()

        withContext(Dispatchers.IO) {
            // String: return type of the getAsteroids() function
            val asteroidsList = AsteroidApi.asteroidService
                .getAsteroidsAsync(startDateFormatted, endDateFormatted, API_KEY).await()
                                           // await gives error (it isn't needed i/s a suspend fun)    ???

            // String -> List<Asteroid>
            val parsedAsteroidsList = parseAsteroidsJsonResult(JSONObject(asteroidsList))
            database.asteroidDao.insertAll(*parsedAsteroidsList.toDatabaseModel())
        }
    }

}
package com.gmail.apigeoneer.aesteroids.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gmail.apigeoneer.aesteroids.data.dao.AsteroidDao
import com.gmail.apigeoneer.aesteroids.data.dao.PictureOfTheDayDao
import com.gmail.apigeoneer.aesteroids.data.entities.AsteroidEntity
import com.gmail.apigeoneer.aesteroids.data.entities.PictureOfTheDayEntity

/**
 * The Room Database class puts together the Entity & the Dao
 */
@Database(entities = [AsteroidEntity::class, PictureOfTheDayEntity::class], version = 1)
abstract class AsteroidsDatabase: RoomDatabase() {
    abstract val asteroidDao: AsteroidDao
    abstract val pictureOfTheDayDao: PictureOfTheDayDao
}

/**
 * Use the singleton pattern to get an instance of the database
 */

// var for our singleton
private lateinit var INSTANCE: AsteroidsDatabase

fun getDatabase(context: Context): AsteroidsDatabase {
    // Made code synchronized so its thread safe
    synchronized(AsteroidsDatabase::class.java) {
        // Check whether the database has been initialized
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                    AsteroidsDatabase::class.java,
            "asteroids").build()
        }
    }
    return INSTANCE
}
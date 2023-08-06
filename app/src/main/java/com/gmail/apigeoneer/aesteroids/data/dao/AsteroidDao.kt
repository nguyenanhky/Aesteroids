package com.gmail.apigeoneer.aesteroids.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gmail.apigeoneer.aesteroids.data.entities.AsteroidEntity

@Dao
interface AsteroidDao {
    @Query("select * from asteroid_table")
    fun getAsteroids(): LiveData<List<AsteroidEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg asteroids: AsteroidEntity)
}
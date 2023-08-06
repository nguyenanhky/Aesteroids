package com.gmail.apigeoneer.aesteroids.network

import com.gmail.apigeoneer.aesteroids.Constants.BASE_URL
import com.gmail.apigeoneer.aesteroids.data.domain.PictureOfTheDay
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val URL = BASE_URL                                   // make sure the base url is the base url. don't play w/ it & start appending all kinds of stupid things
const val API_KEY = "PlrXOXI4EduI8PgVnyjzxgqfrmczwwOGXRg5Ylri"

interface AsteroidApiService {
    // https://api.nasa.gov/neo/rest/v1/feed?start_date=2015-09-07&end_date=2015-09-08&api_key=DEMO_KEY

    // @GET -> specifying the endpoint for the JSON Asteroid response
    @GET("neo/rest/v1/feed")
    fun getAsteroidsAsync(@Query("start_date") startDate: String,
                                  @Query("end_date") endDate: String,
                                  @Query("api_key") apiKey: String
    ): Deferred<String>             // To use await() on a method, the return type for that method should be of type Deferred<T>
}

interface PictureOdTheDayService {
    // https://api.nasa.gov/planetary/apod?api_key=YOUR_API_KEY

    @GET("planetary/apod")
    fun getPictureOfTheDayAsync(@Query("api_key") apiKey: String): Deferred<PictureOfTheDay>
}

//  Create a Moshi object
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// Retrofit obj for the Asteroid data
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())                     // for the Asteroids
    .addConverterFactory(MoshiConverterFactory.create(moshi))                  // for the ImageOfDay
    // .addCallAdapterFactory(CoroutineCallAdapterFactory())                   // for Coroutines                   // depreciated
    .addCallAdapterFactory(CoroutineCallAdapterFactory())                      // needed since we're using Deferred
    .baseUrl(BASE_URL)
    .build()

/**
 * Expose the Retrofit service to the rest of the app through a public object (Singleton)
 * we make a singleton since we don't want to create an instance of the api many times
 * we'll just create 1 instance (ie. here) & use it everywhere
 */
object AsteroidApi {
    val asteroidService: AsteroidApiService by lazy {
        retrofit.create(AsteroidApiService::class.java)
    }

    val pictureOdTheDayService: PictureOdTheDayService by lazy {
        retrofit.create(PictureOdTheDayService::class.java)
    }

}

package com.gmail.apigeoneer.aesteroids.overview

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.gmail.apigeoneer.aesteroids.data.domain.Asteroid
import com.gmail.apigeoneer.aesteroids.data.getDatabase
import com.gmail.apigeoneer.aesteroids.data.repositories.AsteroidRepository
import com.gmail.apigeoneer.aesteroids.data.repositories.PictureOfTheDayRepository
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
class OverviewViewModel(application: Application): AndroidViewModel(application) {

    companion object {
        private const val TAG = "OverviewViewModel"
    }

    private val database = getDatabase(application)
    private val asteroidRepository = AsteroidRepository(database)
    private val pictureOfTheDayRepository = PictureOfTheDayRepository(database)

    // the internal MutableLiveData String that stores the status of the most recent requests
    // response -> the request status String
    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status

//    private val _asteroids = MutableLiveData<List<Asteroid>>()
//    val asteroid: LiveData<List<Asteroid>>
//        get() = _asteroids

    // for navigating to the detail screen
    private val _navigateToSelectedAsteroid = MutableLiveData<Asteroid>()
    val navigateToSelectedAsteroid: LiveData<Asteroid>
        get() = _navigateToSelectedAsteroid

//    private val _pictureOfTheDay = MutableLiveData<PictureOfTheDay>()
//    val pictureOfTheDay: LiveData<PictureOfTheDay>
//        get() = _pictureOfTheDay

    // Call getMarsRealEstateProperties() on init so we can display status immediately
    init {
//        getAsteroids()
//          getPictureOfTheDay()

        viewModelScope.launch {
            asteroidRepository.refreshAsteroids()
        }

        viewModelScope.launch {
            pictureOfTheDayRepository.refreshPictureOfTheDay()
        }
    }

//    /**
//     * Sets the value of the status LiveData to the NASA Asteroid API status
//     */
//    private fun getAsteroids() {
////         _response.value = "Set the Mars API response here!"                                      // checking for response
//
////        val asteroid = AsteroidApi.retrofitService.getAsteroids("2019-9-7", "2019-9-8")           // fetching response w/o Retrofit
////        asteroid.enqueue(object : Callback<AsteroidData> {
////
////            override fun onFailure(call: Call<AsteroidData>, t: Throwable) {
////                Log.d(TAG, "Failed to fetch asteroids from the network: ${t.message}")
////            }
////
////            override fun onResponse(call: Call<AsteroidData>, response: Response<AsteroidData>) {
////                val asteroidResponse= response.body()
////                Log.d(TAG, "${asteroidResponse?.elementCount.toString()} asteroids retrieved")
////            }
////        })
//
//        viewModelScope.launch {
//            try {
//                val listResult = AsteroidApi.asteroidService.getAsteroids("2021-05-05", "2021-05-06", API_KEY)   // 2021-5-5 WRONG, 2021-05-05 RIGHT
//                _status.value = "Success: ${listResult.length} Asteroids retrieved"
////                val listResultJSON = JSONObject(listResult)
////                val asteroidList: ArrayList<Asteroid> = parseAsteroidsJsonResult(listResultJSON)
//
//                val asteroidList: ArrayList<Asteroid> = parseAsteroidsJsonResult(JSONObject(listResult)) as ArrayList<Asteroid>
//
//                if (asteroidList.isNotEmpty()) {
//                    _asteroids.value = asteroidList
//                }
//
//            } catch (e: Exception) {
//                _status.value = "Failure: ${e.message}"
//            }
//            Log.d(TAG, _status.toString())
//        }
//    }

    val asteroid = asteroidRepository.asteroids
    val pictureOfTheDay = pictureOfTheDayRepository.pictureOfTheDay

    // initiate navigation to the detail screen
    fun displayAsteroidDetails(asteroid: Asteroid) {
        _navigateToSelectedAsteroid.value = asteroid
    }

    fun displayAsteroidDetailsComplete() {
        _navigateToSelectedAsteroid.value = null
    }

//    private fun getPictureOfTheDay() {
//
//        viewModelScope.launch {
//            try {
//                val pictureOfTheDay = AsteroidApi.pictureOdTheDayService.getPictureOfTheDay(API_KEY)
//                _pictureOfTheDay.value = pictureOfTheDay
//                Log.d(TAG, pictureOfTheDay.url)
//            } catch (e: Exception) {
//                Log.d(TAG, "PictureOfTheDay retrieval unsuccessful: ${e.message}")
//            }
//        }
//    }

    class OverviewViewModelFactory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(OverviewViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return OverviewViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct ViewModel")
        }
    }

}
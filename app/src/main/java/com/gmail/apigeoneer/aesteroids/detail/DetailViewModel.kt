package com.gmail.apigeoneer.aesteroids.detail

import android.app.Application
import androidx.lifecycle.*
import com.gmail.apigeoneer.aesteroids.data.domain.Asteroid

class DetailViewModel(
        asteroid: Asteroid,
        app: Application
): AndroidViewModel(app) {

    // the asteroid item user clicks on in the overview screen
    private val _selectedAsteroid = MutableLiveData<Asteroid>()
    val selectedAsteroid: LiveData<Asteroid>
        get() = _selectedAsteroid

    init {
        _selectedAsteroid.value = asteroid
    }

    class DetailViewModelFactory(
        private val asteroid: Asteroid,
        private val app: Application) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
                return DetailViewModel(asteroid, app) as T
            }
            throw IllegalArgumentException("Unable to construct ViewModel")
        }
    }
}
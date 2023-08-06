package com.gmail.apigeoneer.aesteroids.data.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * Parcelable is a serialization mechanism provided by Android to pass complex data from one activity to another activity.
 * In order to write an object to a Parcel, that object should implement the interface “Parcelable“.
 */

// Make Asteroid parcelable so it can be passed as an argument in navigation
@Parcelize
data class Asteroid(
    val id: Long,
    @Json(name = "name") val codeName: String,
    @Json(name = "close_approach_date") val closeApproachDate: String,
    @Json(name = "absolute_magnitude") val absMagnitude: Double,
    @Json(name = "estimated_diameter_max") val estDiaMax: Double,
    @Json(name = "is_potentially_hazardous_asteroid") val isHazardous: Boolean,
    @Json(name = "kilometers_per_second") val relativeVelocity: Double,
    @Json(name = "astronomical") val distanceFromEarth: Double
): Parcelable
package com.gmail.apigeoneer.aesteroids.network

import android.annotation.SuppressLint
import com.gmail.apigeoneer.aesteroids.Constants
import com.gmail.apigeoneer.aesteroids.data.domain.Asteroid
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

//fun parseAsteroidsJsonResult(jsonResult: JSONObject): ArrayList<Asteroid> {
//    val nearEarthObjectsJson = jsonResult.getJSONObject("near_earth_objects")
//
//    val asteroidList = ArrayList<Asteroid>()
//
//    val nextSevenDaysFormattedDates = getNextSevenDaysFormattedDates()
//    for (formattedDate in nextSevenDaysFormattedDates) {
//        if (nearEarthObjectsJson.has(formattedDate)) {
//            val dateAsteroidJsonArray = nearEarthObjectsJson.getJSONArray(formattedDate)
//
//            for (i in 0 until dateAsteroidJsonArray.length()) {
//                val asteroidJson = dateAsteroidJsonArray.getJSONObject(i)
//                val id = asteroidJson.getLong("id")
//                val codename = asteroidJson.getString("name")
//                val absoluteMagnitude = asteroidJson.getDouble("absolute_magnitude_h")
//                val estimatedDiameter = asteroidJson.getJSONObject("estimated_diameter")
//                        .getJSONObject("kilometers").getDouble("estimated_diameter_max")
//
//                val closeApproachData = asteroidJson
//                        .getJSONArray("close_approach_data").getJSONObject(0)
//                val relativeVelocity = closeApproachData.getJSONObject("relative_velocity")
//                        .getDouble("kilometers_per_second")
//                val distanceFromEarth = closeApproachData.getJSONObject("miss_distance")
//                        .getDouble("astronomical")
//                val isPotentiallyHazardous = asteroidJson
//                        .getBoolean("is_potentially_hazardous_asteroid")
//
//                val asteroid = Asteroid(id, codename, formattedDate, absoluteMagnitude,
//                        estimatedDiameter, isPotentiallyHazardous, relativeVelocity, distanceFromEarth)
//                asteroidList.add(asteroid)
//            }
//        }
//    }
//
//    return asteroidList
//}

/**
 * When there is no value for an Asteroid for the given date, the above fun throws the JsonException,
 * which is caught in our OverviewViewModel. When this exception is thrown,
 * the previous parsed data also will be lost due to the Exception. Hence, we use this approach, wherein we would parse
 * the JSON response as it is like below instead of trying to fetch for the next 7 days or so
 * wherein sometimes there can be no Asteroid for that day and trying to extract one would throw such exceptions
 */
fun parseAsteroidsJsonResult(jsonObject: JSONObject): List<Asteroid> {
    val asteroidList = mutableListOf<Asteroid>()
    val nearEarthObjectsJson = jsonObject.getJSONObject("near_earth_objects")
    val dateList = nearEarthObjectsJson.keys()
    val dateListSorted = dateList.asSequence().sorted()
    dateListSorted.forEach {
        val key: String = it
        val dateAsteroidJsonArray = nearEarthObjectsJson.getJSONArray(key)
        for (i in 0 until dateAsteroidJsonArray.length()) {
            val asteroidJson = dateAsteroidJsonArray.getJSONObject(i)
            val id = asteroidJson.getLong("id")
            val codename = asteroidJson.getString("name")
            val absoluteMagnitude = asteroidJson.getDouble("absolute_magnitude_h")
            val estimatedDiameter = asteroidJson.getJSONObject("estimated_diameter")
                .getJSONObject("kilometers").getDouble("estimated_diameter_max")
            val closeApproachData = asteroidJson
                .getJSONArray("close_approach_data").getJSONObject(0)
            val relativeVelocity = closeApproachData.getJSONObject("relative_velocity")
                .getDouble("kilometers_per_second")
            val distanceFromEarth = closeApproachData.getJSONObject("miss_distance")
                .getDouble("astronomical")
            val isPotentiallyHazardous = asteroidJson
                .getBoolean("is_potentially_hazardous_asteroid")
            val asteroid = Asteroid(
                id,
                codename,
                key,
                absoluteMagnitude,
                estimatedDiameter,
                isPotentiallyHazardous,
                relativeVelocity,
                distanceFromEarth
            )
            asteroidList.add(asteroid)
        }
    }
    return asteroidList
}

@SuppressLint("WeekBasedYear")
private fun getNextSevenDaysFormattedDates(): ArrayList<String> {
    val formattedDateList = ArrayList<String>()

    val calendar = Calendar.getInstance()
    for (i in 0..Constants.DEFAULT_END_DATE_DAYS) {
        val currentTime = calendar.time
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        formattedDateList.add(dateFormat.format(currentTime))
        calendar.add(Calendar.DAY_OF_YEAR, 1)
    }
    return formattedDateList
}

@SuppressLint("WeekBasedYear")
fun getFormattedToday(): String {
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
    return dateFormat.format(calendar.time)
}

@SuppressLint("WeekBasedYear")
fun getFormattedSeventhDay(): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, 7)
    val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
    return dateFormat.format(calendar.time)
}
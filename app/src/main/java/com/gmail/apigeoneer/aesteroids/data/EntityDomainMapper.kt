package com.gmail.apigeoneer.aesteroids.data

import com.gmail.apigeoneer.aesteroids.data.domain.Asteroid
import com.gmail.apigeoneer.aesteroids.data.domain.PictureOfTheDay
import com.gmail.apigeoneer.aesteroids.data.entities.AsteroidEntity
import com.gmail.apigeoneer.aesteroids.data.entities.PictureOfTheDayEntity

fun List<Asteroid>.toDatabaseModel(): Array<AsteroidEntity>{

    return map{
        AsteroidEntity(
                id = it.id,
                codeName = it.codeName,
                closeApproachDate = it.closeApproachDate,
                absMagnitude = it.absMagnitude,
                estDiaMax = it.estDiaMax,
                relativeVelocity = it.relativeVelocity,
                distanceFromEarth = it.distanceFromEarth,
                isHazardous = it.isHazardous
        )
    }.toTypedArray()
}

fun List<AsteroidEntity>.toDomainModel(): List<Asteroid>{
    return map{
        Asteroid(
                id = it.id,
                codeName = it.codeName,
                closeApproachDate = it.closeApproachDate,
                absMagnitude = it.absMagnitude,
                estDiaMax = it.estDiaMax,
                relativeVelocity = it.relativeVelocity,
                distanceFromEarth = it.distanceFromEarth,
                isHazardous = it.isHazardous
        )
    }
}

fun PictureOfTheDay.toDatabaseModel(): PictureOfTheDayEntity {
    return PictureOfTheDayEntity(
            mediaType = this.mediaType,
            title = this.title,
            url = this.url
    )
}

fun PictureOfTheDayEntity.toDomainModel(): PictureOfTheDay{
    return PictureOfTheDay(
            mediaType = this.mediaType,
            title = this.title,
            url = this.url
    )
}
package id.lukasdylan.jelajahindonesia.model

import com.squareup.moshi.JsonClass

/**
 * Created by Lukas Dylan on 21/10/20.
 */
@JsonClass(generateAdapter = true)
data class Coordinates(
    val longitude: Double,
    val latitude: Double
) {

    constructor(doubleArray: DoubleArray) : this(
        longitude = doubleArray[0],
        latitude = doubleArray[1]
    )

    fun isNoLocation(): Boolean = longitude == 0.0 && latitude == 0.0
}
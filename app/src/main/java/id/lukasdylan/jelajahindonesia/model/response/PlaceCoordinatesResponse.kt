package id.lukasdylan.jelajahindonesia.model.response

import androidx.annotation.Keep
import com.squareup.moshi.JsonClass
import id.lukasdylan.jelajahindonesia.model.Coordinates
import id.lukasdylan.jelajahindonesia.model.contract.PlaceCoordinates

/**
 * Created by Lukas Dylan on 24/10/20.
 */
@JsonClass(generateAdapter = true)
@Keep
data class PlaceCoordinatesResponse(
    override val name: String,
    val lat: Double?,
    val lon: Double?
) : PlaceCoordinates {
    override val coordinates: Coordinates
        get() = Coordinates(latitude = lat ?: 0.0, longitude = lon ?: 0.0)
}
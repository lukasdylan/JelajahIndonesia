package id.lukasdylan.jelajahindonesia.model.response

import androidx.annotation.Keep
import com.squareup.moshi.JsonClass

/**
 * Created by Lukas Dylan on 24/10/20.
 */
@JsonClass(generateAdapter = true)
@Keep
data class DetailTourismPlaceCoordinatesResponse(
    val lat: Double?,
    val lon: Double?
)
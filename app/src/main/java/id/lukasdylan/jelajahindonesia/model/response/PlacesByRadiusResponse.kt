package id.lukasdylan.jelajahindonesia.model.response

import androidx.annotation.Keep
import com.squareup.moshi.JsonClass

/**
 * Created by Lukas Dylan on 21/10/20.
 */
@JsonClass(generateAdapter = true)
@Keep
data class PlacesByRadiusResponse(
    val features: List<TourismPlaceResponse>?
)
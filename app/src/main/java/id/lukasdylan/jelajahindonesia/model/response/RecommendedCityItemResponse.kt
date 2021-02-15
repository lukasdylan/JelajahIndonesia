package id.lukasdylan.jelajahindonesia.model.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import id.lukasdylan.jelajahindonesia.model.Coordinates
import id.lukasdylan.jelajahindonesia.model.contract.TourismPlace

/**
 * Created by Lukas Dylan on 07/11/20.
 */
@JsonClass(generateAdapter = true)
@Keep
data class RecommendedCityItemResponse(
    override val id: String,
    override val name: String,
    @Json(name = "image_url") override val imageUrl: String,
    val longitude: Double,
    val latitude: Double
) : TourismPlace {
    override val distance: Double
        get() = 0.0
    override val tags: List<String>
        get() = emptyList()
    override val rate: Int
        get() = 0
    override val coordinates: Coordinates
        get() = Coordinates(longitude, latitude)
}
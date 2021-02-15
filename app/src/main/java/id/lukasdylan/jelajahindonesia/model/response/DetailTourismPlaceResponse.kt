package id.lukasdylan.jelajahindonesia.model.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import id.lukasdylan.jelajahindonesia.model.Coordinates
import id.lukasdylan.jelajahindonesia.model.contract.DetailTourismPlace
import id.lukasdylan.jelajahindonesia.utils.asTags

/**
 * Created by Lukas Dylan on 24/10/20.
 */
@JsonClass(generateAdapter = true)
@Keep
data class DetailTourismPlaceResponse(
    override val name: String,
    val kinds: String?,
    @Json(name = "rate") val _rate: String?,
    val point: DetailTourismPlaceCoordinatesResponse?,
    @Json(name = "address") val _address: DetailTourismPlaceAddressResponse?,
    val preview: DetailTourismPlaceImagePreviewResponse?,
    @Json(name = "wikipedia_extracts") val wikipediaInfo: DetailTourismPlaceInfoResponse?,
    @Json(name = "wikipedia") override val wikipediaLink: String,
    @Json(name = "xid") override val id: String
) : DetailTourismPlace {
    override val imageUrl: String
        get() = preview?.source.orEmpty()
    override val address: String
        get() = _address?.getAddress().orEmpty()
    override val description: String
        get() = wikipediaInfo?.text ?: "Tidak ada deskripsi"
    override val distance: Double
        get() = 0.0
    override val tags: List<String>
        get() = kinds.asTags()
    override val rate: Int
        get() = try {
            _rate?.toInt() ?: 0
        } catch (ex: Exception) {
            ex.printStackTrace()
            0
        }
    override val coordinates: Coordinates
        get() = point?.let { Coordinates(longitude = it.lon ?: 0.0, latitude = it.lat ?: 0.0) }
            ?: Coordinates(0.0, 0.0)
}
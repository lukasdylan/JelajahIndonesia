package id.lukasdylan.jelajahindonesia.model.response

import androidx.annotation.Keep
import com.squareup.moshi.JsonClass
import id.lukasdylan.jelajahindonesia.model.Coordinates
import id.lukasdylan.jelajahindonesia.model.contract.TourismPlace
import id.lukasdylan.jelajahindonesia.utils.asTags

/**
 * Created by Lukas Dylan on 20/10/20.
 */
@JsonClass(generateAdapter = true)
@Keep
data class TourismPlaceResponse(
    val geometry: TourismPlaceGeometryResponse?,
    val properties: TourismPlacePropertiesResponse?
) : TourismPlace {
    override val id: String
        get() = properties?.xid.orEmpty()
    override val name: String
        get() = properties?.name.orEmpty()
    override val coordinates: Coordinates
        get() = Coordinates(geometry?.coordinates ?: arrayOf(0.0, 0.0).toDoubleArray())
    override val distance: Double
        get() = properties?.dist ?: 0.0
    override val tags: List<String>
        get() = properties?.kinds.asTags()
    override val rate: Int
        get() = properties?.rate ?: 0
    override val imageUrl: String
        get() = ""
}
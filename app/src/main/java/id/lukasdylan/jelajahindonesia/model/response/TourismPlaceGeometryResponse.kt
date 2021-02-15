package id.lukasdylan.jelajahindonesia.model.response

import androidx.annotation.Keep
import com.squareup.moshi.JsonClass

/**
 * Created by Lukas Dylan on 20/10/20.
 */
@JsonClass(generateAdapter = true)
@Keep
data class TourismPlaceGeometryResponse(
    val coordinates: DoubleArray?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TourismPlaceGeometryResponse

        if (!coordinates.contentEquals(other.coordinates)) return false

        return true
    }

    override fun hashCode(): Int {
        return coordinates.contentHashCode()
    }
}
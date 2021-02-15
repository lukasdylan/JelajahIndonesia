package id.lukasdylan.jelajahindonesia.model.response

import androidx.annotation.Keep
import com.squareup.moshi.JsonClass

/**
 * Created by Lukas Dylan on 20/10/20.
 */
@JsonClass(generateAdapter = true)
@Keep
data class TourismPlacePropertiesResponse(
    val xid: String?,
    val name: String?,
    val dist: Double?,
    val kinds: String?,
    val rate: Int?
)
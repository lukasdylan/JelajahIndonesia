package id.lukasdylan.jelajahindonesia.model.response

import androidx.annotation.Keep
import com.squareup.moshi.JsonClass

/**
 * Created by Lukas Dylan on 08/11/20.
 */
@JsonClass(generateAdapter = true)
@Keep
data class RecommendedCityResponse(
    val title: String = "",
    val subtitle: String = "",
    val data: List<RecommendedCityItemResponse> = emptyList()
)
package id.lukasdylan.jelajahindonesia.model.response

import androidx.annotation.Keep
import com.squareup.moshi.JsonClass

/**
 * Created by Lukas Dylan on 07/11/20.
 */
@JsonClass(generateAdapter = true)
@Keep
data class PopularDestinationResponse(
    val title: String = "",
    val data: List<PopularDestinationItemResponse> = emptyList()
)
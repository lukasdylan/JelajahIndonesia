package id.lukasdylan.jelajahindonesia.model.response

import androidx.annotation.Keep
import com.squareup.moshi.JsonClass

/**
 * Created by Lukas Dylan on 24/10/20.
 */
@JsonClass(generateAdapter = true)
@Keep
data class DetailTourismPlaceAddressResponse(
    val state: String?,
    val country: String?,
    val village: String?,
    val postcode: String?
) {

    fun getAddress(): String {
        val stringBuilder = StringBuilder()
        if (!village.isNullOrBlank()) {
            stringBuilder.append("$village, ")
        }
        if (!state.isNullOrBlank()) {
            stringBuilder.append("$state, ")
        }
        if (!postcode.isNullOrBlank()) {
            stringBuilder.append("$postcode, ")
        }
        if (!country.isNullOrBlank()) {
            stringBuilder.append(country)
        }
        return stringBuilder.toString()
    }
}
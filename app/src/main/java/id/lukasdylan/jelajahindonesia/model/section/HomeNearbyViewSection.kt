package id.lukasdylan.jelajahindonesia.model.section

import android.content.Context
import android.location.Geocoder
import id.lukasdylan.jelajahindonesia.model.Coordinates
import id.lukasdylan.jelajahindonesia.model.contract.TourismPlace
import id.lukasdylan.jelajahindonesia.utils.StringWrapper
import java.io.IOException

/**
 * Created by Lukas Dylan on 30/10/20.
 */
data class HomeNearbyViewSection(
    override val data: List<TourismPlace> = emptyList(),
    override val isLoading: Boolean = true,
    override val errorType: ViewSection.ErrorType = ViewSection.ErrorType.NONE,
    val userCoordinates: Coordinates = Coordinates(0.0, 0.0)
) : ViewSection<TourismPlace> {

    override val id: Int
        get() = ViewSection.NEARBY_PLACES_ID
    override val title: StringWrapper
        get() = StringWrapper.StringType("Sekitar Kamu")

    private var userLocationName = ""

    fun getUserLocationName(context: Context): String {
        if (userLocationName.isNotBlank()) return userLocationName
        if (userCoordinates.isNoLocation()) return ""
        return try {
            Geocoder(context).getFromLocation(
                userCoordinates.latitude,
                userCoordinates.longitude,
                1
            ).firstOrNull()?.subAdminArea.orEmpty().also {
                userLocationName = it
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
            userLocationName
        }
    }
}
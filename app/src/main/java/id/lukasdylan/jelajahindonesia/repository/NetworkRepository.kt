package id.lukasdylan.jelajahindonesia.repository

import id.lukasdylan.jelajahindonesia.model.contract.DetailTourismPlace
import id.lukasdylan.jelajahindonesia.model.contract.PlaceCoordinates
import id.lukasdylan.jelajahindonesia.model.contract.TourismPlace
import id.lukasdylan.jelajahindonesia.service.OpenTripMapApiService
import id.lukasdylan.jelajahindonesia.utils.DataResult
import id.lukasdylan.jelajahindonesia.utils.map
import javax.inject.Inject

/**
 * Created by Lukas Dylan on 21/10/20.
 */
interface NetworkRepository {
    suspend fun getPlacesByCoordinates(
        longitude: Double,
        latitude: Double,
        limit: Int
    ): DataResult<List<TourismPlace>>

    suspend fun getPlaceCoordinatesByName(placeName: String): DataResult<PlaceCoordinates>

    suspend fun getPlaceDetail(xid: String): DataResult<DetailTourismPlace>
}

class NetworkRepositoryImpl @Inject constructor(private val apiService: OpenTripMapApiService) :
    NetworkRepository {

    override suspend fun getPlacesByCoordinates(
        longitude: Double,
        latitude: Double,
        limit: Int
    ): DataResult<List<TourismPlace>> {
        return apiService.getPlacesByCoordinate(
            longitude = longitude,
            latitude = latitude,
            radius = 50000,
            limit = limit,
            minRate = "3"
        ).map {
            it.features.orEmpty()
        }
    }

    override suspend fun getPlaceCoordinatesByName(placeName: String): DataResult<PlaceCoordinates> {
        return apiService.getPlaceCoordinatesByName(name = placeName, countryId = "ID")
    }

    override suspend fun getPlaceDetail(xid: String): DataResult<DetailTourismPlace> {
        return apiService.getPlaceDetail(xid)
    }
}
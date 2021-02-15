package id.lukasdylan.jelajahindonesia.service

import id.lukasdylan.jelajahindonesia.model.response.DetailTourismPlaceResponse
import id.lukasdylan.jelajahindonesia.model.response.PlaceCoordinatesResponse
import id.lukasdylan.jelajahindonesia.model.response.PlacesByRadiusResponse
import id.lukasdylan.jelajahindonesia.utils.DataResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Lukas Dylan on 21/10/20.
 */
interface OpenTripMapApiService {

    @GET("/0.1/en/places/radius")
    suspend fun getPlacesByCoordinate(
        @Query("radius") radius: Int,
        @Query("lon") longitude: Double,
        @Query("lat") latitude: Double,
        @Query("rate") minRate: String,
        @Query("limit") limit: Int
    ): DataResult<PlacesByRadiusResponse>

    @GET("/0.1/en/places/geoname")
    suspend fun getPlaceCoordinatesByName(
        @Query("name") name: String,
        @Query("country") countryId: String
    ): DataResult<PlaceCoordinatesResponse>

    @GET("/0.1/en/places/xid/{xid}")
    suspend fun getPlaceDetail(@Path("xid") xid: String): DataResult<DetailTourismPlaceResponse>
}
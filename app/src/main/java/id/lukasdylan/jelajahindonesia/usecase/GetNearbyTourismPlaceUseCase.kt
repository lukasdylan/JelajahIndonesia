package id.lukasdylan.jelajahindonesia.usecase

import id.lukasdylan.jelajahindonesia.intention.ActionResult
import id.lukasdylan.jelajahindonesia.model.Coordinates
import id.lukasdylan.jelajahindonesia.model.contract.TourismPlace
import id.lukasdylan.jelajahindonesia.repository.LocationRepository
import id.lukasdylan.jelajahindonesia.repository.NetworkRepository
import id.lukasdylan.jelajahindonesia.utils.DataResult
import id.lukasdylan.jelajahindonesia.utils.map
import javax.inject.Inject

/**
 * Created by Lukas Dylan on 24/10/20.
 */
class GetNearbyTourismPlaceUseCase @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val userLocationRepository: LocationRepository,
) : BaseUseCase<GetNearbyTourismPlaceUseCase.Param, Pair<List<TourismPlace>, Coordinates>, GetNearbyTourismPlaceUseCase.UseCaseResult>() {

    override suspend fun execute(param: Param? ): DataResult<Pair<List<TourismPlace>, Coordinates>> {
        return when (val locationResult = userLocationRepository.getUserLocation()) {
            is DataResult.Success -> {
                networkRepository.getPlacesByCoordinates(
                    latitude = locationResult.data.latitude,
                    longitude = locationResult.data.longitude,
                    limit = param?.limit ?: 30
                ).map {
                    it to Coordinates(
                        longitude = locationResult.data.longitude,
                        latitude = locationResult.data.latitude
                    )
                }
            }
            is DataResult.Failure -> DataResult.Failure(locationResult.error)
        }
    }

    override suspend fun DataResult<Pair<List<TourismPlace>, Coordinates>>.transformToResult(): UseCaseResult {
        return when (this) {
            is DataResult.Success -> {
                if (this.data.first.isNullOrEmpty()) {
                    UseCaseResult.Empty
                } else {
                    UseCaseResult.Success(this.data.first, this.data.second)
                }
            }
            is DataResult.Failure -> {
                if (this.error is SecurityException) {
                    UseCaseResult.LocationPermissionNotGranted
                } else {
                    UseCaseResult.Failed(this.error.message.orEmpty())
                }
            }
        }
    }

    data class Param(val limit: Int)

    sealed class UseCaseResult : ActionResult {
        data class Success(val data: List<TourismPlace>, val userCoordinate: Coordinates) :
            UseCaseResult()

        data class Failed(val reason: String) : UseCaseResult()
        object LocationPermissionNotGranted : UseCaseResult()
        object Empty : UseCaseResult()
    }
}
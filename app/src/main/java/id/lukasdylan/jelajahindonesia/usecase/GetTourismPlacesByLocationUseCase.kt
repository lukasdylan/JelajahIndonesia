package id.lukasdylan.jelajahindonesia.usecase

import id.lukasdylan.jelajahindonesia.model.Coordinates
import id.lukasdylan.jelajahindonesia.model.contract.TourismPlace
import id.lukasdylan.jelajahindonesia.repository.NetworkRepository
import id.lukasdylan.jelajahindonesia.utils.DataResult
import javax.inject.Inject

/**
 * Created by Lukas Dylan on 24/10/20.
 */
class GetTourismPlacesByLocationUseCase @Inject constructor(
    private val networkRepository: NetworkRepository
) : BaseUseCase<GetTourismPlacesByLocationUseCase.Param, List<TourismPlace>, GetTourismPlacesByLocationUseCase.UseCaseResult>() {

    override suspend fun execute(param: Param?): DataResult<List<TourismPlace>> {
        if (param == null) return DataResult.Failure(Throwable("Param cannot be null"))
        return networkRepository.getPlacesByCoordinates(
            latitude = param.coordinates.latitude,
            longitude = param.coordinates.longitude,
            limit = param.limit
        )
    }

    override suspend fun DataResult<List<TourismPlace>>.transformToResult(): UseCaseResult {
        return when (this) {
            is DataResult.Success -> {
                if (this.data.isNullOrEmpty()) {
                    UseCaseResult.Empty
                } else {
                    UseCaseResult.Success(this.data)
                }
            }
            is DataResult.Failure -> UseCaseResult.Failed(this.error.message.orEmpty())
        }
    }

    data class Param(val coordinates: Coordinates, val limit: Int)

    sealed class UseCaseResult {
        data class Success(val data: List<TourismPlace>) : UseCaseResult()
        data class Failed(val reason: String) : UseCaseResult()
        object Empty : UseCaseResult()
    }
}
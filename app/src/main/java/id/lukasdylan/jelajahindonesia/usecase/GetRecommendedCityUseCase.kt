package id.lukasdylan.jelajahindonesia.usecase

import id.lukasdylan.jelajahindonesia.intention.ActionResult
import id.lukasdylan.jelajahindonesia.model.contract.TourismPlace
import id.lukasdylan.jelajahindonesia.model.response.RecommendedCityResponse
import id.lukasdylan.jelajahindonesia.repository.RemoteConfigRepository
import id.lukasdylan.jelajahindonesia.utils.DataResult
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Lukas Dylan on 07/11/20.
 */
class GetRecommendedCityUseCase @Inject constructor(private val remoteConfigRepository: RemoteConfigRepository) :
    BaseUseCase<Nothing, RecommendedCityResponse, GetRecommendedCityUseCase.UseCaseResult>() {

    override suspend fun execute(param: Nothing?): DataResult<RecommendedCityResponse> {
        Timber.d("Start Recommended")
        return remoteConfigRepository.getRecommendedCity()
    }

    override suspend fun DataResult<RecommendedCityResponse>.transformToResult(): UseCaseResult {
        return when (this) {
            is DataResult.Success -> {
                UseCaseResult.Success(
                    data = this.data.data,
                    title = this.data.title,
                    subtitle = this.data.subtitle
                )
            }
            is DataResult.Failure -> {
                UseCaseResult.Failed(reason = this.error.message.orEmpty())
            }
        }
    }

    sealed class UseCaseResult : ActionResult {
        data class Success(val data: List<TourismPlace>, val title: String, val subtitle: String) :
            UseCaseResult()

        data class Failed(val reason: String) : UseCaseResult()
    }
}
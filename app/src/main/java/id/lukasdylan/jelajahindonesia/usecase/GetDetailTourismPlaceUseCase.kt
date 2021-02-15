package id.lukasdylan.jelajahindonesia.usecase

import id.lukasdylan.jelajahindonesia.model.contract.DetailTourismPlace
import id.lukasdylan.jelajahindonesia.repository.NetworkRepository
import id.lukasdylan.jelajahindonesia.utils.DataResult
import javax.inject.Inject

/**
 * Created by Lukas Dylan on 24/10/20.
 */
class GetDetailTourismPlaceUseCase @Inject constructor(
    private val networkRepository: NetworkRepository
) : BaseUseCase<GetDetailTourismPlaceUseCase.Param, DetailTourismPlace, GetDetailTourismPlaceUseCase.UseCaseResult>() {

    override suspend fun execute(param: Param?): DataResult<DetailTourismPlace> {
        if (param == null) return DataResult.Failure(Throwable("Param cannot be null"))
        return networkRepository.getPlaceDetail(xid = param.xid)
    }

    override suspend fun DataResult<DetailTourismPlace>.transformToResult(): UseCaseResult {
        return when (this) {
            is DataResult.Success -> UseCaseResult.Success(this.data)
            is DataResult.Failure -> UseCaseResult.Failed(this.error.message.orEmpty())
        }
    }

    data class Param(val xid: String)

    sealed class UseCaseResult {
        data class Success(val data: DetailTourismPlace) : UseCaseResult()
        data class Failed(val reason: String) : UseCaseResult()
    }
}
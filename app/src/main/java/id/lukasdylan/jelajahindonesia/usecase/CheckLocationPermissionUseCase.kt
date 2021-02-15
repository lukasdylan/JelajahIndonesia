package id.lukasdylan.jelajahindonesia.usecase

import id.lukasdylan.jelajahindonesia.intention.ActionResult
import id.lukasdylan.jelajahindonesia.repository.PreferencesRepository
import id.lukasdylan.jelajahindonesia.utils.DataResult
import javax.inject.Inject

/**
 * Created by Lukas Dylan on 01/02/21.
 */
class CheckLocationPermissionUseCase @Inject constructor(private val preferencesRepository: PreferencesRepository) :
    BaseUseCase<Nothing, Boolean, CheckLocationPermissionUseCase.UseCaseResult>() {

    data class UseCaseResult(val needShow: Boolean) : ActionResult

    override suspend fun execute(param: Nothing?): DataResult<Boolean> {
        return DataResult.Success(preferencesRepository.isNeedToShowLocationPermission())
    }

    override suspend fun DataResult<Boolean>.transformToResult(): UseCaseResult {
        val needShow = (this as? DataResult.Success)?.data ?: false
        return UseCaseResult(needShow)
    }
}
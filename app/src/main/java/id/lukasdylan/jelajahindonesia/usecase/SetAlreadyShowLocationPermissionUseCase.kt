package id.lukasdylan.jelajahindonesia.usecase

import id.lukasdylan.jelajahindonesia.repository.PreferencesRepository
import id.lukasdylan.jelajahindonesia.utils.DataResult
import javax.inject.Inject

/**
 * Created by Lukas Dylan on 01/02/21.
 */
class SetAlreadyShowLocationPermissionUseCase @Inject constructor(private val preferencesRepository: PreferencesRepository) :
    BaseUseCase<Nothing, Unit, Unit>() {

    override suspend fun execute(param: Nothing?): DataResult<Unit> {
        preferencesRepository.setAlreadyShowLocationPermission()
        return DataResult.Success(Unit)
    }

    override suspend fun DataResult<Unit>.transformToResult() {
        // nothing to do
    }
}
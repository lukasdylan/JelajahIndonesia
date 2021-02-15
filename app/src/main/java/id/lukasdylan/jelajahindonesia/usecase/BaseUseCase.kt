package id.lukasdylan.jelajahindonesia.usecase

import id.lukasdylan.jelajahindonesia.utils.DataResult
import kotlinx.coroutines.*

/**
 * Created by Lukas Dylan on 24/10/20.
 */
abstract class BaseUseCase<RequestParam, ResponseObject, UseCaseResult> {

    protected abstract suspend fun execute(param: RequestParam?): DataResult<ResponseObject>

    protected abstract suspend fun DataResult<ResponseObject>.transformToResult(): UseCaseResult

    suspend fun getResult(param: RequestParam? = null): UseCaseResult =
        withContext(Dispatchers.IO) {
            execute(param).transformToResult()
        }

    suspend fun getDeferredResult(
        coroutineScope: CoroutineScope,
        param: RequestParam? = null
    ): Deferred<UseCaseResult> = coroutineScope.async(Dispatchers.IO) {
        execute(param).transformToResult()
    }
}
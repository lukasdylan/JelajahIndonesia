package id.lukasdylan.jelajahindonesia.utils

/**
 * Created by Lukas Dylan on 23/10/20.
 */
sealed class DataResult<out T> {

    data class Success<T>(val data: T) : DataResult<T>()

    data class Failure(val error: Throwable) : DataResult<Nothing>()
}

inline fun <T, V> DataResult<T>.map(block: (T) -> V): DataResult<V> =
    when (this) {
        is DataResult.Success -> DataResult.Success(block(data))
        is DataResult.Failure -> this
    }

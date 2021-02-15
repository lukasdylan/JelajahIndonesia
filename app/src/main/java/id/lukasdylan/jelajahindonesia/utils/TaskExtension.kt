package id.lukasdylan.jelajahindonesia.utils

import com.google.android.gms.tasks.Task
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Created by Lukas Dylan on 26/10/20.
 */
suspend fun <T> Task<T>.awaitResult() = suspendCoroutine<DataResult<T>> { continuation ->
    if (isComplete) {
        if (isSuccessful) continuation.resume(DataResult.Success(result))
        else continuation.resume(DataResult.Failure(Throwable(exception)))
        return@suspendCoroutine
    }
    addOnSuccessListener { continuation.resume(DataResult.Success(it)) }
    addOnFailureListener { continuation.resume(DataResult.Failure(it)) }
    addOnCanceledListener { continuation.resume(DataResult.Failure(Throwable("Cancelled"))) }
}
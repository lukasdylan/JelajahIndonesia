package id.lukasdylan.jelajahindonesia.utils.network

/**
 * Created by Lukas Dylan on 23/10/20.
 */
data class NetworkFailureException(val httpStatusCode: Int, val reason: Throwable) :
    Exception(reason)
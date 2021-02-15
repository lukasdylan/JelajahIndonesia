package id.lukasdylan.jelajahindonesia.utils.network

import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

/**
 * Created by Lukas Dylan on 13/01/21.
 */
fun networkLoggingInterceptor(tagName: String) =
    HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            Timber.tag(tagName).i(message)
        }
    })
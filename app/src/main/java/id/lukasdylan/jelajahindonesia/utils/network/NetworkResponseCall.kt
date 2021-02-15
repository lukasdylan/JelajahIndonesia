package id.lukasdylan.jelajahindonesia.utils.network

import id.lukasdylan.jelajahindonesia.utils.DataResult
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

/**
 * Created by Lukas Dylan on 23/10/20.
 */
class NetworkResponseCall<S>(private val delegate: Call<S>) :
    Call<DataResult<S>> {

    override fun clone(): Call<DataResult<S>> = NetworkResponseCall(delegate.clone())

    override fun execute(): Response<DataResult<S>> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun enqueue(callback: Callback<DataResult<S>>) =
        delegate.enqueue(object : Callback<S> {

            override fun onResponse(call: Call<S>, response: Response<S>) {
                val networkResponse = if (response.isSuccessful) {
                    response.body()?.let { body -> DataResult.Success(body) }
                        ?: DataResult.Failure(Throwable("Terjadi kesalahan, silahkan coba lagi."))
                } else {
                    DataResult.Failure(
                        NetworkFailureException(
                            response.code(),
                            Throwable(response.message())
                        )
                    )
                }
                callback.onResponse(this@NetworkResponseCall, Response.success(networkResponse))
            }

            override fun onFailure(call: Call<S>, t: Throwable) {
                val networkResponse = DataResult.Failure(t)
                callback.onResponse(
                    this@NetworkResponseCall,
                    Response.success(networkResponse)
                )
            }

        })

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() {
        delegate.cancel()
    }

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout {
        throw NetworkFailureException(
            HttpURLConnection.HTTP_CLIENT_TIMEOUT,
            Throwable("Terjadi kesalahan, silahkan coba lagi")
        )
    }
}
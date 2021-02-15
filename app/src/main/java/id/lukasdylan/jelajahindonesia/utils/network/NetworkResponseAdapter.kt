package id.lukasdylan.jelajahindonesia.utils.network

import id.lukasdylan.jelajahindonesia.utils.DataResult
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class NetworkResponseAdapter<S : Any>(private val successType: Type) :
    CallAdapter<S, Call<DataResult<S>>> {

    override fun responseType(): Type = successType

    override fun adapt(call: Call<S>): Call<DataResult<S>> = NetworkResponseCall(call)
}
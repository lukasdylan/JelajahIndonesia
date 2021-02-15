package id.lukasdylan.jelajahindonesia.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import id.lukasdylan.jelajahindonesia.service.OpenTripMapApiService
import id.lukasdylan.jelajahindonesia.utils.network.NetworkResponseAdapterFactory
import id.lukasdylan.jelajahindonesia.utils.network.networkLoggingInterceptor
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
 * Created by Lukas Dylan on 21/10/20.
 */
@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        return OkHttpClient().newBuilder().apply {
            retryOnConnectionFailure(true)
            cache(Cache(context.cacheDir, 30 * 1024 * 1024))
            addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    var request: Request = chain.request()
                    val url: HttpUrl =
                        request.url.newBuilder().addQueryParameter(
                            "apikey",
                            "5ae2e3f221c38a28845f05b6695dfd91d8f10822d1dbde4ef40ff3a5"
                        ).build()
                    request = request.newBuilder().url(url).build()
                    return chain.proceed(request)
                }
            })
            addInterceptor(networkLoggingInterceptor("ApiRequest"))
            connectTimeout(15, TimeUnit.SECONDS)
            readTimeout(15, TimeUnit.SECONDS)
            writeTimeout(15, TimeUnit.SECONDS)
        }.build()
    }

    @Provides
    @Singleton
    fun provideOpenTripMapApiService(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): OpenTripMapApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.opentripmap.com/")
            .client(okHttpClient)
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(OpenTripMapApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(@ApplicationContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }
}
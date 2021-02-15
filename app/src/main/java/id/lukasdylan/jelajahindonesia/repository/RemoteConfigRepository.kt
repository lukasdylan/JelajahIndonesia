package id.lukasdylan.jelajahindonesia.repository

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import id.lukasdylan.jelajahindonesia.BuildConfig
import id.lukasdylan.jelajahindonesia.R
import id.lukasdylan.jelajahindonesia.model.response.PopularDestinationResponse
import id.lukasdylan.jelajahindonesia.model.response.RecommendedCityResponse
import id.lukasdylan.jelajahindonesia.utils.DataResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by Lukas Dylan on 07/11/20.
 */
interface RemoteConfigRepository {
    suspend fun getPopularDestinations(): DataResult<PopularDestinationResponse>
    suspend fun getRecommendedCity(): DataResult<RecommendedCityResponse>
}

class RemoteConfigRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context,
    private val moshi: Moshi
) : RemoteConfigRepository {

    private val remoteConfig: FirebaseRemoteConfig
        get() = Firebase.remoteConfig

    init {
        FirebaseApp.initializeApp(context)
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds =
                if (BuildConfig.DEBUG) 0 else TimeUnit.HOURS.toSeconds(2)
            fetchTimeoutInSeconds = 60
        }
        remoteConfig.apply {
            setConfigSettingsAsync(configSettings)
            setDefaultsAsync(R.xml.remote_config_defaults)
            fetchAndActivate()
        }
    }

    override suspend fun getPopularDestinations(): DataResult<PopularDestinationResponse> =
        withContext(Dispatchers.IO) {
            val jsonResult = remoteConfig.getString(HOME_POPULAR_DESTINATION)
            Timber.d(jsonResult)
            return@withContext try {
                val result =
                    moshi.adapter(PopularDestinationResponse::class.java).fromJson(jsonResult)
                result?.let { DataResult.Success(it) } ?: DataResult.Failure(Throwable("No Result"))
            } catch (ex: Exception) {
                DataResult.Failure(ex)
            }
        }

    override suspend fun getRecommendedCity(): DataResult<RecommendedCityResponse> =
        withContext(Dispatchers.IO) {
            val jsonResult = remoteConfig.getString(EXPLORE_RECOMMENDED_CITY)
            Timber.d(jsonResult)
            return@withContext try {
                val result =
                    moshi.adapter(RecommendedCityResponse::class.java).fromJson(jsonResult)
                result?.let { DataResult.Success(it) } ?: DataResult.Failure(Throwable("No Result"))
            } catch (ex: Exception) {
                DataResult.Failure(ex)
            }
        }

    companion object {
        private const val HOME_POPULAR_DESTINATION = "home_popular_destination"
        private const val EXPLORE_RECOMMENDED_CITY = "explore_recommended_city"
    }
}
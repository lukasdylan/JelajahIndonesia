package id.lukasdylan.jelajahindonesia.repository

import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Lukas Dylan on 21/10/20.
 */
interface PreferencesRepository {
    suspend fun isNeedToShowLocationPermission(): Boolean
    suspend fun setAlreadyShowLocationPermission()
}

class PreferencesRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val moshi: Moshi
) : PreferencesRepository {

    override suspend fun isNeedToShowLocationPermission(): Boolean = withContext(Dispatchers.IO) {
        sharedPreferences.getBoolean(SHOW_LOCATION_PERMISSION_KEY, true)
    }

    override suspend fun setAlreadyShowLocationPermission() {
        withContext(Dispatchers.IO) {
            sharedPreferences.edit().apply {
                putBoolean(SHOW_LOCATION_PERMISSION_KEY, false)
            }.apply()
        }
    }

    companion object {
        private const val SHOW_LOCATION_PERMISSION_KEY = "show_location_permission"
    }
}
package id.lukasdylan.jelajahindonesia

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.util.CoilUtils
import com.mapbox.mapboxsdk.Mapbox
import dagger.hilt.android.HiltAndroidApp
import id.lukasdylan.jelajahindonesia.utils.network.networkLoggingInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

/**
 * Created by Lukas Dylan on 21/10/20.
 */
@HiltAndroidApp
class MainApplication : Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token))
    }

    override fun newImageLoader(): ImageLoader = ImageLoader.Builder(applicationContext)
        .crossfade(250)
        .error(R.drawable.ic_default_place_photo)
        .okHttpClient {
            OkHttpClient.Builder()
                .cache(CoilUtils.createDefaultCache(applicationContext))
                .addInterceptor(networkLoggingInterceptor("ImageRequest").also {
                    it.redactHeader("Authorization")
                    it.redactHeader("Cookies")
                    it.level = HttpLoggingInterceptor.Level.BASIC
                })
                .build()
        }
        .build()
}
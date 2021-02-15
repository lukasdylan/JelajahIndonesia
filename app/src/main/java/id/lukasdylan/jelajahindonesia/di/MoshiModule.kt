package id.lukasdylan.jelajahindonesia.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

/**
 * Created by Lukas Dylan on 21/10/20.
 */
@Module
@InstallIn(ApplicationComponent::class)
object MoshiModule {

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }
}
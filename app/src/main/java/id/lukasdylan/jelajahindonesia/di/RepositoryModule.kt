package id.lukasdylan.jelajahindonesia.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import id.lukasdylan.jelajahindonesia.repository.*
import javax.inject.Singleton

/**
 * Created by Lukas Dylan on 21/10/20.
 */
@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideNetworkRepository(networkRepositoryImpl: NetworkRepositoryImpl): NetworkRepository

    @Singleton
    @Binds
    abstract fun providePreferencesRepository(preferencesRepositoryImpl: PreferencesRepositoryImpl): PreferencesRepository

    @Singleton
    @Binds
    abstract fun provideRemoteConfigRepository(remoteConfigRepositoryImpl: RemoteConfigRepositoryImpl): RemoteConfigRepository

    @Singleton
    @Binds
    abstract fun provideLocationRepository(locationRepositoryImpl: LocationRepositoryImpl): LocationRepository
}
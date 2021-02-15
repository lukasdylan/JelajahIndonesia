package id.lukasdylan.jelajahindonesia.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import id.lukasdylan.jelajahindonesia.repository.LocationRepository
import id.lukasdylan.jelajahindonesia.repository.NetworkRepository
import id.lukasdylan.jelajahindonesia.repository.PreferencesRepository
import id.lukasdylan.jelajahindonesia.repository.RemoteConfigRepository
import id.lukasdylan.jelajahindonesia.usecase.*
import javax.inject.Singleton

/**
 * Created by Lukas Dylan on 24/10/20.
 */
@Module
@InstallIn(ApplicationComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideGetDetailTourismPlaceUseCase(
        networkRepository: NetworkRepository
    ): GetDetailTourismPlaceUseCase {
        return GetDetailTourismPlaceUseCase(networkRepository)
    }

    @Singleton
    @Provides
    fun provideGetNearbyTourismPlaceUseCase(
        networkRepository: NetworkRepository,
        locationRepository: LocationRepository
    ): GetNearbyTourismPlaceUseCase {
        return GetNearbyTourismPlaceUseCase(networkRepository, locationRepository)
    }

    @Singleton
    @Provides
    fun provideGetTourismPlacesByLocationUseCase(
        networkRepository: NetworkRepository
    ): GetTourismPlacesByLocationUseCase {
        return GetTourismPlacesByLocationUseCase(networkRepository)
    }

    @Singleton
    @Provides
    fun provideGetPopularDestinationUseCase(
        remoteConfigRepository: RemoteConfigRepository
    ): GetPopularDestinationUseCase {
        return GetPopularDestinationUseCase(remoteConfigRepository)
    }

    @Singleton
    @Provides
    fun provideGetRecommendedCityUseCase(
        remoteConfigRepository: RemoteConfigRepository
    ): GetRecommendedCityUseCase {
        return GetRecommendedCityUseCase(remoteConfigRepository)
    }

    @Singleton
    @Provides
    fun provideCheckLocationPermissionUseCase(
        preferencesRepository: PreferencesRepository
    ): CheckLocationPermissionUseCase {
        return CheckLocationPermissionUseCase(preferencesRepository)
    }

    @Singleton
    @Provides
    fun provideSetAlreadyShowLocationPermissionUseCase(
        preferencesRepository: PreferencesRepository
    ): SetAlreadyShowLocationPermissionUseCase {
        return SetAlreadyShowLocationPermissionUseCase(preferencesRepository)
    }
}
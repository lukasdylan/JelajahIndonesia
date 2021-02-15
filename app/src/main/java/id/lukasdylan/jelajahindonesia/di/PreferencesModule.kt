package id.lukasdylan.jelajahindonesia.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

/**
 * Created by Lukas Dylan on 21/10/20.
 */
@Module
@InstallIn(ApplicationComponent::class)
object PreferencesModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("Jelajah Indonesia Preferences", Context.MODE_PRIVATE)
    }
}
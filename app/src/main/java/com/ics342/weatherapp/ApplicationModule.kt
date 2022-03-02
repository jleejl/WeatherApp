package com.ics342.weatherapp

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * This is the application module, we enable Hilt for dependency injection.
 * Using one instance of Moshi, Retrofit, and Api in this application.
 */
@Module
@InstallIn(ActivityComponent::class)
object ApplicationModule {

    @Provides
    fun provideApiService(): Api {
        // This is Moshi integration with Retrofit.
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        //Retrofit provides a converter for Moshi
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        return retrofit.create(Api::class.java)
    }
}
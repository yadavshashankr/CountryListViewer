package com.shashank.countrylist.identityModule.di

import com.shashank.countrylist.core.data.preferences.Preferences
import com.shashank.countrylist.core.data.helpers.ErrorHelper
import com.shashank.countrylist.identityModule.domain.IdentityRepository
import com.shashank.countrylist.identityModule.domain.IdentityRepositoryImpl
import com.shashank.countrylist.identityModule.data.services.IdentityService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object IdentityModule {

    @Provides
    @Singleton
    fun provideIdentityRepository(identityService: IdentityService, preferences: Preferences, errorHelper: ErrorHelper): IdentityRepository{
        return IdentityRepositoryImpl(identityService, preferences, errorHelper)
    }

    @Provides
    @Singleton
    fun provideIdentityService(retrofit: Retrofit): IdentityService {
        return retrofit.create(IdentityService::class.java)
    }

}
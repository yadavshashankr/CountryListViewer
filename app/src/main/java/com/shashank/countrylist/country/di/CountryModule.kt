package com.shashank.countrylist.country.di

import com.shashank.countrylist.country.countryList.helpers.CountryHelper
import com.shashank.countrylist.country.countryList.helpers.CountryHelperImpl
import com.shashank.countrylist.country.domain.CountryRepository
import com.shashank.countrylist.country.domain.CountryRepositoryImpl
import com.shashank.countrylist.country.services.CountryService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CountryModule {

    @Provides
    @Singleton
    fun provideCountryService(retrofit: Retrofit): CountryService {
        return retrofit.create(CountryService::class.java)
    }

    @Singleton
    @Provides
    fun provideCountryHelper(): CountryHelper {
        return CountryHelperImpl()
    }

    @Singleton
    @Provides
    fun provideCountryRepository(
        countryService: CountryService, countryHelper: CountryHelper
    ): CountryRepository {
        return CountryRepositoryImpl(countryService, countryHelper)
    }
}
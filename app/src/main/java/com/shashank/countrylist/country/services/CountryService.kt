package com.shashank.countrylist.country.services

import com.shashank.countrylist.country.countryList.model.CountryListItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface CountryService {

    @Headers("Content-Type: application/json")
    @GET("api/v1/countries")
    suspend fun getCountryList(): Response<List<CountryListItem>>

}
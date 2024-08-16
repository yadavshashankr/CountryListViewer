package com.shashank.countrylist.country.domain

import com.shashank.countrylist.core.utils.NetworkResult
import com.shashank.countrylist.country.countryList.model.CountryListItem

interface CountryRepository {

    suspend fun getCountryList(): NetworkResult<List<CountryListItem>?>

    fun getCountryFlag(countryCode: String): String

}
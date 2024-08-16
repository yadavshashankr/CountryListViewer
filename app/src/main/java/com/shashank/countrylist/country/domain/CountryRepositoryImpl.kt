package com.shashank.countrylist.country.domain

import com.shashank.countrylist.core.utils.NetworkResult
import com.shashank.countrylist.country.countryList.model.CountryListItem
import com.shashank.countrylist.country.countryList.helpers.CountryHelper
import com.shashank.countrylist.country.services.CountryService
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

/**
 * 'CountryRepository' acts as a cohesion point, where different Services and Helpers are referenced.
 * If we consider UI as finished product, viewModel as assembly line then CountryRepository acts
 * as a cohesion point where different dependent parts are accumulated.
 */

class CountryRepositoryImpl @Inject constructor(
    private val countryService: CountryService, private val countryHelper: CountryHelper
) : CountryRepository {

    override suspend fun getCountryList(): NetworkResult<List<CountryListItem>?> {
        try {
            val response: Response<List<CountryListItem>> = countryService.getCountryList()
            if (response.body() != null) {
                return NetworkResult.Success(response.body())
            } else {
                val jObjError = response.errorBody()?.string()?.let { JSONObject(it) }
                return NetworkResult.Error(jObjError?.getString("message").toString())
            }
        } catch (e: Exception) {
            return NetworkResult.Error(e.message.toString())
        }
    }

    override fun getCountryFlag(countryCode: String): String {
        return countryHelper.getCountryEmoji(countryCode)
    }
}
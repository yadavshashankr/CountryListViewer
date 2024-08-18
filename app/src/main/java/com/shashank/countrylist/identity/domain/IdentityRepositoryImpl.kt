package com.shashank.countrylist.identity.domain

import com.shashank.countrylist.BuildConfig
import com.shashank.countrylist.core.data.preferences.Preferences
import com.shashank.countrylist.core.utils.Constants
import com.shashank.countrylist.core.data.helpers.ErrorHelper
import com.shashank.countrylist.core.utils.NetworkResult
import com.shashank.countrylist.identity.data.model.IdentityResponse
import com.shashank.countrylist.identity.data.services.IdentityService
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

/**
 * 'IdentityRepository' acts as a cohesion point for IdentityService, Persistent-Encrypted
 * data and ErrorHelpers.
 * Once the Identity is successfully verified, the received access_token is stored in the KeyStore.
 */

class IdentityRepositoryImpl @Inject constructor(
    private val identityService: IdentityService,
    private val preferences: Preferences,
    private val errorHelper: ErrorHelper
) : IdentityRepository {

    override suspend fun doVerification(
        username: String, password: String
    ): NetworkResult<String?> {
        try {
            val response: Response<IdentityResponse> = identityService.doVerification(
                Constants.ApiProperties.AUTH_URL,
                BuildConfig.GRANT_TYPE,
                username,
                password,
                BuildConfig.CLIENT_ID,
                BuildConfig.CLIENT_SECRET
            )

            if (response.body() != null) {
                preferences.saveToken(response.body()?.accessToken as String)
                return NetworkResult.Success("Success")
            } else {
                val jObjError = response.errorBody()?.string()?.let { JSONObject(it) }
                val errorMessage = jObjError?.getString("error_description") as String
                return NetworkResult.Error(errorHelper.formatError(errorMessage))
            }
        } catch (e: Exception) {
            return NetworkResult.Error(e.message.toString())
        }
    }
}
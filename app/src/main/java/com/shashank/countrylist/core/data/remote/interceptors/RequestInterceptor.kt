package com.shashank.countrylist.core.data.remote.interceptors

import com.shashank.countrylist.core.data.preferences.Preferences
import okhttp3.Interceptor
import okhttp3.Response

/**
 * 'RequestInterceptor' intercepts the calling request and sends bearer token with the request during the call.
 */

class RequestInterceptor(
    private val preferences: Preferences
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = preferences.loadLoggedUser()?.token
        val original = chain.request()
        val requestBuilder = original.newBuilder()
            .header("Authorization", "Bearer $token")
        return chain.proceed(requestBuilder.build())
    }
}

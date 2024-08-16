package com.shashank.countrylist.identity.data.services

import com.shashank.countrylist.identity.data.model.IdentityResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Url

interface IdentityService {

    @FormUrlEncoded
    @POST
    suspend fun doVerification(
        @Url url: String,
        @Field("grant_type") grantType: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("client_id") clientID: String,
        @Field("client_secret") clientSecret: String
    ): Response<IdentityResponse>
}
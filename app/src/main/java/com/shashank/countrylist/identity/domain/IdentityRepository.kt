package com.shashank.countrylist.identity.domain

import com.shashank.countrylist.core.utils.NetworkResult

interface IdentityRepository {

    suspend fun doVerification(username: String, password: String): NetworkResult<String?>

}
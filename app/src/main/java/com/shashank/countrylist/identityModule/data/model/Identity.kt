package com.shashank.countrylist.identityModule.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class IdentityResponse(@Expose @SerializedName("access_token")var accessToken : String?)
package com.shashank.countrylist.identityModule.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class IdentityResponse(@Expose @SerializedName("access_token")val accessToken : String?) : Parcelable
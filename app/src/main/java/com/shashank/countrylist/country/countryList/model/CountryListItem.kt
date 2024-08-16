package com.shashank.countrylist.country.countryList.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CountryListItem(
    @Expose @SerializedName("countryCode") val countryCode: String?,
    @Expose @SerializedName("countryName") val countryName: String?,
    @Expose @SerializedName("dialCode") val dialCode: String?,
    @Expose @SerializedName("displayOrder") val displayOrder: Int?,
    @Expose @SerializedName("id") val id: Int?,
    @Expose @SerializedName("isoAlpha2") val isoAlpha2: String?,
    @Expose @SerializedName("isoAlpha3") val isoAlpha3: String?,
    @Expose @SerializedName("isoNumeric") val isoNumeric: Int?,
    @Expose @SerializedName("lat") val lat: String?,
    @Expose @SerializedName("links") val links: String?,
    @Expose @SerializedName("long") val long: String?,
    @Expose @SerializedName("nationality") val nationality: String?,
    @Expose @SerializedName("type") val type: String?
) : Parcelable
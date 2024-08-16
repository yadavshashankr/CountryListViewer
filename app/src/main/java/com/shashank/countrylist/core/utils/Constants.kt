package com.shashank.countrylist.core.utils

import com.shashank.countrylist.BuildConfig

/**
 * 'Constants' helps in maintaining values like ApiProperties, Navigation Routes etc.
 */

class Constants {

    object ApiProperties {
        const val BASE_URL = "https://${BuildConfig.BASE_URL}"
        const val AUTH_URL = "https://${BuildConfig.AUTH_URL}connect/token"
    }

    object Routes {
        const val LOGIN = "login"
        const val COUNTRY_LIST = "country_list"
    }
}
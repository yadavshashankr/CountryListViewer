package com.shashank.countrylist.country.countryList.helpers

import javax.inject.Inject

/**
 * 'CountryHelper' helps in processing data related to the countries. Currently it is helping to
 * generate Emojis as per the Country Code which is received from the server.
 */

class CountryHelperImpl @Inject constructor() : CountryHelper {

    override fun getCountryEmoji(countryCode: String): String {
        val flagOffset = 0x1F1E6
        val asciiOffset = 0x41

        val firstChar = Character.codePointAt(countryCode, 0) - asciiOffset + flagOffset
        val secondChar = Character.codePointAt(countryCode, 1) - asciiOffset + flagOffset

        val flag = String(Character.toChars(firstChar)) + String(Character.toChars(secondChar))
        return flag
    }

}
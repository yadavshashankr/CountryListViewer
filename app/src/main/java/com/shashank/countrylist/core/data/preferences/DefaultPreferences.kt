package com.shashank.countrylist.core.data.preferences

import android.content.SharedPreferences
import com.shashank.countrylist.core.data.model.LoggedUser

/**
 * 'DefaultPreferences' helps in storing sensitive data securely on Keystore/EncryptedSharedPreference.
 */

class DefaultPreferences(
    private val sharedPreferences: SharedPreferences
) : Preferences {
    override fun saveToken(token: String) {
        sharedPreferences.edit().putString(Preferences.KEY_TOKEN, token).apply()
    }

    override fun saveFullName(name: String) {
        sharedPreferences.edit().putString(Preferences.KEY_NAME, name).apply()
    }

    override fun saveUserId(userId: String) {
        sharedPreferences.edit().putString(Preferences.KEY_USERID, userId).apply()
    }

    override fun loadLoggedUser(): LoggedUser? {
        val token = sharedPreferences.getString(Preferences.KEY_TOKEN, "") ?: ""
        if (token.isBlank()) {
            return null
        }
        return LoggedUser(
            token = token
        )
    }

    override fun deleteUser() {
        sharedPreferences.edit().remove(Preferences.KEY_TOKEN).remove(Preferences.KEY_NAME)
            .remove(Preferences.KEY_USERID).apply()
    }
}
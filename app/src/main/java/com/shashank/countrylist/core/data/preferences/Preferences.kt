package com.shashank.countrylist.core.data.preferences

import com.shashank.countrylist.core.data.model.LoggedUser

interface Preferences {
    fun saveToken(token: String)
    fun saveFullName(name: String)
    fun saveUserId(userId: String)
    fun loadLoggedUser(): LoggedUser?
    fun deleteUser()

    companion object {
        const val KEY_TOKEN = "token"
        const val KEY_NAME = "name"
        const val KEY_USERID = "userid"
    }
}
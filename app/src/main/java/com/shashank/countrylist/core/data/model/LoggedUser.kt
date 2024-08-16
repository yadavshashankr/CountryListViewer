package com.shashank.countrylist.core.data.model

/**
 * 'LoggedUser' currently holds only token however it can contain more details regarding the user in future.
 */

data class LoggedUser(
    val token: String,
)

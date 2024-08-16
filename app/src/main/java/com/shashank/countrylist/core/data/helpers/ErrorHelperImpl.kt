package com.shashank.countrylist.core.data.helpers

import javax.inject.Inject

/**
 * 'ErrorHelper' helps in processing the unformatted messages received from the server.
 */

class ErrorHelperImpl @Inject constructor() : ErrorHelper {

    override fun formatError(error: String): String {
        val err = error.replace("_", " ")
        return err.substring(0, 1).uppercase() + err.substring(1, err.length)
    }

}
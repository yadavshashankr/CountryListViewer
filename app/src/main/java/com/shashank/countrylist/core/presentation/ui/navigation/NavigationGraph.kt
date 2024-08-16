package com.shashank.countrylist.core.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shashank.countrylist.core.utils.Constants.Routes.COUNTRY_LIST
import com.shashank.countrylist.core.utils.Constants.Routes.LOGIN
import com.shashank.countrylist.country.presentation.ui.screen.CountryScreen
import com.shashank.countrylist.identityModule.presentation.screen.LoginForm

/**
 * 'Navigation Graph' acts as a registry of all the screens in the app.
 * Whenever a screen is pushed or popped in the app it happens through here
 * 'startDestination' acts as a default destination when the app starts
 */

@Composable
fun NavigationGraph(navController: NavHostController){
    NavHost(navController = navController, startDestination = LOGIN) {
        composable(route = LOGIN){
            LoginForm(navController)
        }
        composable(route = COUNTRY_LIST){
            CountryScreen()
        }
    }
}
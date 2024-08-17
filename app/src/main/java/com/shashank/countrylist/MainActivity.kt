package com.shashank.countrylist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.shashank.countrylist.core.presentation.ui.navigation.NavigationGraph
import com.shashank.countrylist.identity.presentation.viewModels.IdentityViewModel
import com.shashank.countrylist.theme.CountryListAppTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * MainActivity acts as the only activity in the project. It defines SplashScreen dismiss state, NavigationGraph initialization.
 * When the background tasks are completed we can dismiss the SplashScreen by changing the 'isAppReady' flag to true
 */

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: IdentityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !viewModel.isAppReady.value
            }
        }

        setContent {
            CountryListAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    App()
                }
            }
        }
    }
}

/**
 * 'App' composable to remember Navigation state and pass the instance of nav-controller to Navigation-Graph.
 */

@Composable
fun App() {
    val navController = rememberNavController()
    NavigationGraph(navController = navController)
}
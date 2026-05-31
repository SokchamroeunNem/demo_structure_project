package com.example.demo.presentation

import android.util.Log
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.demo.BuildConfig
import com.example.demo.graphs.Graph
import com.example.demo.graphs.MainNavGraph
import com.example.demo.presentation.components.BottomNavigationBar
import com.example.demo.presentation.components.SimpleLightTopAppBar
import com.example.demo.ui.theme.EmailVerificationAuthenticationViaAPITheme
import com.example.demo.util.AuthStateManager

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
    authStateManager: AuthStateManager = hiltViewModel() // Use Hilt to inject AuthStateManager
) {

    val authState by authStateManager.authState.collectAsState()

    LaunchedEffect(authState) {
        if (authState == AuthStateManager.AuthState.NeedLogin) {
            // Navigate to the login screen
            Log.d("AuthAuthenticator", "MainScreen: NeedLogin")
            navController.navigate(Graph.AUTHENTICATION) {
                popUpTo(Graph.MAIN_SCREEN_PAGE) {
                    inclusive = true
                }
            }
        } else {
            Log.d("AuthAuthenticator", "MainScreen: Valid")
        }
    }

    Scaffold(
        topBar = { SimpleLightTopAppBar("Nested Navigation Demo ${BuildConfig.BUILD_TYPE}") },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }) { padding ->
        //Content
        var modifier = Modifier.padding(padding)
        MainNavGraph(
            modifier = modifier, navController = navController
        )


    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    EmailVerificationAuthenticationViaAPITheme {
        val navController = rememberNavController()
        MainScreen(navController)
    }
}
package com.example.emailverificationauthenticationviaapi.presentation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.emailverificationauthenticationviaapi.graphs.AuthScreen
import com.example.emailverificationauthenticationviaapi.graphs.Graph
import com.example.emailverificationauthenticationviaapi.graphs.MainNavGraph
import com.example.emailverificationauthenticationviaapi.presentation.components.BottomNavigationBar
import com.example.emailverificationauthenticationviaapi.presentation.components.SimpleLightTopAppBar
import com.example.emailverificationauthenticationviaapi.presentation.login.LoginFormEvent
import com.example.emailverificationauthenticationviaapi.ui.theme.EmailVerificationAuthenticationViaAPITheme
import com.example.emailverificationauthenticationviaapi.util.AuthStateManager

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

    Scaffold(topBar = { SimpleLightTopAppBar("Nested Navigation Demo") }, bottomBar = {
        BottomNavigationBar(navController = navController)
    }) { padding ->
        //Content
        var modifier = Modifier.padding(padding)
        MainNavGraph(navController = navController)
        //Test code below
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    //navController.navigate(Graph.AUTHENTICATION)
                    navController.navigate(Graph.AUTHENTICATION) {
                        popUpTo(Graph.MAIN_SCREEN_PAGE) {
                            inclusive = true
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(text = "Back to Login")
            }
        }

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
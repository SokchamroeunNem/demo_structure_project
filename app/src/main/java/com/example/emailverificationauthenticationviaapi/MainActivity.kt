package com.example.emailverificationauthenticationviaapi

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.emailverificationauthenticationviaapi.graphs.Graph.AUTHENTICATION
import com.example.emailverificationauthenticationviaapi.graphs.Graph.MAIN_SCREEN_PAGE
import com.example.emailverificationauthenticationviaapi.graphs.RootNavigationGraph
import com.example.emailverificationauthenticationviaapi.preferences.SharedPrefs
import com.example.emailverificationauthenticationviaapi.ui.theme.EmailVerificationAuthenticationViaAPITheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sharedPrefs: SharedPrefs
    private lateinit var startDestination: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestNotificationPermission()
        setContent {
            EmailVerificationAuthenticationViaAPITheme {
                RootNavigationGraph(startDestination)
                //RootNavigationGraph(MAIN_SCREEN_PAGE)
                //RootNavigationGraph(AUTHENTICATION)
            }
        }
    }

    private fun requestNotificationPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val hasPermission = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if(!hasPermission) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    0
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()
        checkIsLoggedIn()
    }

    private fun checkIsLoggedIn() {
        runBlocking {
            Log.d("getToken", "checkIsLoggedIn: ${sharedPrefs.getToken()}")
            startDestination =
                if (sharedPrefs.getToken().equals("") || sharedPrefs.getToken() == null) {
                    AUTHENTICATION
                } else {
                    MAIN_SCREEN_PAGE
                }
        }
    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!", modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EmailVerificationAuthenticationViaAPITheme {
        Greeting("Android")
    }
}
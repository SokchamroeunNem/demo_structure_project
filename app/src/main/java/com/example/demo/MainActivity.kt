package com.example.demo

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.demo.graphs.Graph.AUTHENTICATION
import com.example.demo.graphs.Graph.MAIN_SCREEN_PAGE
import com.example.demo.graphs.RootNavigationGraph
import com.example.demo.preferences.SharedPrefs
import com.example.demo.ui.theme.EmailVerificationAuthenticationViaAPITheme
import com.example.demo.util.AppSecretManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    @Inject
//    lateinit var sharedPrefs: SharedPrefs
//    private lateinit var startDestination: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        requestNotificationPermission()
//        setContent {
//            EmailVerificationAuthenticationViaAPITheme {
//                RootNavigationGraph(startDestination)
//                //RootNavigationGraph(MAIN_SCREEN_PAGE)
//                //RootNavigationGraph(AUTHENTICATION)
//            }
//        }
        setContent {
            DemoCMake()
        }
    }

    @Composable
    private fun DemoCMake() {
        Scaffold() { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Text(
                    "API Endpoint = ${AppSecretManager.getApiBaseUrl()}",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        color = Color.Blue,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                )
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

//    override fun onStart() {
//        super.onStart()
//        checkIsLoggedIn()
//    }
//
//    private fun checkIsLoggedIn() {
//        runBlocking {
//            Log.d("getToken", "checkIsLoggedIn: ${sharedPrefs.getToken()}")
//            startDestination =
//                if (sharedPrefs.getToken().equals("") || sharedPrefs.getToken() == null) {
//                    AUTHENTICATION
//                } else {
//                    MAIN_SCREEN_PAGE
//                }
//        }
//    }

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
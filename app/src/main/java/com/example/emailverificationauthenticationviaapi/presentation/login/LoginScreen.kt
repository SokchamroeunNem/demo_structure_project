package com.example.emailverificationauthenticationviaapi.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.emailverificationauthenticationviaapi.R
import com.example.emailverificationauthenticationviaapi.graphs.AuthScreen
import com.example.emailverificationauthenticationviaapi.graphs.Graph
import com.example.emailverificationauthenticationviaapi.presentation.components.ValidatedOutlinedTextField
import com.example.emailverificationauthenticationviaapi.presentation.registerscreen.RegistrationFormEvent
import com.example.emailverificationauthenticationviaapi.ui.theme.EmailVerificationAuthenticationViaAPITheme

@Composable
fun LoginScreen(navController: NavController) {

    /*var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }*/

    val loginViewModel: LoginViewModel = hiltViewModel<LoginViewModel>()
    val stateValidation = loginViewModel.loginState

    val stateActivity = loginViewModel.stateActivity

    var passwordVisibility by remember { mutableStateOf(false) }

    if (stateActivity.isSuccess) {
        //navController.navigate(AuthScreen.Login.route)
        navController.navigate(AuthScreen.Login.route) {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
        }
    }

    stateActivity.message?.let {
        // Show toast message
    }

    stateActivity.loginEntity?.let {
        // Handle success
    }

    stateActivity.rawResponse?.let {
        // Handle error
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
        )
        Text(text = "*** Login Screen ***")

        Spacer(modifier = Modifier.height(16.dp))

        ValidatedOutlinedTextField(
            value = stateValidation.email,
            onValueChange = { loginViewModel.onEvent(LoginFormEvent.EmailChanged(it)) },
            label = "Email",
            validate = { loginViewModel.validateEmail(it) },
            error = stateValidation.emailError,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(16.dp))

        ValidatedOutlinedTextField(
            value = stateValidation.password,
            onValueChange = { loginViewModel.onEvent(LoginFormEvent.PasswordChanged(it)) },
            label = "Password",
            validate = { loginViewModel.validatePassword(it) },
            error = stateValidation.passwordError,
            leadingIcon = { Icon(imageVector = Icons.Default.Password, contentDescription = null) },
            trailingIcon = {
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(
                        imageVector = if (passwordVisibility) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = null
                    )
                }
            },
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                /*navController.navigate(Graph.MAIN_SCREEN_PAGE) {
                    popUpTo(AuthScreen.Login.route) { inclusive = true }
                }*/
                loginViewModel.onEvent(LoginFormEvent.Submit)
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = { navController.navigate(AuthScreen.SignUp.route) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "Don't have an account? Register")
        }
        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = {
            // Navigate to Forgot Password screen
            navController.navigate(AuthScreen.Forgot.route)
        }) {
            Text(text = "Forgot Password?")
        }

    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    EmailVerificationAuthenticationViaAPITheme {
        val navController = rememberNavController()
        LoginScreen(navController = navController)
    }
}
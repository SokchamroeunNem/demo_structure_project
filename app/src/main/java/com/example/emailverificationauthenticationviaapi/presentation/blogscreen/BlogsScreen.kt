package com.example.emailverificationauthenticationviaapi.presentation.blogscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun BlogsScreen(navController: NavController, blogViewModel: BlogViewModel = hiltViewModel()) {

    val blogs = blogViewModel.blogs.value

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

    }
}
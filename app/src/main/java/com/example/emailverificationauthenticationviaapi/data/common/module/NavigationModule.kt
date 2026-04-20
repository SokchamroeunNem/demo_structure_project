package com.example.emailverificationauthenticationviaapi.data.common.module

import android.content.Context
import androidx.navigation.NavHostController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Provides
    @Singleton
    fun provideNavHostController(
        @ApplicationContext context: Context
    ): NavHostController {
        val navController = NavHostController(context)
        // Set up your NavController here if needed
        // navController.setGraph(R.navigation.nav_graph) // Example setup
        return navController
    }
}



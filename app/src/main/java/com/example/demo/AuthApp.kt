package com.example.demo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import com.onesignal.OneSignal

@HiltAndroidApp
class AuthApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
    }

    companion object {
        private const val ONESIGNAL_APP_ID = "fe95a75e-e304-4ca3-8c7a-12501cbf5b6b"
    }
}
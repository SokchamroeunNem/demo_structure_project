import com.google.firebase.appdistribution.gradle.firebaseAppDistribution
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.firebase.appdistribution)
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("com.onesignal.androidsdk.onesignal-gradle-plugin")
}

val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
if (keystorePropertiesFile.exists()) {
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))
}

android {
    namespace = "com.example.demo"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.demo"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        android.buildFeatures.buildConfig = true
        //buildConfigField("String", "API_BASE_URL", "\"http://10.0.2.2:8080/\"")

        vectorDrawables {
            useSupportLibrary = true
        }
        externalNativeBuild {
            cmake {
                cppFlags += "-std=c++17"
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }

    signingConfigs {
        create("release") {
            if (keystorePropertiesFile.exists()) {
                storeFile = file(keystoreProperties.getProperty("storeFile", "release.jks"))
                storePassword = keystoreProperties.getProperty("storePassword")
                keyAlias = keystoreProperties.getProperty("keyAlias")
                keyPassword = keystoreProperties.getProperty("keyPassword")
            }
        }
        getByName("debug") {
            // By default uses the local debug keystore
        }
    }

    flavorDimensions.add("environment")

    productFlavors {
        create("dev") {
            dimension = "environment"
            resValue("string", "app_name", "https://jsonplaceholder.typicode.com/dev")
            buildConfigField("String", "ENVIRONMENT_NAME", "\"DEV\"")
            externalNativeBuild {
                cmake {
                    arguments("-DFLAVOR=dev")
                }
            }
        }
        create("staging") {
            dimension = "environment"

            resValue(
                "string",
                "app_name",
                "https://jsonplaceholder.typicode.com/"
            )

            buildConfigField(
                "String",
                "ENVIRONMENT_NAME",
                "\"https://jsonplaceholder.typicode.com/\""
            )
            externalNativeBuild {
                cmake {
                    arguments("-DFLAVOR=staging")
                }
            }
        }
        create("preprod") {
            dimension = "environment"
            resValue("string", "app_name", "MyApp PreProd")
            resValue("string", "app_name", "https://jsonplaceholder.typicode.com/preprod")
            buildConfigField("String", "ENVIRONMENT_NAME", "\"preprod\"")
            externalNativeBuild {
                cmake {
                    arguments("-DFLAVOR=preprod")
                }
            }
        }
        create("production") {
            dimension = "environment"
            resValue("string", "app_name", "https://jsonplaceholder.typicode.com/production")
            buildConfigField("String", "ENVIRONMENT_NAME", "\"production\"")
            externalNativeBuild {
                cmake {
                    arguments("-DFLAVOR=production")
                }
            }
        }

        buildTypes {
            release {
                isMinifyEnabled = true
                isShrinkResources = true
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
                signingConfig = signingConfigs.getByName("release")

                firebaseAppDistribution {
                    artifactType = "APK"
                    releaseNotes = "New automatic deployment from CI"

                    val credencesFile = rootProject.file("firebase-credentials.json")
                    if (credencesFile.exists()) {
                        serviceCredentialsFile = credencesFile.absolutePath
                    } else {
                        // Fallbacks: you can also set FIREBASE_TOKEN or GOOGLE_APPLICATION_CREDENTIALS environment variables.
                    }
                }
            }
            debug {
                isDebuggable = true
                isMinifyEnabled = false
                signingConfig = signingConfigs.getByName("debug")

                firebaseAppDistribution {
                    artifactType = "APK"
                    releaseNotes = "New automatic debug deployment from CI"

                    val credencesFile = rootProject.file("firebase-credentials.json")
                    if (credencesFile.exists()) {
                        serviceCredentialsFile = credencesFile.absolutePath
                    } else {
                        // Fallbacks: you can also set FIREBASE_TOKEN or GOOGLE_APPLICATION_CREDENTIALS environment variables.
                    }
                }
            }
        }

        composeOptions {
            kotlinCompilerExtensionVersion = "1.5.1"
        }
        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
    }
    externalNativeBuild {
        cmake {
            path = file("CMakeLists.txt")
            version = "3.22.1"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Navigation Compose
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.material.icons.extended)

    //Dagger - Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    kapt(libs.androidx.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose.v275)

    // Timber for logging
    implementation(libs.timber)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.v4100)
    implementation(libs.logging.interceptor)
    implementation(libs.conscrypt.android)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Coroutine Lifecycle Scopes
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx.v262)

    // Datastore
    implementation(libs.androidx.datastore.preferences)

    // Firebase
    implementation(libs.firebase.messaging.ktx)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)

    implementation(libs.onesignal.v4810)

    val ktor_version = "2.3.10"
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-android:$ktor_version")
    implementation("io.ktor:ktor-client-serialization:$ktor_version")

    // Security
    /*implementation("androidx.biometric:biometric:1.2.0-alpha05")
    implementation("androidx.security:security-crypto:1.1.0-alpha06")
    implementation("com.scottyab:rootbeer-lib:0.0.8")*/
}
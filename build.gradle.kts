// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath(libs.onesignal.gradle.plugin)
    }
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.firebase.appdistribution) apply false
    alias(libs.plugins.detekt)
    id("com.google.dagger.hilt.android") version "2.57" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.10" apply false
    id("com.google.gms.google-services") version "4.4.3" apply false
}

// Global detekt config
detekt {
    buildUponDefaultConfig = true // preconfigure defaults
    allRules = false // activate all available (even unstable) rules.
    config.setFrom(files("$projectDir/detekt.yml"))
}

tasks.register<Copy>("installGitHook") {
    description = "Installs the pre-commit git hook"
    group = "help"
    from(File(rootProject.rootDir, ".githooks/pre-commit"))
    into(File(rootProject.rootDir, ".git/hooks"))
    fileMode = 0b111101101 // chmod 755
}
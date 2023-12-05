// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    dependencies{
        classpath("com.google.gms:google-services:4.3.15")
        classpath("com.android.tools.build:gradle:7.2.2")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.45")
    }
}


plugins {
    id ("com.android.application") version "8.0.0" apply false
    id ("com.android.library") version "8.0.0" apply false
    id("org.jetbrains.kotlin.android") version "1.7.20" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
}

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}



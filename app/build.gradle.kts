plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.3")

    defaultConfig {
        applicationId = "com.didik.moflix"
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release"){
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.all { test ->
            test.useJUnitPlatform()
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.32")

    // Core KTX
    implementation("androidx.core:core-ktx:1.3.2")

    // App Compat
    implementation("androidx.appcompat:appcompat:1.2.0")

    // Android Material
    implementation("com.google.android.material:material:1.3.0")

    // Constraint Layout
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")

    // Vector Drawable
    implementation("androidx.vectordrawable:vectordrawable:1.1.0")

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.4")

    // Lifecycle Live Data
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")

    // Groupie
    implementation("com.xwray:groupie:2.9.0")
    implementation("com.xwray:groupie-viewbinding:2.9.0")

    // Coil
    implementation("io.coil-kt:coil:1.1.1")

    //Dagger Hilt
    implementation("com.google.dagger:hilt-android:2.28-alpha")
    kapt("com.google.dagger:hilt-android-compiler:2.28-alpha")
    implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha01")
    kapt("androidx.hilt:hilt-compiler:1.0.0-alpha01")

    // Moshi
    implementation("com.squareup.moshi:moshi-kotlin:1.9.3")

    // MockK
    testImplementation("io.mockk:mockk:1.11.0")

    // Kotest
    testImplementation("io.kotest:kotest-runner-junit5:4.4.3")
    testImplementation("io.kotest:kotest-assertions-core:4.4.3")
    testImplementation("io.kotest:kotest-property:4.4.3")

    // Arch Core Testing
    testImplementation("android.arch.core:core-testing:1.1.1")

    // Kotlin Coroutine Test
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.3")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    androidTestImplementation("androidx.test:runner:1.3.0")
    androidTestImplementation("androidx.test:rules:1.3.0")
    androidTestImplementation("com.android.support.test.espresso:espresso-contrib:3.0.2") {
        exclude(group = "com.android.support", module = "recyclerview-v7")
    }
}
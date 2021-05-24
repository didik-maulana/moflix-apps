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
        minSdkVersion(23)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildTypes.forEach { buildType ->
        buildType.buildConfigField("String", "IMAGE_BASE_URL", "\"https://image.tmdb.org\"")
        buildType.buildConfigField("String", "API_BASE_URL", "\"https://api.themoviedb.org/3/\"")
        buildType.buildConfigField("String", "API_KEY", "\"0e8b96bbbfb9a12b5fc83724af528456\"")
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
    implementation(Libs.kotlinStdlib)
    implementation(Libs.coreKtx)
    implementation(Libs.fragmentKtx)
    implementation(Libs.appCompat)
    implementation(Libs.material)
    implementation(Libs.constraintLayout)
    implementation(Libs.navigationUI)
    implementation(Libs.lifecycleLiveData)
    implementation(Libs.groupie)
    implementation(Libs.groupieViewBinding)
    implementation(Libs.coil)
    implementation(Libs.moshi)
    implementation(Libs.moshiConverter)
    implementation(Libs.retrofit)
    implementation(Libs.loggingInterceptor)
    implementation(Libs.swipeRefreshLayout)
    implementation(TestLibs.idlingResource)

    implementation(Libs.daggerHilt)
    kapt(Libs.daggerHiltCompiler)

    testImplementation(TestLibs.mockk)
    testImplementation(TestLibs.kotestRunner)
    testImplementation(TestLibs.kotestAssertions)
    testImplementation(TestLibs.kotestProperty)
    testImplementation(TestLibs.coreTesting)
    testImplementation(TestLibs.jUnit)

    androidTestImplementation(TestLibs.jUnitExt)
    androidTestImplementation(TestLibs.espressoCore)
    androidTestImplementation(TestLibs.testRunner)
    androidTestImplementation(TestLibs.testRules)
    androidTestImplementation(TestLibs.espressoContrib) {
        exclude(group = "com.android.support", module = "recyclerview-v7")
    }
}
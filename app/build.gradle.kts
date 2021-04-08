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
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildTypes.forEach { buildType ->
        buildType.buildConfigField("String", "BASE_URL", "\"https://image.tmdb.org\"")
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
    implementation(Libs.appCompat)
    implementation(Libs.material)
    implementation(Libs.constraintLayout)
    implementation(Libs.vectorDrawable)
    implementation(Libs.navigationUI)
    implementation(Libs.lifecycleLiveData)
    implementation(Libs.groupie)
    implementation(Libs.groupieViewBinding)
    implementation(Libs.coil)
    implementation(Libs.moshi)

    implementation(Libs.daggerHilt)
    kapt(Libs.daggerHiltCompiler)
    implementation(Libs.hiltLifecycleViewModel)
    kapt(Libs.hiltCompiler)

    testImplementation(TestLibs.mockk)
    testImplementation(TestLibs.kotestRunner)
    testImplementation(TestLibs.kotestAssertions)
    testImplementation(TestLibs.kotestProperty)
    testImplementation(TestLibs.coreTesting)
    testImplementation(TestLibs.coroutineTest)
    testImplementation(TestLibs.jUnit)

    androidTestImplementation(TestLibs.jUnitExt)
    androidTestImplementation(TestLibs.espressoCore)
    androidTestImplementation(TestLibs.testRunner)
    androidTestImplementation(TestLibs.testRules)
    androidTestImplementation(TestLibs.espressoContrib) {
        exclude(group = "com.android.support", module = "recyclerview-v7")
    }
}
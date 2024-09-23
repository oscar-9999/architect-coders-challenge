plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.acchallenge.rickandmorty"
    compileSdk = 34


    defaultConfig {
        applicationId = "com.acchallenge.rickandmorty"
        minSdk = 25
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.acchallenge.rickandmorty.di.HiltTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildFeatures {
        buildConfig = true
    }

    kapt {
        correctErrorTypes = true
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("String", "URL_BASE", "\"https://rickandmortyapi.com/api/\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("String", "URL_BASE", "\"https://rickandmortyapi.com/api/\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
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

dependencies {
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.animation)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.design.material.extended)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.palette.ktx)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.compose.material)
    testImplementation(libs.junit)
    testImplementation(libs.mockK)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.junit.ktx)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    debugImplementation(libs.androidx.test.monitor)
    kaptAndroidTest(libs.dagger.hilt.compiler)

    //COIL
    api(libs.landscapist.coil)

    //CONSTRAINTLAYOUT
    implementation(libs.androidx.compose.constraintlayout)

    //NAVIGATION
    api(libs.accompanist.insetsui)
    api(libs.accompanist.navigation.animation)
    api(libs.accompanist.ui.controller)
    api(libs.accompanist.placeholder.material)

    //PAGING
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)

    //SPLASH
    implementation(libs.androidx.core.splashscreen)

    //SERIALIZER
    implementation(libs.json.serializer)

    //RETROFIT
    implementation(libs.retrofit)

    //OKHTTP
    implementation(libs.okhttp.okhttp)
    implementation(libs.okhttp.loggingInterceptor)

    //JSON
    implementation(libs.json.serializer)
    implementation(libs.json.converter)

    //GSON
    implementation(libs.gson)
    implementation(libs.converter.gson)

    //DAGGER
    kapt(libs.dagger.hilt.compiler)
    kapt(libs.androidx.hilt.compiler)
    implementation(libs.dagger.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.dagger.hilt.android.testing)
}
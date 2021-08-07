plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 30
    defaultConfig {
        applicationId = "com.corradodev.mvvm"
        minSdk = 23
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "11"
        //Needed by Store
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-Xopt-in=kotlinx.coroutines.FlowPreview",
            "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
        )
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.0.1"
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core)
    implementation(libs.androidx.compose.activity)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.tooling)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material.core)
    implementation(libs.androidx.compose.material.icons)
    implementation(libs.androidx.compose.lifecycle.viewmodel)
    implementation(libs.androidx.compose.hilt)

    implementation(libs.kotlinx.coroutines)

    implementation(libs.material)

    implementation(libs.androidx.room.core)
    kapt(libs.androidx.room.kapt)

    implementation(libs.gson)

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.gson)
    implementation(libs.retrofit.logging)

    implementation(libs.store)

    implementation(libs.hilt.core)
    kapt(libs.hilt.kapt)

    testImplementation(libs.testing.junit)

    androidTestImplementation(libs.testing.androidx.junit)
    androidTestImplementation(libs.testing.espresso.core)
}
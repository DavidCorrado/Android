plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("jacoco")
}
android {
    testCoverage {
        jacocoVersion = libs.versions.jacoco.get()
    }
    compileSdk = 30
    defaultConfig {
        applicationId = "com.corradodev.todo"
        minSdk = 23
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("debug") {
            isTestCoverageEnabled = true
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        // Have to use Java8 because of bug with using jacoco https://issuetracker.google.com/issues/178400721 https://issuetracker.google.com/issues/178172809
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        // Needed by Store
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-Xopt-in=kotlinx.coroutines.FlowPreview",
            "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
        )
        allWarningsAsErrors = true
    }
    lint {
        isWarningsAsErrors = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.core.get()
    }
    buildFeatures {
        compose = true
    }
    testOptions {
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
        animationsDisabled = true

        unitTests {
            isIncludeAndroidResources = true
        }
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

    coreLibraryDesugaring(libs.android.desugaring)

    implementation(libs.kotlinx.coroutines)

    implementation(libs.material)

    implementation(libs.androidx.room.core)
    kapt(libs.androidx.room.kapt)

    implementation(libs.moshi.core)
    kapt(libs.moshi.kapt)

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.moshi)
    implementation(libs.retrofit.logging)

    implementation(libs.store)

    implementation(libs.hilt.core)
    kapt(libs.hilt.kapt)

    testImplementation(libs.testing.junit)

    androidTestImplementation(libs.testing.runner)
    androidTestUtil(libs.testing.orchestrator)
    androidTestImplementation(libs.testing.androidx.junit)
    androidTestImplementation(libs.testing.espresso.core)
}

tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn(listOf("testDebugUnitTest", "createDebugCoverageReport"))
    reports {
        xml.isEnabled = true
        html.isEnabled = true
    }

    val fileFilter = listOf(
        "**/di/**", //
        "**/*JsonAdapter.*", // Moshi generated
        "**/*\$\$*",
    )

    val debugTree =
        fileTree("$buildDir/tmp/kotlin-classes/debug") { exclude(fileFilter) }
    val mainSrc = "${project.projectDir}/src/main/java"

    sourceDirectories.setFrom(files(listOf(mainSrc)))
    classDirectories.setFrom(files(listOf(debugTree)))
    executionData.setFrom(
        fileTree("$buildDir") {
            include(
                listOf(
                    "outputs/unit_test_code_coverage/debugUnitTest/testDebugUnitTest.exec",
                    "outputs/code_coverage/debugAndroidTest/connected/**/*.ec"
                )
            )
        }
    )
}

tasks.withType<Test> {
    configure<JacocoTaskExtension> {
        isIncludeNoLocationClasses = true
        excludes = listOf("jdk.internal.*")
    }
}

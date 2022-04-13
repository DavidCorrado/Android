

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // Fix to not need next line in Gradle 7.2 https://github.com/gradle/gradle/issues/16958
        val libs = project.extensions.getByType<VersionCatalogsExtension>()
            .named("libs") as org.gradle.accessors.dm.LibrariesForLibs
        classpath(libs.android.gradle.plugin)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.hilt.gradle.plugin)
        classpath(libs.jacoco.core)
    }
}

// https://issuetracker.google.com/issues/195035558
subprojects {
    configurations.all {
        resolutionStrategy {
            eachDependency {
                if ("org.jacoco" == requested.group) {
                    useVersion(libs.versions.jacoco.get())
                }
            }
        }
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}


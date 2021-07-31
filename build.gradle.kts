buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        //Fix to not need next line in Gradle 7.2 https://github.com/gradle/gradle/issues/16958
        val libs = project.extensions.getByType<VersionCatalogsExtension>()
            .named("libs") as org.gradle.accessors.dm.LibrariesForLibs
        classpath(libs.android.gradlePlugin)
        classpath(libs.kotlin.gradlePlugin)
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
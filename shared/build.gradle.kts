import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("android.extensions")
    id("kotlinx-serialization")
}

repositories {
    gradlePluginPortal()
    google()
    jcenter()
    mavenCentral()
}

kotlin {
    android()
    ios {
        binaries {
            framework {
                baseName = "shared"
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Dependencies.Coroutines.core)
                implementation(Dependencies.Koin.core)

                implementation(Dependencies.Ktor.core)
                implementation(Dependencies.Ktor.serialization)
                implementation(Dependencies.Ktor.logging)

                implementation(Dependencies.UserPreferences.settings)
                implementation(Dependencies.UserPreferences.settingsNoArg)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(Dependencies.Test.Kotlin.common)
                implementation(Dependencies.Test.Kotlin.annotations)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Dependencies.Koin.core)
                implementation(Dependencies.Koin.androidViewModel)

                implementation(Dependencies.timber)

                implementation(Dependencies.Ktor.android)
            }
        }
        val iosMain by getting {
            dependencies {
                implementation(Dependencies.Koin.core)
                implementation(Dependencies.Ktor.ios)
            }
        }
    }
}

android {
    compileSdkVersion(Versions.compileSdk)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(Versions.minSdk)
        targetSdkVersion(Versions.targetSdk)
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("debug") {
            versionNameSuffix = "-debug"
        }

        getByName("release") {
            isMinifyEnabled = true
            consumerProguardFiles("shared-proguard-rules.pro")
        }
    }
}

val packForXcode by tasks.creating(Sync::class) {
    group = "build"
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val sdkName = System.getenv("SDK_NAME") ?: "iphonesimulator"
    val targetName = "ios" + if (sdkName.startsWith("iphoneos")) "Arm64" else "X64"
    val framework = kotlin.targets.getByName<KotlinNativeTarget>(targetName).binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)
    val targetDir = File(buildDir, "xcode-frameworks")
    from({ framework.outputDirectory })
    into(targetDir)
}

tasks.getByName("build").dependsOn(packForXcode)
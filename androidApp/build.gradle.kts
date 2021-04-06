plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
}

repositories {
    gradlePluginPortal()
    google()
    jcenter()
    mavenCentral()
}

dependencies {
    implementation(project(":shared"))

    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.AndroidX.fragment)
    implementation(Dependencies.AndroidX.constraintLayout)

    implementation(Dependencies.Ktx.core)

    implementation(Dependencies.Coroutines.android)

    implementation(Dependencies.Koin.core)
    implementation(Dependencies.Koin.android)

    implementation(Dependencies.timber)

    implementation(Dependencies.Lifecycle.process)

    implementation(Dependencies.UI.pageIndicator)
    implementation(Dependencies.UI.coordinatorLayout)
    implementation(Dependencies.UI.materialComponents)

    implementation(Dependencies.Image.loader)
    annotationProcessor(Dependencies.Image.loaderAnnotationCompiler)
}

val versionMajor = 1
val versionMinor = 0
val versionPatch = 1

android {
    compileSdkVersion(Versions.compileSdk)

    defaultConfig {
        applicationId = "com.ludens.androidApp"
        minSdkVersion(Versions.minSdk)
        targetSdkVersion(Versions.targetSdk)
        versionCode = versionMajor * 10000 + versionMinor * 100 + versionPatch
        versionName = "${versionMajor}.${versionMinor}.${versionPatch}"
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }

    buildTypes {
        getByName("debug") {
            versionNameSuffix = "-debug"
        }

        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        viewBinding = true
    }
}

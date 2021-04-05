object Versions {
    const val minSdk = 24
    const val targetSdk = 30
    const val compileSdk = 30

    const val kotlin = "1.4.30"
    const val koin = "3.0.0-alpha-4"
    const val ktor = "1.5.2"
    const val settings = "0.7.4"
}

object Dependencies {

    object Koin {
        const val core = "org.koin:koin-core:${Versions.koin}"
        const val androidViewModel = "org.koin:koin-android-viewmodel:${Versions.koin}"
    }

    object Coroutines {
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2-native-mt"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.2"
    }

    object AndroidX {
        const val appCompat = "androidx.appcompat:appcompat:1.2.0"
        const val fragment = "androidx.fragment:fragment-ktx:1.2.5"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.2"
    }

    object Lifecycle {
        private val process_version = "2.1.0"

        val process = "androidx.lifecycle:lifecycle-process:$process_version"
    }

    object Ktx {
        const val core = "androidx.core:core-ktx:1.3.2"
    }

    object Ktor {
        const val core = "io.ktor:ktor-client-core:${Versions.ktor}"
        const val logging = "io.ktor:ktor-client-logging:${Versions.ktor}"
        const val serialization = "io.ktor:ktor-client-serialization:${Versions.ktor}"
        const val android = "io.ktor:ktor-client-android:${Versions.ktor}"
        const val ios = "io.ktor:ktor-client-ios:${Versions.ktor}"
    }

    object UserPreferences {
        const val settings = "com.russhwolf:multiplatform-settings:${Versions.settings}"
        const val settingsNoArg = "com.russhwolf:multiplatform-settings-no-arg:${Versions.settings}"
    }

    object Test {
        object Kotlin {
            const val common = "org.jetbrains.kotlin:kotlin-test-common:${Versions.kotlin}"
            const val annotations = "org.jetbrains.kotlin:kotlin-test-annotations-common:${Versions.kotlin}"
        }
    }

    object UI {
        const val coordinatorLayout = "androidx.coordinatorlayout:coordinatorlayout:1.1.0"
        const val pageIndicator = "com.github.zhpanvip:viewpagerindicator:1.0.6"
        const val materialComponents = "com.google.android.material:material:1.2.1"
    }

    object Image {
        const val loader = "com.github.bumptech.glide:glide:4.11.0"
        const val loaderAnnotationCompiler = "com.github.bumptech.glide:compiler:4.11.0"
    }

    const val timber = "com.jakewharton.timber:timber:4.7.1"
}

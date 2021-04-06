buildscript {
    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(kotlin("gradle-plugin", Versions.kotlin))
        classpath(kotlin("serialization", Versions.kotlin))

        classpath("com.android.tools.build:gradle:4.1.2")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()

        maven(url = "https://dl.bintray.com/ekito/koin")
        maven(url = "https://jitpack.io")
    }
}

repositories {
    mavenCentral()
}

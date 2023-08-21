plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
        }
        pod("Sentry") {
            version = "8.7.1"

            /**
             * We need this to avoid a conflict between Sentry's `SentryMechanismMeta` class and a class the Kotlin
             * compiler tries to create with the same name. Without this we can't reference Sentry on the Kotlin side:
             *
             * > Task :shared:cinteropSentryIosArm64
             * Exception in thread "main" java.lang.IllegalArgumentException:
             * 'SentryMechanismMeta' is going to be declared twice
             *
             * Related issue: https://youtrack.jetbrains.com/issue/KT-41709
             */
            extraOpts = listOf("-compiler-option", "-DSentryMechanismMeta=SentryMechanismMetaUnavailable")
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                //put your multiplatform dependencies here
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val iosMain by getting {
            dependencies {
                implementation("com.rickclephas.kmp:nsexception-kt-sentry:0.1.9")
            }
        }
    }
}

android {
    namespace = "com.fredporciuncula.sentrynsexceptionkt"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
    }
}
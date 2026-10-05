plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.infidelrahul.antigravitymobile"
    compileSdk = 37
    buildToolsVersion = "37.0.0"
    ndkVersion = System.getenv("ANDROID_NDK_VERSION") ?: "30.0.16248370"

    defaultConfig {
        applicationId = "com.infidelrahul.antigravitymobile"
        minSdk = 28
        targetSdk = 37
        versionCode = 1
        versionName = "0.1.0"

        ndk {
            abiFilters += "arm64-v8a"
        }
        externalNativeBuild {
            cmake {
                cFlags += "-std=c11 -Wall -Wextra -Werror"
                cppFlags += "-std=c11 -Wall -Wextra -Werror"
            }
        }
    }

    val releaseKeystore = System.getenv("KEYSTORE_FILE")?.let { file(it) }
        ?: rootProject.file("release.keystore").takeIf { it.exists() }
        ?: file("release.keystore").takeIf { it.exists() }

    val keystorePassword = System.getenv("KEYSTORE_PASSWORD")?.takeIf { it.isNotBlank() && it != "true" } ?: "antigravitymobile123"
    val keyAliasValue = System.getenv("KEY_ALIAS")?.takeIf { it.isNotBlank() && it != "true" } ?: "antigravitymobile"
    val keyPasswordValue = System.getenv("KEY_PASSWORD")?.takeIf { it.isNotBlank() && it != "true" } ?: "antigravitymobile123"
    val releaseSigningReady = releaseKeystore != null &&
        releaseKeystore.exists() &&
        !keystorePassword.isNullOrBlank() &&
        !keyAliasValue.isNullOrBlank() &&
        !keyPasswordValue.isNullOrBlank()

    signingConfigs {
        create("release") {
            if (releaseSigningReady) {
                storeFile = releaseKeystore
                storePassword = keystorePassword
                keyAlias = keyAliasValue
                keyPassword = keyPasswordValue
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            if (releaseSigningReady) {
                signingConfig = signingConfigs.getByName("release")
            }
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            applicationIdSuffix = ".debug"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/DEPENDENCIES"
        }
        jniLibs {
            useLegacyPackaging = true
        }
    }

    lint {
        abortOnError = false
        checkReleaseBuilds = false
    }
}

dependencies {
    implementation(platform("androidx.compose:compose-bom:2026.09.01"))
    implementation("androidx.activity:activity-compose:1.11.0")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.9.4")
    implementation("androidx.core:core-ktx:1.17.0")
    implementation("androidx.browser:browser:1.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
}

tasks.withType<com.android.build.gradle.internal.tasks.CheckAarMetadataTask>().configureEach {
    enabled = false
}

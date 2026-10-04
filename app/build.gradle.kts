plugins { id("com.android.application"); id("org.jetbrains.kotlin.android"); id("org.jetbrains.kotlin.plugin.compose") }

android { namespace="com.infidelrahul.antigravitymobile"; compileSdk=36
 defaultConfig { applicationId="com.infidelrahul.antigravitymobile"; minSdk=28; targetSdk=36; versionCode=1; versionName="0.1.0"; ndk { abiFilters += "arm64-v8a" }; externalNativeBuild { cmake { cppFlags += "-std=c11 -Wall -Wextra -Werror" } } }
 buildTypes { release { isMinifyEnabled=true; proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),"proguard-rules.pro") }; debug { applicationIdSuffix=".debug" } }
 buildFeatures { compose=true; buildConfig=true }
 externalNativeBuild { cmake { path=file("src/main/cpp/CMakeLists.txt") } }
 packaging { jniLibs { useLegacyPackaging=true } }
 kotlinOptions { jvmTarget="17" }
}

dependencies { implementation(platform("androidx.compose:compose-bom:2026.09.01")); implementation("androidx.activity:activity-compose:1.11.0"); implementation("androidx.compose.ui:ui"); implementation("androidx.compose.material3:material3"); implementation("androidx.compose.material:material-icons-extended"); implementation("androidx.lifecycle:lifecycle-runtime-compose:2.9.4"); implementation("androidx.core:core-ktx:1.17.0"); implementation("androidx.browser:browser:1.9.0"); implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2"); implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0") }

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "id.musofa.liquidglass"
    compileSdk = 36

    defaultConfig {
        applicationId = "id.musofa.liquidglass"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "0.1.0"
    }

    buildFeatures { compose = true }
}

dependencies {
    val composeBom = platform("androidx.compose:compose-bom:2025.11.01")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation("androidx.activity:activity-compose:1.12.0")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.10.0")

    // Kyant0 Backdrop: the Liquid Glass rendering library.
    implementation("io.github.kyant0:backdrop:2.0.1")

    debugImplementation("androidx.compose.ui:ui-tooling")
}

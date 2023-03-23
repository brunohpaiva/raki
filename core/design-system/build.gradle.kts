plugins {
    id("raki.android.library")
    id("raki.android.library.compose")
    id("raki.android.hilt")
}

android {
    namespace = "codes.bruno.raki.core.designsystem"
}

dependencies {
    implementation(libs.androidx.core)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.ui.tooling)

    api(libs.coil.compose)
}
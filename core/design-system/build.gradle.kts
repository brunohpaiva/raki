plugins {
    id("raki.android.library")
    id("raki.android.library.compose")
}

android {
    namespace = "codes.bruno.raki.core.designsystem"
}

dependencies {
    implementation(libs.androidx.core)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.ui.tooling)
}
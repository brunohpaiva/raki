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
    implementation(libs.accompanist.systemuicontroller)

    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.ui.tooling)
    api(libs.omico.m3PullRefresh)

    api(libs.coil.compose)
}
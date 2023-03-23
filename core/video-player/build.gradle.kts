plugins {
    id("raki.android.library")
    id("raki.android.library.compose")
}

android {
    namespace = "codes.bruno.raki.core.videoplayer"
}

dependencies {
    implementation(project(":core:design-system"))

    implementation(libs.androidx.compose.material.iconsextended)
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.exoplayer.dash)
}
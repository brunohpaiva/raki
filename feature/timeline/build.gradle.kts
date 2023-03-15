plugins {
    id("raki.android.feature")
    id("raki.android.library.compose")
}

android {
    namespace = "codes.bruno.raki.feature.timeline"
}

dependencies {
    implementation(libs.androidx.paging.compose)
    implementation(libs.coil.compose)
}
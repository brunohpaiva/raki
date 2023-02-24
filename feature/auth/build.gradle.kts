plugins {
    id("raki.android.feature")
    id("raki.android.library.compose")
}

android {
    namespace = "codes.bruno.raki.feature.auth"
}

dependencies {
    implementation(libs.androidx.browser)
}
plugins {
    id("raki.android.feature")
    id("raki.android.library.compose")
}

android {
    namespace = "codes.bruno.raki.feature.timeline"

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }
}

dependencies {
    coreLibraryDesugaring(libs.android.desugarLibs)

    implementation(project(":core:video-player"))

    implementation(libs.androidx.paging.compose)
    implementation(libs.coil.compose)
}
plugins {
    id("raki.android.feature")
    id("raki.android.library.compose")
}

android {
    namespace = "codes.bruno.raki.feature.search"

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }
}

dependencies {
    coreLibraryDesugaring(libs.android.desugarLibs)
}
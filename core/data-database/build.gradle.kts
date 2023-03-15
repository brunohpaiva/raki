plugins {
    id("raki.android.library")
    id("raki.android.hilt")
    id("com.google.devtools.ksp")
}

android {
    namespace = "codes.bruno.raki.core.data.database"

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }
}

dependencies {
    coreLibraryDesugaring(libs.android.desugarLibs)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.core)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.paging)
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}
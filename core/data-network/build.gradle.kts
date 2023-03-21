plugins {
    id("raki.android.library")
    id("raki.android.hilt")
    id("com.google.devtools.ksp")
}

android {
    namespace = "codes.bruno.raki.core.data.network"

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }
}

dependencies {
    coreLibraryDesugaring(libs.android.desugarLibs)

    implementation(project(":core:domain-model"))
    implementation(project(":core:data-datastore"))

    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi)
    implementation(libs.okhttp.logging)

    implementation(libs.moshi.adapters)
    ksp(libs.moshi.codegen)
}

plugins {
    id("raki.android.library")
    id("raki.android.hilt")
}

android {
    namespace = "codes.bruno.raki.core.data.network"
}

dependencies {
    implementation(project(":core:domain-model"))
    implementation(project(":core:data-datastore"))

    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi)
    implementation(libs.okhttp.logging)
}

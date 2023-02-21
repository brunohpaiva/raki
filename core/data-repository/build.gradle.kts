plugins {
    id("raki.android.library")
    id("raki.android.hilt")
}

android {
    namespace = "codes.bruno.raki.core.data.repository"
}

dependencies {
    implementation(project(":core:domain-model"))
    implementation(project(":core:domain-repository"))

    implementation(project(":core:data-datastore"))
    implementation(project(":core:data-network"))
}
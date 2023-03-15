plugins {
    id("raki.jvm")
}

dependencies {
    implementation(project(":core:domain-model"))
    implementation(project(":core:domain-repository"))

    implementation(libs.hilt.core)
    implementation(libs.androidx.paging.common)
}
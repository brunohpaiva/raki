plugins {
    id("raki.jvm")
}

dependencies {
    implementation(project(":core:domain-model"))
    implementation(libs.androidx.paging.common)
}
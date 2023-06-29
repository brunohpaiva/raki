import com.google.protobuf.gradle.id

plugins {
    id("raki.android.library")
    id("raki.android.hilt")
    id("com.google.protobuf")
}

android {
    namespace = "codes.bruno.raki.core.data.datastore"

    defaultConfig {
        consumerProguardFile("consumer-proguard-rules.pro")
    }
}

dependencies {
    implementation(project(":core:domain-model"))
    implementation(libs.androidx.datastore)
    api(libs.protobuf)
}

protobuf {
    protoc {
        // TODO: find a way to fetch via version catalog without affecting Gradle performance
        artifact = "com.google.protobuf:protoc:3.23.3"
    }

    generateProtoTasks {
        all().forEach {
            it.builtins {
                id("java") {
                    option("lite")
                }
                id("kotlin") {
                    option("lite")
                }
            }
        }
    }
}
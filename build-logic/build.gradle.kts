plugins {
    `kotlin-dsl`
}

group = "codes.bruno.raki.buildlogic"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidLibrary") {
            id = "raki.android.library"
            implementationClass = "AndroidLibrary"
        }
        register("androidLibraryCompose") {
            id = "raki.android.library.compose"
            implementationClass = "AndroidLibraryCompose"
        }
        register("androidFeature") {
            id = "raki.android.feature"
            implementationClass = "AndroidFeature"
        }
        register("androidHilt") {
            id = "raki.android.hilt"
            implementationClass = "AndroidHilt"
        }
        register("jvm") {
            id = "raki.jvm"
            implementationClass = "Jvm"
        }
    }
}

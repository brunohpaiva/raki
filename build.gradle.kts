import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.protobuf) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.versions)
}

tasks.withType<DependencyUpdatesTask> {
    val stableVersionRegex = "^[0-9,.v-]+(-r)?$".toRegex()
    val stableStrings = arrayOf("release", "final", "ga")

    fun isStable(version: String): Boolean {
        return stableStrings.any { version.contains(it, true) }
                || stableVersionRegex.matches(version)
    }

    gradleReleaseChannel = "current"

    rejectVersionIf {
        isStable(currentVersion) && !isStable(candidate.version)
    }
}
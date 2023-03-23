pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "raki"

includeBuild("build-logic")
include(":app")
include(":core:design-system")
include(":core:domain-model")
include(":core:domain-repository")
include(":core:domain-usecase")
include(":core:data-repository")
include(":core:data-database")
include(":core:data-datastore")
include(":core:data-network")
include(":core:video-player")
include(":feature:auth")
include(":feature:timeline")
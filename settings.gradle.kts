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

includeBuild("m3-pullrefresh") {
    dependencySubstitution {
        substitute(module("me.omico.lux:lux-androidx-compose-material3-pullrefresh")).using(project(":library"))
    }
}

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
include(":feature:search")
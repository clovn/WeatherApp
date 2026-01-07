pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "SemestrWork"
include(":app")
include(":core:network")
include(":core:util")
include(":feature:home:api")
include(":feature:home:impl")
include(":feature:pick:api")
include(":feature:pick:impl")
include(":core:local")
include(":feature:auth:api")
include(":feature:auth:impl")

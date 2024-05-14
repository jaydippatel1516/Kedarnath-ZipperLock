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
        jcenter()
        mavenCentral()
        maven { url= uri{"https://www.jitpack.io"} }
        maven { url = uri{"https://maven.google.com" }}
        maven { url = uri{"https://jitpack.io" }}
        gradlePluginPortal()
    }
}

rootProject.name = "Kedarnath"
include(":app")
 
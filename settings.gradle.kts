enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositories {
        mavenLocal()
        mavenCentral()

        google()
    }

    val group = "io.github.merseyside"
    val catalogVersions = "1.7.6"
    versionCatalogs {
        val androidLibs by creating {
            from("$group:catalog-version-android:$catalogVersions")
        }

        val common by creating {
            from("$group:catalog-version-common:$catalogVersions")
        }

        val catalogPlugins by creating {
            from("$group:catalog-version-plugins:$catalogVersions")
        }
    }
}

include(":app")
include(":calendar-core")
include(":calendar-views")

rootProject.name = "mersey-calendar-view"
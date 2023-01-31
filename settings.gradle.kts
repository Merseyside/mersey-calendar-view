enableFeaturePreview("VERSION_CATALOGS")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        mavenLocal()

        google()
    }

    val group = "io.github.merseyside"
    val catalogVersions = "1.6.6"
    versionCatalogs {
        val androidLibs by creating {
            from("$group:catalog-version-android:$catalogVersions")
            version("mersey-android", "2.0.6")
        }

        val common by creating {
            from("$group:catalog-version-common:$catalogVersions")
            version("mersey-time", "1.1.8")
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
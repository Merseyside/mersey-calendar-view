@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    with(catalogPlugins.plugins) {
        plugin(android.application)
        plugin(kotlin.android)
        id(mersey.android.extension.id())
        id(mersey.kotlin.extension.id())
        plugin(kotlin.kapt)
    }
}

android {
    namespace = "com.merseyside.calendar.core.sample"
    compileSdk = Application.compileSdk

    defaultConfig {
        minSdk = Application.minSdk
        targetSdk = Application.targetSdk
        versionCode = 1
        versionName = "0.1.0"

        multiDexEnabled = true
        vectorDrawables.useSupportLibrary = true
    }

    buildFeatures.dataBinding = true

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getByName("release") {
            signingConfig = signingConfigs.getByName("debug")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    packagingOptions {
        resources.excludes.addAll(
            listOf(
                "META-INF/DEPENDENCIES",
                "META-INF/LICENSE",
                "META-INF/LICENSE.txt",
                "META-INF/license.txt",
                "META-INF/NOTICE",
                "META-INF/NOTICE.txt",
                "META-INF/notice.txt",
                "META-INF/ASL2.0",
                "META-INF/*.kotlin_module"
            )
        )
    }

    sourceSets.getByName("main") {
        res.srcDirs(
            "src/main/res/",
            "src/main/res/drawable",
            "src/main/res/layouts/fragment",
            "src/main/res/layouts/activity",
            "src/main/res/layouts/dialog",
            "src/main/res/layouts/view",
            "src/main/res/value/values-light",
            "src/main/res/value/values-night"
        )
    }
}

kotlinExtension {
    setCompilerArgs(
        "-opt-in=kotlin.RequiresOptIn",
        "-Xcontext-receivers",
        "-Xskip-prerelease-check"
    )
}

val androidLibz = listOf(
    androidLibs.coroutines,
    androidLibs.recyclerView,
    androidLibs.navigation,
    androidLibs.navigationUi,
    androidLibs.constraintLayout,
    androidLibs.lifecycleLiveDataKtx,
    androidLibs.appCompat,
    androidLibs.material,
    androidLibs.cardView,
)

val merseyLibs = listOf(
    androidLibs.mersey.archy,
    androidLibs.mersey.utils
)

dependencies {
    implementation(projects.calendarViews)

    androidLibz.forEach { lib -> implementation(lib) }
    merseyLibs.forEach { lib -> implementation(lib) }
}
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    with(catalogPlugins.plugins) {
        plugin(android.library)
        plugin(kotlin.android)
        id(mersey.android.extension.id())
        id(mersey.kotlin.extension.id())
        plugin(kotlin.kapt)
    }
}

android {
    namespace = "com.merseyside.calendar.core"
    compileSdk = Application.compileSdk

    defaultConfig {
        minSdk = Application.minSdk
    }

    buildFeatures.dataBinding = true

    lint {
        lintConfig = rootProject.file(".lint/config.xml")
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        getByName("release") {
            isMinifyEnabled = false
            consumerProguardFiles("proguard-rules.pro")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    val basePath = "src/main/res"
    sourceSets.getByName("main") {
        res.srcDir(basePath)
        res.srcDir("$basePath/layouts/calendar")
    }
}

kotlinExtension {
    setCompilerArgs(
        "-opt-in=kotlin.RequiresOptIn",
        "-Xcontext-receivers"
    )
}

val androidLibz = listOf(
    androidLibs.coroutines,
    androidLibs.appCompat,
    androidLibs.material
)

val androidBundles = listOf(
    androidLibs.bundles.lifecycle
)

val merseyLibs = listOf(
    androidLibs.mersey.utils,
    androidLibs.mersey.adapters
)


dependencies {
    implementation(common.serialization)
    api(common.bundles.mersey.time)
    
    androidLibz.forEach { lib -> implementation(lib) }
    androidBundles.forEach { bundle -> implementation(bundle) }
    merseyLibs.forEach { lib -> implementation(lib) }
}
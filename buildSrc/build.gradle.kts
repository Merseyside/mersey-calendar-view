plugins {
    `kotlin-dsl`
}

dependencies {
    with(catalogGradle) {
        implementation(kotlin.gradle)
        implementation(android.gradle.stable)
        implementation(nexusPublish)
        implementation(maven.publish.plugin)
        implementation(mersey.gradlePlugins)
    }
}

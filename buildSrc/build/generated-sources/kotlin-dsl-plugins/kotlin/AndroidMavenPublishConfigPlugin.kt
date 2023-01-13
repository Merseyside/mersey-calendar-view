/**
 * Precompiled [android-maven-publish-config.gradle.kts][Android_maven_publish_config_gradle] script plugin.
 *
 * @see Android_maven_publish_config_gradle
 */
public
class AndroidMavenPublishConfigPlugin : org.gradle.api.Plugin<org.gradle.api.Project> {
    override fun apply(target: org.gradle.api.Project) {
        try {
            Class
                .forName("Android_maven_publish_config_gradle")
                .getDeclaredConstructor(org.gradle.api.Project::class.java, org.gradle.api.Project::class.java)
                .newInstance(target, target)
        } catch (e: java.lang.reflect.InvocationTargetException) {
            throw e.targetException
        }
    }
}

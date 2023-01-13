/**
 * Precompiled [javadoc-stub-convention.gradle.kts][Javadoc_stub_convention_gradle] script plugin.
 *
 * @see Javadoc_stub_convention_gradle
 */
public
class JavadocStubConventionPlugin : org.gradle.api.Plugin<org.gradle.api.Project> {
    override fun apply(target: org.gradle.api.Project) {
        try {
            Class
                .forName("Javadoc_stub_convention_gradle")
                .getDeclaredConstructor(org.gradle.api.Project::class.java, org.gradle.api.Project::class.java)
                .newInstance(target, target)
        } catch (e: java.lang.reflect.InvocationTargetException) {
            throw e.targetException
        }
    }
}

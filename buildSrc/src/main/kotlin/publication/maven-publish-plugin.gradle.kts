import com.vanniktech.maven.publish.SonatypeHost

plugins {
    id("com.vanniktech.maven.publish")
}

mavenPublishing {
    val projectGitUrl = "https://github.com/Merseyside/mersey-calendar-view"

    pom {
        name.set("Mersey android calendar view")
        description.set("Kotlin based android calendar view")
        url.set(projectGitUrl)

        licenses {
            license {
                name.set("MIT")
                url.set("https://opensource.org/licenses/MIT")
            }
        }
        developers {
            developer {
                id.set("Merseyside")
                name.set("Ivan Sablin")
                email.set("ivanklessablin@gmail.com")
            }
        }

        licenses {
            license {
                name.set("MIT")
                url.set("https://opensource.org/licenses/MIT")
            }
        }

        issueManagement {
            system.set("GitHub")
            url.set("$projectGitUrl/issues")
        }

        scm {
            connection.set("scm:git:$projectGitUrl")
            developerConnection.set("scm:git:$projectGitUrl")
            url.set(projectGitUrl)
        }
    }

    publishToMavenCentral(SonatypeHost.S01)
}


import net.researchgate.release.ReleaseExtension
import org.gradle.api.publish.maven.tasks.PublishToMavenRepository
import org.gradle.plugins.signing.Sign

plugins {
    `maven-publish`
    signing
    id("io.github.gradle-nexus.publish-plugin") version "2.0.0"
    id("net.researchgate.release") version "3.1.0"
}

allprojects {
    group = "{{ cookiecutter.group_id }}"
}

subprojects {
    tasks.register<DependencyReportTask>("allDependencies") {}

    apply(plugin = "maven-publish")
    apply(plugin = "signing")

    plugins.withId("java") {
        extensions.configure<JavaPluginExtension>("java") {
            withSourcesJar()
            withJavadocJar()
        }

        tasks.withType(Javadoc::class.java).configureEach {
            isFailOnError = false
        }

        extensions.configure<PublishingExtension>("publishing") {
            publications {
                if (project.name != "plugin" && findByName("mavenJava") == null) {
                    create<MavenPublication>("mavenJava") {
                        from(components["java"])
                        artifactId = project.name
                    }
                }
            }

            publications.withType<MavenPublication>().configureEach {
                pom {
                    name.set(
                        when (project.name) {
                            "plugin" -> "{{ cookiecutter.plugin_name }}"
                            "core" -> "{{ cookiecutter.plugin_name }} Core"
                            else -> project.name
                        }
                    )
                    description.set("{{ cookiecutter.description }}")
                    url.set("https://github.com/{{ cookiecutter.github_username }}/{{ cookiecutter.github_repo }}")

                    licenses {
                        license {
                            name.set("Apache License 2.0")
                            url.set("https://www.apache.org/licenses/LICENSE-2.0")
                        }
                    }

                    developers {
                        developer {
                            id.set("{{ cookiecutter.developer_id }}")
                            name.set("{{ cookiecutter.developer_name }}")
                            email.set("{{ cookiecutter.developer_email }}")
                        }
                    }

                    scm {
                        url.set("https://github.com/{{ cookiecutter.github_username }}/{{ cookiecutter.github_repo }}")
                        connection.set("scm:git:https://github.com/{{ cookiecutter.github_username }}/{{ cookiecutter.github_repo }}.git")
                        developerConnection.set("scm:git:ssh://git@github.com:{{ cookiecutter.github_username }}/{{ cookiecutter.github_repo }}.git")
                    }
                }
            }
        }
    }

    extensions.configure<SigningExtension>("signing") {
        val keyFile = System.getenv("SIGNING_KEY_FILE")
        val keyPass = System.getenv("SIGNING_KEY_PASSWORD")

        if (!keyFile.isNullOrBlank()) {
            val keyText = file(keyFile).readText()
            useInMemoryPgpKeys(keyText, keyPass)

            val publishing = extensions.findByType(PublishingExtension::class.java)
            if (publishing != null) {
                sign(publishing.publications)
            }
        }
    }

    tasks.withType<PublishToMavenRepository>().configureEach {
        dependsOn(tasks.withType<Sign>())
    }
}

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://ossrh-staging-api.central.sonatype.com/service/local/"))
            snapshotRepositoryUrl.set(uri("https://central.sonatype.com/repository/maven-snapshots/"))
            username.set(System.getenv("OSSRH_USERNAME"))
            password.set(System.getenv("OSSRH_PASSWORD"))
        }
    }
}

configure<ReleaseExtension> {
    buildTasks.set(listOf("publishToSonatype", "closeAndReleaseSonatypeStagingRepository"))
    versionPropertyFile.set("gradle.properties")
    tagTemplate.set("\$name-\$version")

    with(git) {
        requireBranch.set("master")
    }
}

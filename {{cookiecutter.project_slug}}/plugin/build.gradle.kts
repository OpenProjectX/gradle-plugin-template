plugins {
    id("buildsrc.convention.kotlin-jvm")
    `java-gradle-plugin`
}

dependencies {
    implementation(project(":core"))
    testImplementation(libs.junitJupiter)
    testImplementation(gradleTestKit())
    testRuntimeOnly(libs.junitPlatformLauncher)
}

gradlePlugin {
    plugins {
        create("{{ cookiecutter.extension_name }}") {
            id = "{{ cookiecutter.plugin_id }}"
            implementationClass = "{{ cookiecutter.package_name }}.plugin.{{ cookiecutter.plugin_class_name }}Plugin"
            displayName = "{{ cookiecutter.plugin_name }}"
            description = "{{ cookiecutter.description }}"
        }
    }
}

pluginManagement {
    includeBuild("../..")

    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "{{ cookiecutter.project_slug }}-basic-example"

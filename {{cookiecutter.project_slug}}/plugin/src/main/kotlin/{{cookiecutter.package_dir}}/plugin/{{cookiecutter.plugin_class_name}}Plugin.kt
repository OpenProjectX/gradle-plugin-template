package {{ cookiecutter.package_name }}.plugin

import {{ cookiecutter.package_name }}.core.GreetingService
import org.gradle.api.Plugin
import org.gradle.api.Project

class {{ cookiecutter.plugin_class_name }}Plugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create(
            "{{ cookiecutter.extension_name }}",
            {{ cookiecutter.plugin_class_name }}Extension::class.java
        )
        extension.message.convention("Hello from {{ cookiecutter.plugin_name }}")

        project.tasks.register("sayHello") { task ->
            task.group = "{{ cookiecutter.extension_name }}"
            task.description = "Prints the configured greeting."
            task.doLast {
                println(GreetingService().greeting(extension.message.get()))
            }
        }
    }
}

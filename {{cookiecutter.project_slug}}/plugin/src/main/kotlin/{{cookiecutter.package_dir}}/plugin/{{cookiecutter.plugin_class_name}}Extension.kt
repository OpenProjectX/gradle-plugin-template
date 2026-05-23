package {{ cookiecutter.package_name }}.plugin

import org.gradle.api.provider.Property

abstract class {{ cookiecutter.plugin_class_name }}Extension {
    abstract val message: Property<String>
}

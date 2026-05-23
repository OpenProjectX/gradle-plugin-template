package {{ cookiecutter.package_name }}.core

class GreetingService {
    fun greeting(message: String): String = message.trim().ifEmpty { "Hello from {{ cookiecutter.plugin_name }}" }
}

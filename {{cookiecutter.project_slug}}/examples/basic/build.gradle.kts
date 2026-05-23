plugins {
    java
    id("{{ cookiecutter.plugin_id }}")
}

{{ cookiecutter.extension_name }} {
    message.set("Hello from the basic example")
}

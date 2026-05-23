plugins {
    `kotlin-dsl`
}

kotlin {
    jvmToolchain({{ cookiecutter.java_version }})
}

dependencies {
    implementation(libs.kotlinGradlePlugin)
}

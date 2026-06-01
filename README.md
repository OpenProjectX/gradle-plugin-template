# Gradle Plugin Template

A [Cookiecutter](https://cookiecutter.readthedocs.io/) template for creating multi-module Gradle plugins in Kotlin. The generated project follows the structure used by `gradle-mirror`: a reusable `core` module, a `plugin` module using `java-gradle-plugin`, Gradle TestKit functional tests, a local composite-build example, and Maven Central publishing wiring.

## Using This Template

### Option A: Cookiecutter

Install Cookiecutter once:

```bash
pipx install cookiecutter
# or: brew install cookiecutter
# or: pip install cookiecutter
```

Generate a project:

```bash
cookiecutter gh:openprojectx/gradle-plugin-template

# From a local clone:
cookiecutter path/to/gradle-plugin-template
```

Cookiecutter prompts for the plugin ID, package, class name, GitHub metadata, Java version, and publishing metadata.

### Option B: GitHub Template Repository

GitHub's **Use this template** button copies placeholders as-is. Cookiecutter is recommended because it renders file contents and package directories automatically.

## Generated Project Structure

```text
<project_slug>/
├── core/                         # Reusable Kotlin library code for the plugin
├── plugin/                       # Gradle plugin implementation and TestKit tests
├── examples/basic/               # Composite-build example using includeBuild("../..")
├── buildSrc/                     # Shared Gradle convention plugins
├── doc/user-guide.adoc           # AsciiDoc user guide rendered to HTML by `asciidoctor`
├── .github/workflows/            # Release and GitHub Pages docs publishing workflows
├── gradle/libs.versions.toml     # Version catalog
├── build.gradle.kts              # Publishing, signing, docs, and release configuration
├── settings.gradle.kts           # Auto-discovers subprojects, excluding examples
└── gradle.properties
```

## Template Variables

| Variable | Default | Description |
| --- | --- | --- |
| `project_name` | My Gradle Plugin | Human-readable project name. |
| `project_slug` | derived | Directory and repository name. |
| `group_id` | derived | Maven group ID. |
| `package_name` | derived | Kotlin package for generated source. |
| `package_dir` | derived | Package path used for generated source directories. |
| `plugin_id` | derived | Gradle plugin ID. |
| `plugin_name` | derived | Display name in plugin metadata and POMs. |
| `plugin_class_name` | derived | Prefix for the generated plugin and extension classes. |
| `extension_name` | derived | Gradle extension name and plugin declaration name. |
| `description` | derived | Plugin description for metadata and POMs. |
| `github_username` | OpenProjectX | GitHub username or organization. |
| `github_repo` | derived | GitHub repository name. |
| `developer_id` | OpenProjectX | Developer ID in generated POMs. |
| `developer_name` | OpenProjectX | Developer name in generated POMs. |
| `developer_email` | admin@openprojectx.org | Developer email in generated POMs. |
| `kotlin_version` | 2.2.21 | Kotlin version. |
| `java_version` | 17 | Java toolchain version. |

## Development

Run the generated project tests:

```bash
env GRADLE_USER_HOME=/data/.gradle ./gradlew test --no-configuration-cache
```

Run the generated example:

```bash
env GRADLE_USER_HOME=/data/.gradle ./gradlew -p examples/basic sayHello --no-configuration-cache
```

## Documentation

The generated project ships an AsciiDoc user guide under `doc/user-guide.adoc`. Render it to HTML:

```bash
./gradlew asciidoctor
```

Output is written to `build/docs` (`index.html` is the entry point). The `.github/workflows/publish-docs.yml` workflow builds and deploys this guide to GitHub Pages after each successful release.

## Publishing

Set credentials and signing variables, then run the release task:

```bash
export OSSRH_USERNAME=...
export OSSRH_PASSWORD=...
export SIGNING_KEY_FILE=path/to/key.gpg
export SIGNING_KEY_PASSWORD=...

./gradlew release
```

## Tech Stack

- Kotlin 2.2.21
- Gradle 9.4.1 with configuration cache
- Java 17 toolchain by default
- Gradle TestKit functional tests

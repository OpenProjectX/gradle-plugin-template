package {{ cookiecutter.package_name }}.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import {{ cookiecutter.package_name }}.core.GreetingService;

/**
 * Prints a greeting produced by the shared {@code GreetingService}, mirroring the
 * Gradle plugin's {@code sayHello} task. The same {@code core} logic backs both
 * the Gradle and Maven plugins, so behaviour stays consistent across build tools.
 *
 * <p>Run it directly with {@code mvn {{ cookiecutter.extension_name }}:say-hello}, or bind it to a
 * lifecycle phase in your {@code pom.xml}.
 */
@Mojo(name = "say-hello", defaultPhase = LifecyclePhase.INITIALIZE, threadSafe = true)
public class {{ cookiecutter.plugin_class_name }}Mojo extends AbstractMojo {

    /** Message to greet with; falls back to the default greeting when blank. */
    @Parameter(property = "{{ cookiecutter.extension_name }}.message",
            defaultValue = "Hello from {{ cookiecutter.plugin_name }}")
    private String message;

    /** Skip execution. */
    @Parameter(property = "{{ cookiecutter.extension_name }}.skip", defaultValue = "false")
    private boolean skip;

    @Override
    public void execute() throws MojoExecutionException {
        if (skip) {
            getLog().info("{{ cookiecutter.extension_name }}: skipped");
            return;
        }

        String greeting = new GreetingService().greeting(message);
        getLog().info(greeting);
    }
}

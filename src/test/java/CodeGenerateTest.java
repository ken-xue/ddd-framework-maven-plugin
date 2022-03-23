import io.ddd.framework.config.Config;
import io.ddd.framework.config.DataSource;
import io.ddd.framework.mojo.ExecutorMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.junit.Test;

public class CodeGenerateTest {
    @Test
    public void testCodeGenerate() throws MojoExecutionException, MojoFailureException {
        DataSource dataSource = new DataSource();
        Config config = new Config();
        ExecutorMojo executorMojo = new ExecutorMojo();
        executorMojo.setDataSource(dataSource);
        executorMojo.setConfig(config);
        executorMojo.execute();
    }
}

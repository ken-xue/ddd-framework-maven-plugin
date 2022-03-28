import io.ddd.framework.config.Config;
import io.ddd.framework.config.DataSource;
import io.ddd.framework.mojo.CodeMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

public class CodeGenerateTest {
//    @Test
    public void testCodeGenerate() throws MojoExecutionException, MojoFailureException {
        DataSource dataSource = new DataSource();
        Config config = new Config();
        CodeMojo codeMojo = new CodeMojo();
        codeMojo.setDataSource(dataSource);
        codeMojo.setConfig(config);
        codeMojo.execute();
    }
}

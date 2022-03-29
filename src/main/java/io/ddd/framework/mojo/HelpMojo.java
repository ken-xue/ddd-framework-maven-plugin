package io.ddd.framework.mojo;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * 删除上次生成的代码文件
 */
@Slf4j
@Mojo(name = "help", defaultPhase = LifecyclePhase.PACKAGE, threadSafe = true)
public class HelpMojo extends AbstractMojo {
    /**
     * 帮助
     * @return
     */
    @SneakyThrows
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        log.info("Maven code generate help");
        log.info("");
        log.info("use generate cmd:");
        log.info("             mvn ddd-framework:generate");
        log.info("delete last file:");
        log.info("             mvn ddd-framework:delete");
    }
}

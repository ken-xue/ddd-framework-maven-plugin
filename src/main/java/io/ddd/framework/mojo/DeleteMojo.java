package io.ddd.framework.mojo;

import io.ddd.framework.handler.CodeHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

@Slf4j
@Mojo( name= "delete" , defaultPhase= LifecyclePhase.PACKAGE,threadSafe = true)
public class DeleteMojo extends AbstractMojo {

    @SneakyThrows
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        log.info("delete gen");
        CodeHandler handler = new CodeHandler(null);
        handler.deleteGenFile();
    }

}

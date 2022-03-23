package io.ddd.framework.mojo;

import io.ddd.framework.config.Config;
import io.ddd.framework.config.DataSource;
import io.ddd.framework.handler.CodeHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.sql.Connection;

@Slf4j
@Mojo( name= "code" , defaultPhase= LifecyclePhase.PACKAGE,threadSafe = true)
public class ExecutorMojo extends AbstractMojo {
    /**
     * 数据库连接
     */
    private Connection conn;
    /**
     * 数据源配置
     */
    @Parameter(required = true)
    private DataSource dataSource;
    /**
     * 路径表名配置
     */
    @Parameter(required = true)
    private Config config;

    @SneakyThrows
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        log.info("config:{}",config);
        log.info("dataSource:{}",dataSource);
        //1.加载用户配置
        conn = dataSource.getConn();
        //2.
        CodeHandler handler = new CodeHandler(conn);
        handler.execute(config);
    }

}

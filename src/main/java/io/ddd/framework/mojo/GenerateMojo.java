package io.ddd.framework.mojo;

import io.ddd.framework.config.Config;
import io.ddd.framework.config.DataSource;
import io.ddd.framework.handler.GenerateCodeHandler;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.sql.Connection;
@Data
@Slf4j
@Mojo( name= "generate" , defaultPhase= LifecyclePhase.PACKAGE,threadSafe = true)
public class GenerateMojo extends AbstractMojo {
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
        log.info("conn:{}",conn);
        //2.创建代码生成器处理器
        GenerateCodeHandler handler = new GenerateCodeHandler(conn);
        log.info("handler:{}",handler);
        handler.execute(config);
    }

}

package io.ddd.framework.mojo;

import io.ddd.framework.constant.Constant;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * 删除上次生成的代码文件
 */
@Slf4j
@Mojo(name = "delete", defaultPhase = LifecyclePhase.PACKAGE, threadSafe = true)
public class DeleteMojo extends AbstractMojo {
    /**
     * 删除上次生成的文件
     * @return
     */
    @SneakyThrows
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        log.info("delete generator code");
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(Constant.STORE_LAST_GENERATOR_FILE_PATH)))) {
            String fileName;
            while ((fileName = bufferedReader.readLine()) != null) {
                File file = new File(fileName);
                if (!file.exists()) {
                    log.error("删除文件失败：{} 文件不存在", fileName);
                } else {
                    log.info("删除：{} ", fileName);
                    file.delete();
                }
            }
        } catch (Exception e) {
            log.error("执行删除异常：{} ", e.getMessage());
        }
    }
}

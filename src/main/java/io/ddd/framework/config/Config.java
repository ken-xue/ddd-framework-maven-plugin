package io.ddd.framework.config;

import lombok.Data;
import lombok.ToString;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * 配置信息
 */
@Data
@ToString
public class Config {
    /**
     * 需要生成的表名字
     */
    @Parameter(required = true)
    private String[] tableNames;
    /**
     * 排除的字段: 默认全部生成
     */
    @Parameter(required = true)
    private String[] excludeFields;
    /**
     * 需要用到的代码模板
     */
    @Parameter(required = true)
    private String[] templates;
    /**
     * 生成代码路径
     */
    @Parameter
    private String sourcePath;
    /**
     * 表前缀
     */
    @Parameter
    private String[] tablePrefixes;
    /**
     * 模块名称
     */
    @Parameter(required = true)
    private String moduleName;
    /**
     * 包名称
     */
    @Parameter(required = true)
    private String packageName;
    /**
     * 作者
     */
    @Parameter
    private String author;
    /**
     * 邮件
     */
    @Parameter
    private String email;
}

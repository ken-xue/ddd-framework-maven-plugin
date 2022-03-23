package io.ddd.framework.config;

import io.ddd.framework.enums.DbType;
import lombok.Data;
import lombok.ToString;
import org.apache.maven.plugins.annotations.Parameter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库配置
 */
@Data
@ToString
public class DataSource {
    /**
     * 数据库类型
     */
    @Parameter
    private DbType dbType;
    /**
     * 驱动连接的URL
     */
    @Parameter(required = true)
    private String url;
    /**
     * 驱动名称
     */
    @Parameter(required = true)
    private String driverName;
    /**
     * 数据库连接用户名
     */
    @Parameter(required = true)
    private String username;
    /**
     * 数据库连接密码
     */
    @Parameter(required = true)
    private String password;
    /**
     * 判断数据库类型
     * @return 类型枚举值
     */
    public DbType getDbType() {
        if (null == dbType) {
            if (driverName.contains("mysql")) {
                dbType = DbType.MYSQL;
            } else if (driverName.contains("oracle")) {
                dbType = DbType.ORACLE;
            }
        }
        return dbType;
    }
    /**
     * 创建数据库连接对象
     * @return Connection
     */
    public Connection getConn() {
        Connection conn = null;
        try {
            Class.forName(driverName);
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}

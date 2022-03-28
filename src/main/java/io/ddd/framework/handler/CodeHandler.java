package io.ddd.framework.handler;

import io.ddd.framework.config.Config;
import io.ddd.framework.constant.Constant;
import io.ddd.framework.database.ColumnDO;
import io.ddd.framework.database.TableDO;
import io.ddd.framework.enums.QuerySQL;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: 麦奇
 * @Date: 21-11-13 下午7:58
 * @Desc: 代码生成器
 */
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class CodeHandler {

    private Connection connection;

    public void execute(Config configuration) {
        String[] tableNames = configuration.getTableNames();
        log.info("tablesNames:{}", tableNames);
        List fileNames = new ArrayList<String>();
        for(String v:tableNames) {
            if (!StringUtils.isNotBlank(v)) log.error("tableName must not null");
            //2.查询表结构
            Map<String, String> table = queryTable(v);
            //3.查询列信息
            List<Map<String, String>> columns = queryColumns(v);
            //4.排除字段
            List<String> exclude = Arrays.asList(configuration.getExcludeFields());
            columns.removeIf(map -> exclude.contains(map.get("columnName")));
            //5.生成文件
            try {
                generate(table, columns,fileNames, configuration);
            } catch (ConfigurationException e) {
                e.printStackTrace();
            }
        }
        storeFileNames(fileNames);
    }


    public void generate(Map<String, String> table, List<Map<String, String>> columns,List fileNames, Config config) throws ConfigurationException {
        //配置信息
        boolean hasBigDecimal = false;
        boolean hasList = false;
        //表信息
        TableDO tableDO = new TableDO();
        tableDO.setTableName(table.get("tableName"));
        tableDO.setComments(table.get("tableComment"));
        //表名转换成Java类名
        String className = table2Java(tableDO.getTableName(), config.getTablePrefixes());
        tableDO.setClassName(className);
        tableDO.setClassname(StringUtils.uncapitalize(className));
        tableDO.setAllLowName(StringUtils.lowerCase(className));
        //列信息
        List<ColumnDO> columnsList = new ArrayList<>();
        for (Map<String, String> column : columns) {
            ColumnDO columnDO = new ColumnDO();
            columnDO.setColumnName(column.get("columnName"));
            columnDO.setDataType(column.get("dataType"));
            columnDO.setComments(column.get("columnComment"));
            columnDO.setExtra(column.get("extra"));
            //列名转换成Java属性名
            String attrName = column2Java(columnDO.getColumnName());
            columnDO.setAttrName(attrName);
            columnDO.setAttrname(StringUtils.uncapitalize(attrName));
            //列的数据类型，转换成Java类型
            PropertiesConfiguration configuration = new PropertiesConfiguration("generator.properties");
            String attrType = configuration.getString(columnDO.getDataType(), column2Java(columnDO.getDataType()));
            columnDO.setAttrType(attrType);

            if (!hasBigDecimal && attrType.equals("BigDecimal")) {
                hasBigDecimal = true;
            }
            if (!hasList && "array".equals(columnDO.getExtra())) {
                hasList = true;
            }
            //是否主键
            if ("PRI".equalsIgnoreCase(column.get("columnKey")) && tableDO.getPk() == null) {
                tableDO.setPk(columnDO);
            }
            columnsList.add(columnDO);
        }
        tableDO.setColumns(columnsList);

        //没主键，则第一个字段为主键
        if (tableDO.getPk() == null) tableDO.setPk(tableDO.getColumns().get(0));

        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);
        //封装模板数据
        Map<String, Object> map = new HashMap<>();
        map.put("tableName", tableDO.getTableName());
        map.put("comments", tableDO.getComments());
        map.put("pk", tableDO.getPk());
        map.put("className", tableDO.getClassName());
        map.put("classname", tableDO.getClassname());
        map.put("allLowName", tableDO.getAllLowName());
        map.put("pathName", tableDO.getClassname().toLowerCase());
        map.put("columns", tableDO.getColumns());
        map.put("hasBigDecimal", hasBigDecimal);
        map.put("hasList", hasList);
        map.put("basePackageName", config.getBasePackageName());
        map.put("moduleName", config.getModuleName());
        map.put("author", config.getAuthor());
        map.put("email", config.getEmail());
        map.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        VelocityContext context = new VelocityContext(map);

        //获取模板列表
        Map<String, String> templates = Constant.getTemplates(config.getTemplates(),config.getBasePackageName());
        for (String template : templates.keySet()) {
            //渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate("template/" + template, "UTF-8");
            tpl.merge(context, sw);
            try {
                String fileAbsolutePath = templates.get(template).replace("{moduleName}", config.getModuleName())
                        .replace("{classLowName}", tableDO.getAllLowName()) + template.replace(".vm", "")
                        .replace("Domain", tableDO.getClassName());
                //判断文件夹是否存在,不存在则创建
                String sourcePath = config.getAbsolutePath();
                String dir = fileAbsolutePath.substring(0, fileAbsolutePath.lastIndexOf(Constant.separator));
                if (StringUtils.isNotBlank(sourcePath)) {
                    if (sourcePath.charAt(sourcePath.length() - 1) != File.separatorChar) sourcePath += Constant.separator;
                    dir = sourcePath + dir;
                    fileAbsolutePath = sourcePath + fileAbsolutePath;
                }
                log.info("path:{}",dir);
                File folder = new File(dir);
                if (!folder.exists()) folder.mkdirs();
                fileNames.add(fileAbsolutePath);
                IOUtils.write(sw.toString(), new FileOutputStream(fileAbsolutePath), "UTF-8");
                IOUtils.closeQuietly(sw);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 列名转换成Java属性名
     */
    public String column2Java(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

    /**
     * 表名转换成Java类名
     */
    public String table2Java(String tableName, String[] tablePrefixArray) {
        if (null != tablePrefixArray && tablePrefixArray.length > 0) {
            for (String tablePrefix : tablePrefixArray) {
                if (tableName.startsWith(tablePrefix)) {
                    tableName = tableName.replaceFirst(tablePrefix, "");
                }
            }
        }
        return column2Java(tableName);
    }

    /**
     * 保存生成的文件路径供删除
     * @param fileNames
     */
    private void storeFileNames(List<String> fileNames) {
        try (FileWriter fw = new FileWriter(Constant.STORE_LAST_GENERATOR_FILE_PATH)) {
            for (String s : fileNames) {
                fw.write(s + Constant.lineSeparator);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询表信息
     *
     * @param tableName
     * @return
     */
    public Map<String, String> queryTable(String tableName) {
        Map<String, String> ret = new HashMap<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(String.format(QuerySQL.MYSQL.queryTable, tableName));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ret.put("tableName", resultSet.getString("tableName"));
                ret.put("engine", resultSet.getString("engine"));
                ret.put("tableComment", resultSet.getString("tableComment"));
                ret.put("createTime", resultSet.getString("createTime"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 查询表字段信息
     *
     * @param tableName
     * @return
     */
    public List<Map<String, String>> queryColumns(String tableName) {
        List<Map<String, String>> ret = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(String.format(QuerySQL.MYSQL.queryColumns, tableName));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Map<String, String> map = new HashMap<>();
                map.put("columnName", resultSet.getString("columnName"));
                map.put("dataType", resultSet.getString("dataType"));
                map.put("columnComment", resultSet.getString("columnComment"));
                map.put("columnKey", resultSet.getString("columnKey"));
                map.put("extra", resultSet.getString("extra"));
                ret.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
}


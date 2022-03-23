package io.ddd.framework.enums;

public enum QuerySQL {

    MYSQL(
            "select table_name tableName, engine, table_comment tableComment, create_time createTime from information_schema.tables where table_schema = (select database()) and table_name = '%s'",
            "select column_name columnName, data_type dataType, column_comment columnComment, column_key columnKey, extra from information_schema.columns where table_name = '%s' and table_schema = (select database()) order by ordinal_position"
    ),
    ORACLE(
            "",
            ""
    );

    public final String queryTable;
    public final String queryColumns;

    QuerySQL(String queryTable, String queryColumns) {
        this.queryTable = queryTable;
        this.queryColumns = queryColumns;
    }

}

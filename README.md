# ddd-framework-maven-plugin

## Quick start

1.create you database table

2.import maven dependency

```xml
<plugin>
    <groupId>io.ddd.framework</groupId>
    <artifactId>ddd-framework-maven-plugin</artifactId>
    <version>1.0.2-SNAPSHOT</version>
    <configuration>
        <dataSource>
            <driverName>com.mysql.cj.jdbc.Driver</driverName>
            <url>jdbc:mysql://127.0.0.1:3306/ddd_framework</url>
            <username>root</username>
            <password>123456</password>
        </dataSource>
        <config>
            <absolutePath>/Users/biaoyang/IdeaProjects/ddd/ddd-framework/</absolutePath>
            <tablePrefixes>sys_,biz_</tablePrefixes>
            <moduleName>sys</moduleName>
            <basePackageName>io.ddd.framework</basePackageName>
            <tableNames>sys_user,sys_menu</tableNames>
            <excludeFields>id,uuid,creator,modifier,gmt_create,gmt_modified,deleted</excludeFields>
            <author>麦奇</author>
            <email>biaogejiushibiao@outlook.com</email>
        </config>
    </configuration>
</plugin>
```
3.execute cmd
```shell
mvn ddd-framework:code
```
4.clean last time code
```shell
mvn ddd-framework:delete
```
5.perfect ! success generate code in you project path ! enjoy ! maven plugin source code see

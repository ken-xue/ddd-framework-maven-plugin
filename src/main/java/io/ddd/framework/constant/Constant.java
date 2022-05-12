package io.ddd.framework.constant;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Constant {
    /**
     * 生成的文件存放路径
     */
    public static final String STORE_LAST_GENERATOR_FILE_PATH = "/tmp/record.txt";
    /**
     * 系统分隔符
     */
    public static final String separator = File.separator;
    /**
     * 系统换行符
     */
    public static final String lineSeparator = System.lineSeparator();
    /**
     * 获取模板
     * @return
     */
    public static Map<String, String> getTemplates(String[] chooseTemplate,String basePackageName) {
        //key:模板路径,value:生成后的路径
        Map<String, String> templates = new HashMap<>();
        String basePath = basePackageName.replaceAll("\\.", "/");
        templates.put("DomainMenu.sql.vm", "doc/script/{moduleName}/");
        templates.put("Domain.java.vm", String.format("core/domain/src/main/java/%s/domain/domain/{moduleName}/",basePath));
        //exe
        templates.put("DomainAddCmdExe.java.vm", String.format("core/application/src/main/java/%s/application/{moduleName}/{classLowName}/command/",basePath));
        templates.put("DomainDeleteCmdExe.java.vm", String.format("core/application/src/main/java/%s/application/{moduleName}/{classLowName}/command/",basePath));
        templates.put("DomainGetQryExe.java.vm", String.format("core/application/src/main/java/%s/application/{moduleName}/{classLowName}/command/query/",basePath));
        templates.put("DomainListQryExe.java.vm", String.format("core/application/src/main/java/%s/application/{moduleName}/{classLowName}/command/query/",basePath));
        templates.put("DomainUpdateCmdExe.java.vm", String.format("core/application/src/main/java/%s/application/{moduleName}/{classLowName}/command/",basePath));
        templates.put("DomainPageQryExe.java.vm", String.format("core/application/src/main/java/%s/application/{moduleName}/{classLowName}/command/query/",basePath));
        //service
        templates.put("DomainAppServiceImpl.java.vm", String.format("core/application/src/main/java/%s/application/{moduleName}/{classLowName}/service/",basePath));
        templates.put("DomainAppService.java.vm", String.format("core/core-client/src/main/java/%s/coreclient/api/{moduleName}/",basePath));
        //dto
        templates.put("DomainDTO.java.vm", String.format("core/core-client/src/main/java/%s/coreclient/dto/{moduleName}/{classLowName}/",basePath));
        //do
        templates.put("DomainDO.java.vm", String.format("core/infrastructure/src/main/java/%s/infrastructure/repositoryimpl/{moduleName}/database/dataobject/",basePath));
        //Repository
        templates.put("DomainRepository.java.vm", String.format("core/domain/src/main/java/%s/domain/repository/{moduleName}/",basePath));
        templates.put("DomainRepositoryImpl.java.vm", String.format("core/infrastructure/src/main/java/%s/infrastructure/repositoryimpl/{moduleName}/",basePath));
        //mapper
        templates.put("DomainMapper.java.vm", String.format("core/infrastructure/src/main/java/%s/infrastructure/repositoryimpl/{moduleName}/database/mapper/",basePath));
        templates.put("DomainMapper.xml.vm", "core/infrastructure/src/main/resources/mybatis/{moduleName}/");
        //controller
        templates.put("DomainController.java.vm", String.format("core/adapter/src/main/java/%s/adapter/rest/{moduleName}/",basePath));
        //cmd
        templates.put("DomainAddCmd.java.vm", String.format("core/core-client/src/main/java/%s/coreclient/dto/{moduleName}/{classLowName}/",basePath));
        templates.put("DomainDeleteCmd.java.vm", String.format("core/core-client/src/main/java/%s/coreclient/dto/{moduleName}/{classLowName}/",basePath));
        templates.put("DomainUpdateCmd.java.vm", String.format("core/core-client/src/main/java/%s/coreclient/dto/{moduleName}/{classLowName}/",basePath));
        templates.put("DomainPageQry.java.vm", String.format("core/core-client/src/main/java/%s/coreclient/dto/{moduleName}/{classLowName}/",basePath));
        templates.put("DomainListQry.java.vm", String.format("core/core-client/src/main/java/%s/coreclient/dto/{moduleName}/{classLowName}/",basePath));
        templates.put("DomainGetQry.java.vm", String.format("core/core-client/src/main/java/%s/coreclient/dto/{moduleName}/{classLowName}/",basePath));
        //domain factory
        templates.put("DomainFactory.java.vm", String.format("core/domain/src/main/java/%s/domain/factory/{moduleName}/",basePath));
        //do convert
        templates.put("Domain2DOConvector.java.vm", String.format("core/infrastructure/src/main/java/%s/infrastructure/repositoryimpl/{moduleName}/database/convertor/",basePath));
        templates.put("Domain2DOMapStruct.java.vm", String.format("core/infrastructure/src/main/java/%s/infrastructure/repositoryimpl/{moduleName}/database/convertor/",basePath));
        //dto assembler
        templates.put("Domain2DTOAssembler.java.vm", String.format("core/application/src/main/java/%s/application/{moduleName}/{classLowName}/assembler/",basePath));
        templates.put("Domain2DTOMapStruct.java.vm", String.format("core/application/src/main/java/%s/application/{moduleName}/{classLowName}/assembler/",basePath));
        //event handler
        templates.put("DomainDeleteEventHandler.java.vm",String.format("core/application/src/main/java/%s/application/{moduleName}/{classLowName}/handler/",basePath));
        templates.put("DomainUpdateEventHandler.java.vm",String.format("core/application/src/main/java/%s/application/{moduleName}/{classLowName}/handler/",basePath));
        templates.put("DomainCreateEventHandler.java.vm",String.format("core/application/src/main/java/%s/application/{moduleName}/{classLowName}/handler/",basePath));
        //event
        templates.put("DomainCreateEvent.java.vm",String.format("core/core-client/src/main/java/%s/coreclient/dto/{moduleName}/{classLowName}/event/",basePath));
        templates.put("DomainDeleteEvent.java.vm",String.format("core/core-client/src/main/java/%s/coreclient/dto/{moduleName}/{classLowName}/event/",basePath));
        templates.put("DomainUpdateEvent.java.vm",String.format("core/core-client/src/main/java/%s/coreclient/dto/{moduleName}/{classLowName}/event/",basePath));

        if (Objects.nonNull(chooseTemplate)&&chooseTemplate.length>0){
            Map<String, String> retChooseTemplates = new HashMap<>();
            for (String templateName:chooseTemplate) {
                retChooseTemplates.put(templateName,templates.get(templateName));
            }
            return retChooseTemplates;
        }
        return templates;
    }

}

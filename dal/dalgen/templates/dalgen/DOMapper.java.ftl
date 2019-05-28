<@pp.dropOutputFile />
<#list dalgen.doMappers as doMapper>
<@pp.changeOutputFile name = "/main/java/${doMapper.classPath}/${doMapper.className}.java" />
package ${doMapper.packageName};

<#list doMapper.importLists as import>
import ${import};
</#list>
import java.util.Map;
import org.apache.ibatis.annotations.Param;

/**
 * 由于需要对分页支持,请直接使用对应的DAO类
 * The Table ${doMapper.tableName!}.
 * ${doMapper.desc!}
 */
public interface ${doMapper.className}{

    <#list doMapper.motheds as method>
    /**
     * desc:${method.desc!method.name!}.<br/>
     * descSql = ${method.sql!}
    <#if (method.params?size lte 1)>
    <#list  method.params as param>
     * @param ${param.param} ${param.param}
    </#list>
    <#else>
    * @param params params
    * the define of params
        <#list  method.params as param>
    *     ${param.param} ${param.param}
        </#list>
    </#if>
     * @return ${method.returnClass!}
     */
    <#if (method.params?size lte 1)>
    ${method.returnClass!} ${method.name}(<#list  method.params as param><#if param_index gt 0>,</#if><#if method.params?size gt 1>@Param("${param.param}")</#if>${param.paramType!} ${param.param}</#list>);
    <#else>
    ${method.returnClass!} ${method.name}(Map<String,Object> params);
    </#if>
    </#list>
}
</#list>

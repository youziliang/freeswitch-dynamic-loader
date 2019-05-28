<@pp.dropOutputFile />
<#list dalgen.daos as dao>
    <@pp.changeOutputFile name = "/main/java/${dao.classPath}/${dao.className}.java" />
package ${dao.packageName};

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
<#if dao.beanNamespace??>
import org.springframework.beans.factory.annotation.Qualifier;
</#if>
import org.springframework.stereotype.Repository;
<#list dao.importLists as import>
import ${import};
</#list>

/**
* The Table ${dao.tableName!}.
* ${dao.desc!}
*/
<#if dao.beanNamespace??>
@Repository("${dao.beanNamespace}.${dao.className}")
<#else>
@Repository
</#if>
public class ${dao.className}{

    @Autowired
    <#if dao.beanNamespace??>
    @Qualifier("${dao.beanNamespace}.${dao.doMapper.className}")
    </#if>
    private ${dao.doMapper.className} ${dao.doMapper.className?uncap_first};

    <#list dao.motheds as method>
    /**
     * desc:${method.desc!method.name!}.<br/>
     * descSql = ${method.sql!}
        <#list  method.params as param>
     * @param ${param.param} ${param.param}
        </#list>
     * @return ${method.returnClass!}
     */
    public ${method.returnClass!} ${method.name}(<#list  method.params as param><#if param_index gt 0>,</#if>${param.paramType!} <#assign pagingParam = param.param/>${param.param}</#list>){
    <#if method.pagingFlag == "true">
        int total = ${dao.doMapper.className?uncap_first}.${method.name}Count(<#list  method.params as param><#if param_index gt 0>, </#if>${param.param}</#list>);
        if(total>0){
            ${pagingParam}.setDatas(${dao.doMapper.className?uncap_first}.${method.name}Result(<#list  method.params as param><#if param_index gt 0>, </#if>${param.param}</#list>));
        }else{
            ${pagingParam}.setDatas(Collections.emptyList());
        }
        ${pagingParam}.setTotal(total);
        return ${pagingParam};
    <#else>
        <#if (method.params?size lte 1)>
        return ${dao.doMapper.className?uncap_first}.${method.name}(<#list  method.params as param><#if param_index gt 0>, </#if>${param.param}</#list>);
        <#else>
        Map<String,Object> params=new HashMap<String,Object>();
        <#list  method.params as param>
        params.put("${param.param}",${param.param});
        </#list>
        return ${dao.doMapper.className?uncap_first}.${method.name}(params);
        </#if>

    </#if>
    }
    </#list>
}
</#list>

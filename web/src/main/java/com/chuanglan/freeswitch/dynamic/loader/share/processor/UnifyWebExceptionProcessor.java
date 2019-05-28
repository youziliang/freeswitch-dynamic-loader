package com.chuanglan.freeswitch.dynamic.loader.share.processor;

import com.chuanglan.freeswitch.dynamic.loader.core.model.vo.RespVO;
import com.chuanglan.framework.core.enums.BizCode;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description Controller层捕获未知异常
 * @Author Youziliang
 * @Date 2019/1/30
 */
@Slf4j
@RestControllerAdvice  //Spring
@Component
@Aspect //自定义
public class UnifyWebExceptionProcessor {

    @Autowired
    private HttpServletRequest request;

    @Pointcut(value = "execution(* com.chuanglan.freeswitch.dynamic.loader.web..*.*(..))")
    private void pointcut() {
    }

    @AfterThrowing(pointcut = "pointcut()", throwing = "e")
    public void handleThrowing(Exception e) {

    }

    /**
     * 全局异常处理
     * TODO 先注释，方便调试，上线后打开
     * @param e
     * @return
     */
    /*@ExceptionHandler(value = Exception.class)
    public RespVO unifyExceptionProcess(Exception e) {
        RespVO respVO = new RespVO();
        respVO.setCode(BizCode.Unknown.code());
        respVO.setMsg(BizCode.Unknown.desc());
        log.error("Web未知异常: {}", e.getMessage());
        return respVO;
    }*/

    @InitBinder
    public void initBinder(WebDataBinder binder) {

    }

    @ModelAttribute("BaseRequest")
    public void addAttributes(Model model) {
    }
}

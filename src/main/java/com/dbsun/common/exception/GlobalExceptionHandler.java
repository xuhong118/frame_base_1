package com.dbsun.common.exception;

import com.dbsun.base.BaseController;
import com.dbsun.common.ServerResponse;
import com.dbsun.core.LogManager;
import com.dbsun.core.log.factory.LogTackFactory;
import com.dbsun.util.PathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.TimerTask;


/**
 * 全局的的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法上都会拦截）
 *
 * @author fengshuonan
 * @date 2016年11月12日 下午3:19:56
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends BaseController {

    /**
     * 全局异常处理
     *
     * @author fengshuonan
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ServerResponse unAuth(RuntimeException e) {
        log.info("ip 地址 ===>>>>  {}", super.getIp());
        e.printStackTrace();
        String requestUrl = PathUtil.getRequestUrl(super.request, true);
        TimerTask timerTask = LogTackFactory.exceptionLog(requestUrl, e);
        LogManager.me().executeLog(timerTask);
        return ServerResponse.createByError();
    }


}

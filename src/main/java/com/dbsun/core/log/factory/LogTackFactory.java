package com.dbsun.core.log.factory;


import com.dbsun.common.constant.state.LogType;
import com.dbsun.common.pojo.OpLog;
import com.dbsun.mapper.system.OpLogMapper;
import com.dbsun.util.SpringContextHolder;
import com.dbsun.util.ToolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;

import java.util.TimerTask;

/**
 * @author zhao yi
 * @create 2018-11-20 15:42
 * @desc 日志任务创建工厂
 **/

@DependsOn("springContextHolder")
public class LogTackFactory {

    private static final Logger LOG = LoggerFactory.getLogger(LogTackFactory.class);

    private static OpLogMapper OPTIONAL_LOG_MAPPER = SpringContextHolder.getBean(OpLogMapper.class);


    /***
     * 创建异常日志
     */
    public static TimerTask exceptionLog(final String target, final Exception exception) {
       final String msg = ToolUtil.getExceptionMsg(exception);
        return new TimerTask() {
            @Override
            public void run() {
                OpLog log = LogFactory.createOpLog(LogType.EXCEPTION, exception.getClass().getCanonicalName(), target, msg);
                try {
                    //insert sql
                    OPTIONAL_LOG_MAPPER.insertLog(log);
                } catch (Exception e) {
                    LOG.error("创建异常异常!", e);
                }
            }
        };
    }


}

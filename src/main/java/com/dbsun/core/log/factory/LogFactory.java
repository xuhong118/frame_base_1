package com.dbsun.core.log.factory;

import com.dbsun.common.constant.state.LogType;
import com.dbsun.common.pojo.OpLog;

import java.util.Date;

/**
 * @author zhao yi
 * @create 2018-11-20 15:41
 * @desc 日志创建工厂
 **/
public class LogFactory {


    /***
     * 创建自定义日志
     * @param logType
     * @param logName
     * @param target
     * @param message
     * @return
     */
    public static OpLog createOpLog(LogType logType, String logName, String target, String message) {
        OpLog log = new OpLog();
        log.setLogtype(logType.getMessage());
        log.setLogname(logName);
        log.setTarget(target);
        log.setCreatetime(new Date());
        log.setMessage(message);
        return log;
    }


}

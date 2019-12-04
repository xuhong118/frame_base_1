package com.dbsun.core;

import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * 日志管理器
 *
 * @author zhao yi
 * @create 2018-11-20 15:40
 * @desc
 **/
public class LogManager {


    /**
     * 操作延迟时间(毫秒)
     */
    private static final int OPERATE_DELAY_TIME = 10;

    private static final LogManager LOG_MANAGER = new LogManager();

    /***
     * 异步操作记录日志的线程池
     */
    private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(10, new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread((ThreadGroup) null, r, "LogManager-thread");
        }
    });


    private LogManager() {
        super();
    }


    public static LogManager me() {
        return LOG_MANAGER;
    }
    /***
     * 执行任务
     * @param task
     */
    public void executeLog(TimerTask task) {
        executor.schedule(task, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
    }


}

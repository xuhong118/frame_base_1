package com.dbsun.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceAop {
	
	private static final Logger logger = LoggerFactory.getLogger(DataSourceAop.class);
    @Before("execution(* com.dbsun.service..*.get*(..))")
    public void setReadDataSourceType() {
    	DbContextHolder.setDatabaseType(DatabaseType.SLAVE);
        logger.info("dataSource切换到：Read");
    }
    @Before("execution(* com.dbsun.service..*.add*(..)) || execution(* com.dbsun.service..*.update*(..)) || execution(* com.dbsun.service..*.del*(..))")
    public void setWriteDataSourceType() {
    	DbContextHolder.setDatabaseType(DatabaseType.MASTER);
        logger.info("dataSource切换到：write");
    }
}

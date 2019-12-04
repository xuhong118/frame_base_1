package com.dbsun.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {
//	 private final int dataSourceNumber = 0;
//	 private AtomicInteger count = new AtomicInteger(0);
	 
    protected Object determineCurrentLookupKey() {
//    	Object typeKey = DbContextHolder.getDatabaseType();
//    	if (typeKey.equals(DatabaseType.MASTER)){//获取当前的连接类型判定等于线程中的类型，如果当前是写主库，则默认返回主库
//    		return DbContextHolder.getDatabaseType();
//    	}else{//当前是从库，则需要计算使用哪个数据库进行读取，进行负载均衡
//        // 读 简单负载均衡
//        int number = count.getAndAdd(1);
//        int lookupKey = number % dataSourceNumber;
//        return new Integer(lookupKey);
//    	}
                return DbContextHolder.getDatabaseType();
    }
}

package com.dbsun.job;

import com.dbsun.util.DateUtil;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * 账单逾期催收定时器
 */
@Component
@EnableScheduling
public class BillOverdueJob {

//    @Autowired
//    private BillOverdueService billOverdueService;

    /**
     *
     */
    public void task(){
        System.out.println(DateUtil.get1000ParsedDate() + "标记逾期定时器启动");
        // 调用业务逻辑
    }

}

package com.dbsun.config;

import org.springframework.stereotype.Component;


@Component
//@RabbitListener(queues = "queue.cdr_push.rx")
public class Receiver {
//    @Autowired
//    private PhoCaloutService phoCaloutService;
//
//    @Value("${receiver.ybcf0013}")
//    private String ybcf0013;
//
//    @RabbitHandler
//    public void process(Object obj) {
//        Message msg = (Message)obj;
//        byte[] b = msg.getBody();
//        try {
//            String boby = new String(msg.getBody(), "utf-8");
//            //转为json获取需要的数据
//            JSONObject json = JSONObject.fromObject(boby);
//            JSONObject varibalesJson = JSONObject.fromObject(json.getString("variables"));
//            System.out.println(varibalesJson.getString("duration"));//variables.duration 通话时长
//            System.out.println(varibalesJson.getString("hangup_cause_q850"));//variables.hangup_cause_q850 挂断编码
//            System.out.println(varibalesJson.getString("hangup_cause"));//variables.hangup_cause 挂断原因
//            System.out.println(varibalesJson.getString("bridge_stamp"));//variables.bridge_stamp 接通时间
//            System.out.println(varibalesJson.getString("bridge_epoch"));//variables.bridge_epoch 接通时间 单位秒
//            System.out.println(varibalesJson.getString("task_result"));//variables.task_result 拨打结果 接通是：bridged;否则是空
//            //task_type 呼叫类型内部拨打（1）；自动外呼（2）；手动外呼（3）；呼入（4）；桥接（4）
//            PageData pd = new PageData();
//            pd.put("YBCF009",varibalesJson.getString("ext_fields"));//外接ID根据此ID更新通话记录数据ext_fields
//            pd.put("YBCF0012",varibalesJson.getString("duration"));//通话时长
//            pd.put("YBCF0015",varibalesJson.getString("destination_number"));//拨打电话
//            if(!Tools.isObjEmpty(varibalesJson.getString("task_result"))){
//                pd.put("YBCF0010_TP",1);//通话状态（1接通/2未接通）_YBCF0010_TP
//            }
//            if(varibalesJson.getString("task_type").equals("3")){//呼出
//                pd.put("YBCF0011_TP",2);//通话类型（1呼入/2呼出）_YBCF0011_TP
//            }else if(varibalesJson.getString("task_type").equals("4")){//呼入
//                pd.put("YBCF0011_TP",1);//通话类型（1呼入/2呼出）_YBCF0011_TP
//            }
//            pd.put("YBCF0013",ybcf0013+"&filename="+varibalesJson.getString("task_id"));//通话录音地址
//            phoCaloutService.updateFwById(pd);//根据返回自定义字段更新通话跟进数据
//            System.out.println(pd);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//    }
}

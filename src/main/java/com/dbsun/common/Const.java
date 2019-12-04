package com.dbsun.common;

/**
 * Created by geely
 */
public class Const {

    public static final String CURRENT_USER = "currentUser";

    public static final String EMAIL = "email";
    public static final String USERNAME = "username";

    /**
     * 代扣文件名前缀
     */
    public static final String FILENAME_PREFIX = "C19IBPSDSDF120001_HHXD_";
    /**
     * 回盘文件名前缀
     */
    public static final String FILENAME_COUNTER_PREFIX = "C19IBPSDSDF230001_HHXD_";

    /**
     * 文件开始序号
     */
    public static final Integer FILE_ORDER_NUMBER = 0001;

    /**
     * 代扣成功凭证目录
     */
    public static final String DK_RECEIPT_PREFIX = "C://upload/";

    public interface Follow{
        int FOLLOW_SYSTEM = 0; //系统跟进
        int FOLLOW_SYSTEM_OTHER = 1;//非系统跟进 
    }

    public interface Cart{
        int CHECKED = 1;//即购物车选中状态
        int UN_CHECKED = 0;//购物车中未选中状态

        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
    }

    public interface Role{
        int ROLE_CUSTOMER = 0; //普通用户
        int ROLE_ADMIN = 1;//管理员
    }

    public enum ProductStatusEnum {
        ON_SALE(1,"在线");
        private String value;
        private int code;

        ProductStatusEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }

    public enum OrderStatusEnum{
        CANCELED(0,"已取消"),
        NO_PAY(10,"未支付"),
        PAID(20,"已付款"),
        SHIPPED(40,"已发货"),
        ORDER_SUCCESS(50,"订单完成"),
        ORDER_CLOSE(60,"订单关闭");


        OrderStatusEnum(int code,String value){
            this.code = code;
            this.value = value;
        }
        private String value;
        private int code;

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }

        public static OrderStatusEnum codeOf(int code){
            for(OrderStatusEnum orderStatusEnum : values()){
                if(orderStatusEnum.code == code){
                    return orderStatusEnum;
                }
            }

            throw new RuntimeException("没有找到对应的枚举");
        }

    }

    public enum PayPlatformEnum{
        ALIPAY(1,"支付宝");

        PayPlatformEnum(int code,String value){
            this.code = code;
            this.value = value;
        }
        private String value;
        private int code;

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }

    public enum PaymentTypeEnum{
        ONLINE_PAY(1, "在线支付")
        ;

        PaymentTypeEnum(int code,String value){
            this.code = code;
            this.value = value;
        }
        private String value;
        private int code;

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }

        public static PaymentTypeEnum codeOf(int code) {
            for(PaymentTypeEnum paymentTypeEnum : values()){
                if(paymentTypeEnum.code == code){
                    return paymentTypeEnum;
                }
            }

            throw new RuntimeException("没有找到对应的枚举");
        }
    }

    public enum DKEnum{
        DK_1(-2, "默认"),
        DK_2(-1, "申请已提交"),
        DK_3(0, "代扣已受理"),
        DK_4(1, "代扣成功"),
        DK_5(2, "代扣失败"),
        DK_6(3, "代扣失败"),
        ;

        DKEnum(int code,String value){
            this.code = code;
            this.value = value;
        }
        private String value;
        private int code;

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }

        public static DKEnum codeOf(int code) {
            for(DKEnum dk : values()){
                if(dk.code == code){
                    return dk;
                }
            }

            throw new RuntimeException("没有找到对应的枚举");
        }
    }


}

package com.dbsun.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 路径工具类
 *
 * @author
 */
public class PathUtil {

    /**
     * 获得缓存临时目录
     *
     * @author stylefeng
     * @Date 2017/5/24 22:35
     */
    public static String getTempPath(){
        return System.getProperty("java.io.tmpdir");
    }




    /***
     * 获得请求 全路径 默认不携带参数
     * @param request
     * @return
     */
    public static String getRequestUrl(HttpServletRequest request) {
        return getRequestUrl(request, false);

    }

    /***
     *获得请求 全路径 默认不携带参数
     * @param request
     * @param hasParam 是否要携带参数
     * @return
     */
    public static String getRequestUrl(HttpServletRequest request, boolean hasParam) {
        StringBuilder sb = new StringBuilder();
        sb.append(request.getScheme());  //当前链接使用的协议
        sb.append("://").append(request.getServerName());//服务器地址
        sb.append(request.getContextPath()); //应用名称，如果应用名称为
        sb.append(request.getServletPath()); //请求的相对url
        final String queryString = request.getQueryString();//请求参数
        if (queryString != null && hasParam) {
            sb.append("?").append(request.getQueryString());
        }
        return sb.toString();
    }

}

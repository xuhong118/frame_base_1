package com.dbsun.common.pojo;

import org.apache.ibatis.type.Alias;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 通用实体类
 * pojo
 *
 * @author zhao yi
 * @create 2018-12-21 11:51
 **/
@Alias("pd")
public class PageData extends HashMap implements Serializable {


    private static final long serialVersionUID = 8867820648641441839L;

    public PageData() {
    }

    public PageData(HttpServletRequest request) {
        this.init(request);
    }

    public void init(HttpServletRequest request) {
        Map properties = request.getParameterMap();
        Iterator<Entry<String, String[]>> iterator = properties.entrySet().iterator();
        Entry<String, String[]> next = null;
        String[] valTemp = null;
        StringBuilder values = null;
        while (iterator.hasNext()) {
            next = iterator.next();
            valTemp = next.getValue();
            if (valTemp != null) {
                values = new StringBuilder();
                for (int i = 0; i < valTemp.length; i++) {
                    values.append(valTemp[i].trim()).append(",");
                }
                values.deleteCharAt(values.length() - 1);
                super.put(next.getKey(), values.toString());
            }
        }
    }


    @Override
    public PageData put(Object key, Object value) {
        super.put(key, value);
        return this;
    }

    @Override
    public void putAll(Map t) {
        if (t != null && t.size() > 0) {
            super.putAll(t);
        }
    }

    public String getString(Object key) {
        String str = null;
        if (key == null || key == "") {
            return str;
        } else if (!super.containsKey(key)) {
            return str;
        } else {
            str = (String) get(key);
        }
        return str;
    }


}

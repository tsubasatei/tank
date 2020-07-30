package com.xt.tank;

import java.io.IOException;
import java.util.Properties;

/**
 * 配置文件
 */
public class PropertyMgr {
    static Properties properties = new Properties();
    static {
        try {
            properties.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Integer getInt(String key) {
        String property = properties.getProperty(key);
        if (property == null) return null;
        return Integer.parseInt(property);
    }

    public static String getString(String key) {
        return properties.getProperty(key);
    }
}

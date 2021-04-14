package com.wjw.common.utils;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

/**
 * @author wjw
 * @date 2021/4/12 23:16
 */
public class JsonUtil {

    public static ObjectMapper objMapper = new ObjectMapper();


    public static JsonNode readJsonNodeFromFile(String fileName){
        JsonNode rootNode = null;
        try {
            File file = new File(JsonUtil.class.getClassLoader().getResource(fileName).getFile());
            rootNode = objMapper.readTree(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootNode;
    }

    public static String readJsonStrFromFile(String fileName){
        return readJsonNodeFromFile(fileName).toString();
    }
}

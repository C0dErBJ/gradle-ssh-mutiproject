package com.ZP.util;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * ProjectName: XSY
 * PackageName: com.ZP.utils
 * User: C0dEr
 * Date: 2016-10-11
 * Time: 13:45
 * Description:
 */
public class HttpParameters {
    private String Url;
    private String Method;
    private Map<String, String> Head;
    private Map<String, Object> Param;


    public String getContentType() {
        return ContentType;
    }

    public void setContentType(String contentType) {
        ContentType = contentType;
    }

    private String ContentType;

    public HttpParameters() {
        Head = new HashMap<>();
        Param = new HashMap<>();
        Method = "POST";

    }

    public static HashMap<String, String> generateAuth() {
        try {
            byte[] json = Files.readAllBytes(Paths.get(HttpProxy.class.getResource("/odlAccount.json").toURI()));
            Map<String, String> ps = new ObjectMapper().readValue(new String(json), new TypeReference<Map<String, String>>() {
            });
            return new HashMap<String, String>() {{
                put("Authorization", "Basic " + Base64.getEncoder().encodeToString((ps.get("username") + ":" + ps.get("password")).getBytes()));
            }};
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getUrl() {
        return Url;
    }

    public HttpParameters setUrl(String url) {
        Url = url;
        return this;
    }


    public String getMethod() {
        return Method;
    }

    public HttpParameters setMethod(String method) {
        Method = method;
        return this;
    }

    public Map<String, Object> getParam() {
        return Param;
    }

    public HttpParameters setParam(Map<String, Object> param) {
        Param.putAll(param);
        return this;
    }

    public Map<String, String> getHead() {
        return Head;
    }

    public HttpParameters setHead(Map<String, String> head) {
        Head.putAll(head);
        return this;
    }
}

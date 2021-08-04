package com.lunarshade.vkapp.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SignChecker {

    @Value("${vk.miniapp.client.secret}")
    private String clientSecret;
    private static final String ENCODING = "UTF-8";

    public boolean check(HttpServletRequest request) throws Exception {

        String sign = request.getHeader("authorization");
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, String> launchParams = getLaunchParamsFromHeader(headerNames, request);
        String checkString = getCheckString(launchParams);

        String checkSign = getHashCode(checkString, clientSecret);
        return sign.equals(checkSign);
    }


    private static String getHashCode(String data, String key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(ENCODING), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKey);
        byte[] hmacData = mac.doFinal(data.getBytes(ENCODING));
        return new String(Base64.getUrlEncoder().withoutPadding().encode(hmacData));
    }

    private static String encode(String value) {
        try {
            return URLEncoder.encode(value, ENCODING);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return value;
    }

    private static Map<String, String> getLaunchParamsFromHeader(Enumeration<String> headerNames, HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String param = headerNames.nextElement();
            if (param.startsWith("vk_")) {
                params.put(param, request.getHeader(param));
            }
        }
        return params;
    }

    private static String getCheckString(Map<String, String> params) {
        return params.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> encode(entry.getKey()) + "=" + encode(entry.getValue()))
                .collect(Collectors.joining("&"));
    }
}

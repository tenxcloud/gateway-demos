/*
 * Licensed Materials - Property of tenxcloud.com
 * (C) Copyright 2019 TenxCloud. All Rights Reserved.
 *
 * 2020/8/5 @author peiyun
 */
package com.spring.demo.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HmacSha256Util {

    private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

    public static String hMACSHA256Signature(String data, String key) throws Exception {

        Mac sha256HMAC = Mac.getInstance(HMAC_SHA256_ALGORITHM);
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(), HMAC_SHA256_ALGORITHM);
        sha256HMAC.init(secret_key);

        byte[] array = sha256HMAC.doFinal(data.getBytes());

        StringBuilder sb = new StringBuilder();

        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }

        return sb.toString().toUpperCase();
    }

    public static byte[] signatureReturnBytes(String data, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA256_ALGORITHM);
        Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
        mac.init(signingKey);
        return mac.doFinal(data.getBytes());
    }

    public static void main(String[] args) throws Exception {
        String secret = "R1DUyi3grTJcbTPRKh02LpAOQZu9RZbZ";
        Date d=new Date();
        DateFormat format=new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        String hdate = format.format(d);
        System.out.println("X-Date: "+hdate);
        String digest = hMACSHA256Signature(hdate, secret);
//        String base64_digest= Base64.getEncoder().encode(digest);
        String hmacResult = hMACSHA256Signature("data", "f96384947e0f4de39d3ffef6bd12c551");
        System.out.println("结果:"+hmacResult);
        assert hmacResult.equals("104152c5bfdca07bc633eebd46199f0255c9f49d");
    }
}

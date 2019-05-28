package com.chuanglan.freeswitch.dynamic.loader.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class MD5Util {

    public static String encode(String origin, String charsetname) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname))
                return byteArrayToHex(md.digest(origin.getBytes()));
            else
                return byteArrayToHex(md.digest(origin.getBytes(charsetname)));
        } catch (Exception e) {
            return "";
        }
    }

    public static String encode(File file) {
        int bufferSize = 256 * 1024;
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
        try (
                FileInputStream fileInputStream = new FileInputStream(file);
                DigestInputStream digestInputStream = new DigestInputStream(fileInputStream, messageDigest)
        ) {
            byte[] buffer = new byte[bufferSize];
            while (digestInputStream.read(buffer) > 0) ;
            messageDigest = digestInputStream.getMessageDigest();
            byte[] byteArr = messageDigest.digest();
            return byteArrayToHex(byteArr);
        } catch (Exception e) {
            return "";
        }
    }

    private static String byteArrayToHex(byte byteArr[]) {
        StringBuilder sb = new StringBuilder();
        for (byte aByteArr : byteArr) sb.append(byteToHex(aByteArr));
        return sb.toString();
    }

    private static String byteToHex(byte bytes) {
        int n = bytes;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 获得随机字符串
     *
     * @return
     */
    public static String getNonceStr() {
        Random random = new Random();
        return MD5Util.encode(String.valueOf(random.nextInt(10000)), "UTF-8");
    }

}

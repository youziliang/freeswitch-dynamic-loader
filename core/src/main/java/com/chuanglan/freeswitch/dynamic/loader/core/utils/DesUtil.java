package com.chuanglan.freeswitch.dynamic.loader.core.utils;

import com.chuanglan.freeswitch.dynamic.loader.core.constants.SystemCharset;
import com.chuanglan.freeswitch.dynamic.loader.core.constants.SystemCharset;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;
import java.util.Base64;

public class DesUtil {

    private static String KEY = "u0WkcrXYcE1L822IMe0mrcEm3mNuf26fQWmKaKNLgJWQlDBvoTLV8IpfExGHjPsn";
    private final static byte[] IV = new byte[]{0x31, 0x30, 0x49, 0x4f, 0x30, 0x31, 0x4f, 0x6c};
    private static String DES = "DES";

    public static String bytes2Hex(byte[] bytes) {
        StringBuilder buf = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            buf.append(String.format("%02x", b & 0xff)).append(",");// 使用String的format方法进行转换
        }
        return buf.toString();
    }


    public static String encrypt(String src) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec ks = new DESKeySpec(KEY.getBytes(SystemCharset.UTF8));
        SecretKeyFactory skf = SecretKeyFactory.getInstance(DES);
        SecretKey sk = skf.generateSecret(ks);
        Cipher cip = Cipher.getInstance("DES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(IV);
        cip.init(Cipher.ENCRYPT_MODE, sk, iv);
        //cip.init(Cipher.ENCRYPT_MODE, sk, sr);//没有传递IV
        String dest = Base64.getEncoder().encodeToString(cip.doFinal(src.getBytes(SystemCharset.UTF8)));
        return dest;
    }


    public static String decrypt(String dest) throws Exception {
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] data = decoder.decodeBuffer(dest);
        DESKeySpec dks = new DESKeySpec(KEY.getBytes(SystemCharset.UTF8));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey secretKey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(IV);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        byte decryptedData[] = cipher.doFinal(data);
        String src = new String(decryptedData);
        return src;
    }

}

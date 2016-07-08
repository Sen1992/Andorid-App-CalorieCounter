package edu.monash.infotech.caloriecounter.Tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by sen on 2016/4/18.
 */
public class PasswordMD5 {
    public static String generateCode(String str) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] srcBytes = str.getBytes();
        md5.update(srcBytes);
        byte[] resultBytes = md5.digest();
        String result = new String(resultBytes);
        return result;
    }
}

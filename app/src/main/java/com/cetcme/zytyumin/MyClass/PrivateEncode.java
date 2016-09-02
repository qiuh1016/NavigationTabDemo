package com.cetcme.zytyumin.MyClass;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by qiuhong on 5/5/16.
 */
public class PrivateEncode {

    public static String b64_md5(String str) {
        String result = "";
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            Base64Encoder base64en = new Base64Encoder();
            result = base64en.encode(md5.digest(str.getBytes("utf-8")));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static String getMD5(String info)
    {
        try
        {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes("UTF-8"));
            byte[] encryption = md5.digest();

            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < encryption.length; i++)
            {
                if (Integer.toHexString(0xff & encryption[i]).length() == 1)
                {
                    strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
                }
                else
                {
                    strBuf.append(Integer.toHexString(0xff & encryption[i]));
                }
            }

            return strBuf.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            return "";
        }
        catch (UnsupportedEncodingException e)
        {
            return "";
        }
    }


    private static double EARTH_RADIUS = 6378.137;
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    public static Double GetDistance(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
                Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
        s = s * EARTH_RADIUS * 1000;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    public Boolean isCard(String text) {
//        String reg15 = "^[1-9]\\d{7}((0\\[1-9])|(1[0-2]))(([0\\[1-9]|1\\d|2\\d])|3[0-1])\\d{2}([0-9]|x|X){1}$";

        if (text.length() != 18 ) {
            return false;
        }

        String reg18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X|x)$";
        if (!text.matches(reg18)) {
            return false;
        }

        int summary =
                Integer.parseInt(String.valueOf(text.charAt(0))) * 7 + Integer.parseInt(String.valueOf(text.charAt(1))) * 9 +
                Integer.parseInt(String.valueOf(text.charAt(2))) * 10 + Integer.parseInt(String.valueOf(text.charAt(3))) * 5 +
                Integer.parseInt(String.valueOf(text.charAt(4))) * 8 + Integer.parseInt(String.valueOf(text.charAt(5))) * 4 +
                Integer.parseInt(String.valueOf(text.charAt(6))) * 2 + Integer.parseInt(String.valueOf(text.charAt(7))) +
                Integer.parseInt(String.valueOf(text.charAt(8))) * 6 + Integer.parseInt(String.valueOf(text.charAt(9))) * 3 +
                Integer.parseInt(String.valueOf(text.charAt(10))) * 7 + Integer.parseInt(String.valueOf(text.charAt(11))) * 9 +
                Integer.parseInt(String.valueOf(text.charAt(12))) * 10 + Integer.parseInt(String.valueOf(text.charAt(13))) * 5 +
                Integer.parseInt(String.valueOf(text.charAt(14))) * 8 + Integer.parseInt(String.valueOf(text.charAt(15))) * 4 +
                Integer.parseInt(String.valueOf(text.charAt(16))) * 2;
        int remainder = summary % 11;
        String checkString = "10X98765432";
        String checkBit = String.valueOf(checkString.charAt(remainder));
        return checkBit.equals(String.valueOf(text.charAt(17)).toUpperCase());
    }

    public Boolean ipCheck(String text) {
        if (text != null && !text.isEmpty()) {
            // 定义正则表达式
            String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
            // 判断ip地址是否与正则表达式匹配
            if (text.matches(regex)) {
                // 返回判断信息
                return true;
            } else {
                // 返回判断信息
                return false;
            }
        }
        // 返回判断信息
        return false;
    }

}

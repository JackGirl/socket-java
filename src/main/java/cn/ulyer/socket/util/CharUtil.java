package cn.ulyer.socket.util;

import java.nio.charset.StandardCharsets;

public class CharUtil {


    private final static String GBK = "GBK";



    public static String encode(String msg){
        return new String(msg.getBytes(StandardCharsets.UTF_8));
    }

    public static String changeLine(){
        return "\n";
    }

    public static String enter(){
        return "\r\n";
    }


}

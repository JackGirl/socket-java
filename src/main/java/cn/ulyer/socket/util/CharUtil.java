package cn.ulyer.socket.util;

import java.nio.charset.Charset;

public class CharUtil {


    public final static String ENCODE = "UTF-8";



    public static String encode(String msg){
        return new String(msg.getBytes(Charset.forName(ENCODE)));
    }

    public static String changeLine(){
        return "\n";
    }

    public static String enter(){
        return "\n";
    }


}

package cn.ulyer.socket.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageFormatter {

    final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

    public static String formatterMessage(String prefix ,String content){
        StringBuilder builder = new StringBuilder();
        builder.append(prefix)
                .append(" time:")
                .append(simpleDateFormat.format(new Date()))
                .append(" ")
                .append(content);
        return builder.toString();
    }

    public static String formatterNickMessage(String prefix,String content,String nickName){
        StringBuilder builder = new StringBuilder();
        builder.append(prefix)
                .append(" time:")
                .append(simpleDateFormat.format(new Date()))
                .append(" ")
                .append(nickName).append(":")
                .append(content);
        return builder.toString();
    }


}

package cn.ulyer.socket.enums;

import lombok.Getter;

@Getter
public enum MessageType {


    PUBLIC_MESSAGE("[广场消息]"),

    PRIVATE_MESSAGE("[私人消息]"),

    SYSTEM_MESSAGE("[系统消息]");

    String prefix;

    MessageType(String prefix){
        this.prefix = prefix;
    }

}

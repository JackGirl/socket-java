package cn.ulyer.socket.model;

import cn.ulyer.socket.enums.MessageType;
import lombok.Builder;
import lombok.experimental.Accessors;

import java.util.Date;


/**
 * 消息模型
 */
@Builder
@Accessors(chain = true)
public class Message {

    private String messageId;

    private User sendUser;

    private User toUser;

    private MessageType messageType;

    private Date sendTime;

    private String serverId;

}

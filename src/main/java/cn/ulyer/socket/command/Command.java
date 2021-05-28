package cn.ulyer.socket.command;


import cn.ulyer.socket.context.LinkContext;
import cn.ulyer.socket.handler.MessageHandler;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Map;


/**
 * -v  输入的值 所有命令都有
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public abstract class Command {


    protected Map<String,String> argMap;

    protected LinkContext linkContext;

    String value;

    MessageHandler messageHandler;

    public Command(MessageHandler messageHandler){
        this.messageHandler = messageHandler;
    }


    public abstract void execute();



}

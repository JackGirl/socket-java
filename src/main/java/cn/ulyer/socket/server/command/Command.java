package cn.ulyer.socket.server.command;


import cn.ulyer.socket.server.link.Link;
import cn.ulyer.socket.server.Server;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;


/**
 * -v  输入的值 所有命令都有
 */
@Data
@Accessors(chain = true)
public abstract class Command {


    protected Map<String,String> argMap;

    protected Server server;

    protected Link clientLink;

    String value;

    public Command(){}

    public Command(Server server, Link clientLink,String value,  Map<String,String> argMap){
        this.server = server;
        this.clientLink = clientLink;
        this.value = value;
        this.argMap = argMap;
    }


    public abstract void execute();



}

package cn.ulyer.socket.security;

import cn.ulyer.socket.command.Command;
import cn.ulyer.socket.link.Link;

public interface ServerSecurityManager {


    void checkLink(Link link);

    void checkCommand(Command command);


}

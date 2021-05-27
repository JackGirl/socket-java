package cn.ulyer.socket.server.security;

import cn.ulyer.socket.server.command.Command;
import cn.ulyer.socket.server.link.Link;

public interface ServerSecurityManager {


    void checkLink(Link link);

    void checkCommand(Command command);


}

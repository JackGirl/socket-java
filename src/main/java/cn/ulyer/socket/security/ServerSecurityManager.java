package cn.ulyer.socket.security;

import cn.ulyer.socket.command.CMD;
import cn.ulyer.socket.link.Link;

public interface ServerSecurityManager {


    boolean checkLink(Link link);

    boolean checkCommand(CMD command);


}

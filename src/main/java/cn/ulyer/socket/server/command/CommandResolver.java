package cn.ulyer.socket.server.command;

import cn.ulyer.socket.server.Configuration;
import cn.ulyer.socket.server.Server;
import cn.ulyer.socket.server.link.Link;

public interface CommandResolver {

    Command resolverCommand(Configuration configuration, Server server, Link link, String value);


}

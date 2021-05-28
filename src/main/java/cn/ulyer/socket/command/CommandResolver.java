package cn.ulyer.socket.command;

import cn.ulyer.socket.context.LinkContext;
import cn.ulyer.socket.server.Configuration;
import cn.ulyer.socket.server.Server;
import cn.ulyer.socket.link.Link;

public interface CommandResolver {

    Command resolverCommand(LinkContext context, String value);


}

package cn.ulyer.socket.server.store;

import cn.ulyer.socket.server.Server;
import cn.ulyer.socket.server.link.Link;

import java.util.List;

public interface MessageStore {

    void store(Link link, Server server,String msg);


    List<String> allMessage(Server server);



}

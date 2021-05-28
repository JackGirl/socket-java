package cn.ulyer.socket.store;

import cn.ulyer.socket.model.Message;
import cn.ulyer.socket.server.Server;
import cn.ulyer.socket.link.Link;

import java.util.List;

public interface MessageStore {

    void store(Message message);


    List<String> allMessage(Server server);



}

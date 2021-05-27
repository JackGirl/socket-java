package cn.ulyer.socket.server.store;

import cn.ulyer.socket.server.link.Link;

import java.util.Collection;
import java.util.Collections;


public interface LinkStore {

    void put(String key,Link clientLink);

    Link getLink(String key);

    void remove(String key);

    Collection<? extends Link> getAllLink();
}

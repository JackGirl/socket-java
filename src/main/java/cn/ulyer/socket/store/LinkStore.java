package cn.ulyer.socket.store;

import cn.ulyer.socket.link.Link;

import java.util.Collection;


public interface LinkStore {

    void put(String key,Link clientLink);

    Link getLink(String key);

    void remove(String key);

    Collection<? extends Link> getAllLink();
}

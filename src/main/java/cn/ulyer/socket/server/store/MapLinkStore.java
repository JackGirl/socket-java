package cn.ulyer.socket.server.store;

import cn.ulyer.socket.server.link.Link;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class MapLinkStore implements LinkStore {


    private Map<String, Link> linkMap = new ConcurrentHashMap<>(128);



    @Override
    public void put(String key, Link clientLink) {
        this.linkMap.put(key,clientLink);
    }

    @Override
    public Link getLink(String key) {
        return this.linkMap.get(key);
    }


    @Override
    public void remove(String key) {
        this.linkMap.remove(key);
    }

    @Override
    public Collection<? extends Link> getAllLink() {
        return linkMap.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }
}

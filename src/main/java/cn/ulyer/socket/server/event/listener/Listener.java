package cn.ulyer.socket.server.event.listener;

import cn.ulyer.orm.config.OrmConfiguration;
import cn.ulyer.socket.server.Server;
import cn.ulyer.socket.server.event.Event;

public interface Listener {

    void onEvent(Event event, OrmConfiguration configuration, Server server);

    boolean canDo(Event event);
}

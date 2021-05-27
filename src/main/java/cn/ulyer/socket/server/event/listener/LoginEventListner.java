package cn.ulyer.socket.server.event.listener;

import cn.ulyer.orm.config.OrmConfiguration;
import cn.ulyer.socket.server.Server;
import cn.ulyer.socket.server.event.Event;
import cn.ulyer.socket.server.event.LoginEvent;

public class LoginEventListner implements Listener{


    @Override
    public void onEvent(Event event, OrmConfiguration configuration, Server server) {

    }

    @Override
    public boolean canDo(Event event) {
        return event.getClass().equals(LoginEvent.class);
    }


}

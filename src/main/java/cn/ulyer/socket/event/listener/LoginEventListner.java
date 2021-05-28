package cn.ulyer.socket.event.listener;

import cn.ulyer.socket.server.Configuration;
import cn.ulyer.socket.server.Server;
import cn.ulyer.socket.event.Event;
import cn.ulyer.socket.event.LoginEvent;

public class LoginEventListner implements Listener{


    @Override
    public void onEvent(Event event) {

    }

    @Override
    public boolean canDo(Event event) {
        return event.getClass().equals(LoginEvent.class);
    }


}

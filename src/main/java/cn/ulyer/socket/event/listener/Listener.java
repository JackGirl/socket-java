package cn.ulyer.socket.event.listener;

import cn.ulyer.socket.event.Event;

public interface Listener {

    void onEvent(Event event);

    boolean canDo(Event event);

}

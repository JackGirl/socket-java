package cn.ulyer.socket.event.listener;

import cn.ulyer.socket.event.Event;

public class SendMessageListener implements Listener{
    @Override
    public void onEvent(Event event) {

    }

    @Override
    public boolean canDo(Event event) {
        return false;
    }
}

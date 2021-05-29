package cn.ulyer.socket.event.listener;

import cn.ulyer.socket.context.LinkContext;
import cn.ulyer.socket.event.Event;
import cn.ulyer.socket.event.LogoutEvent;
import cn.ulyer.socket.model.User;
import cn.ulyer.socket.store.LinkStore;
import cn.ulyer.socket.store.UserStore;


public class LogoutListener implements Listener{


    @Override
    public void onEvent(Event event) {
        LogoutEvent logoutEvent = (LogoutEvent) event;
        User user = (User) logoutEvent.getSource();
        LinkContext context = LinkContext.get();
        UserStore userStore = context.getConfiguration().getUserStore();
        userStore.remove(user.getUsername());
        LinkStore linkStore = context.getConfiguration().getLinkStore();
        linkStore.remove(user.getUsername());
        context.setUser(null);
        context.getLink().setUser(null);
    }

    @Override
    public boolean canDo(Event event) {
        return event.getClass().equals(LogoutEvent.class);
    }


}

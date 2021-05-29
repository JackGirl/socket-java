package cn.ulyer.socket.event.listener;

import cn.ulyer.socket.context.LinkContext;
import cn.ulyer.socket.enums.MessageType;
import cn.ulyer.socket.event.Event;
import cn.ulyer.socket.event.LoginEvent;
import cn.ulyer.socket.link.Link;
import cn.ulyer.socket.model.User;
import cn.ulyer.socket.store.LinkStore;
import cn.ulyer.socket.store.UserStore;

import java.util.Collection;

public class LoginEventListner implements Listener{


    @Override
    public void onEvent(Event event) {
        LinkContext context = LinkContext.get();
        User user = (User) event.getSource();
        LinkStore store = context.getConfiguration().getLinkStore();
        UserStore userStore = context.getConfiguration().getUserStore();
        userStore.store(user);
        context.getLink().setUser(user);
        context.setUser(user);
        context.getConfiguration().getLinkStore().put(user.getUsername(),context.getLink());
        Collection<? extends Link> links = store.getAllLink();
        for (Link link : links) {
            link.writeToClient(MessageType.SYSTEM_MESSAGE.getPrefix()+user.getName()+"已上线");
        }
    }

    @Override
    public boolean canDo(Event event) {
        return event.getClass().equals(LoginEvent.class);
    }


}

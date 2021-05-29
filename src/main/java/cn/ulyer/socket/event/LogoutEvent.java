package cn.ulyer.socket.event;

import cn.ulyer.socket.model.User;

public class LogoutEvent implements Event{

    private User user;

    public LogoutEvent(User user){
        this.user = user;
    }

    @Override
    public String getName() {
        return "logout";
    }

    @Override
    public Object getSource() {
        return user;
    }
}

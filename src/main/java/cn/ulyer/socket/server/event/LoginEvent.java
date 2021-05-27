package cn.ulyer.socket.server.event;

import cn.ulyer.socket.model.User;

public class LoginEvent implements Event{

    private User user;

    @Override
    public String getName() {
        return "login";
    }

    @Override
    public Object getSource() {
        return user;
    }
}

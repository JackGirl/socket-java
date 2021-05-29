package cn.ulyer.socket.store;

import cn.ulyer.socket.model.User;

import java.util.Collection;

public interface UserStore {

    User getUser(String username);

    Collection<User> getAllUser();

    Collection<User> range(int start,int end);

    void store(User user);

    void remove(String username);


}

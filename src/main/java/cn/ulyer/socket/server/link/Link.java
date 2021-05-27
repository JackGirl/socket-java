package cn.ulyer.socket.server.link;

import cn.ulyer.socket.model.User;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

public interface Link extends Serializable {

    void writeToClient(String msg);

    String readLine() throws IOException;

    Optional<User> getUser();

    void setUser(User user);

    void close();

    boolean isClosed();


}

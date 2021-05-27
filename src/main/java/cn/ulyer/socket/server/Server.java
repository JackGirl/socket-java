package cn.ulyer.socket.server;

import java.io.IOException;

public interface Server {

    void setConfiguration(Configuration configuration);

    Configuration getConfiguration();

    void setPort(int port);

    void start() throws IOException;

    int getPort();

    String getId();


}

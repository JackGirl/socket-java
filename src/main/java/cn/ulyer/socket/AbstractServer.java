package cn.ulyer.socket;

import cn.ulyer.socket.server.Configuration;
import cn.ulyer.socket.server.Server;

import java.io.IOException;

public abstract class AbstractServer implements Server {

    protected int port;

    protected String serverId;

    protected Configuration configuration;

    public AbstractServer(int port,String serverId){
        this.port = port;
        this.serverId = serverId;
    }

    @Override
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public void setPort(int port) {
        this.port = port;
    }


    @Override
    public abstract void start() throws IOException;

    @Override
    public int getPort() {
        return this.port;
    }

    @Override
    public String getId() {
        return serverId;
    }
}

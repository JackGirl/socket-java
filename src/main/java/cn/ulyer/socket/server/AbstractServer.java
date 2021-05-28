package cn.ulyer.socket.server;


import cn.ulyer.socket.event.Event;
import cn.ulyer.socket.event.listener.Listener;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@Slf4j
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

    /**
     * 发布事件后给监听器处理
     * @param event
     */
    @Override
    public void publishEvent(Event event) {
        log.info("published one event for name:{}",event.getName());
        Collection<Listener> ls  = this.configuration.getListeners();
        Iterator<Listener> iterator = ls.iterator();
        while (iterator.hasNext()){
            try{
                Listener next = iterator.next();
                if(next.canDo(event)){
                    next.onEvent(event);
                }
            }catch (Exception e){
                this.configuration.getErrorHandler().handler(e);
            }
        }
    }
}

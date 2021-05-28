package cn.ulyer.socket.context;


import cn.ulyer.socket.link.Link;
import cn.ulyer.socket.model.User;
import cn.ulyer.socket.server.Configuration;
import cn.ulyer.socket.server.Server;

public class LinkContext {

    private Server server;

    private Link link;

    private Configuration configuration;

    private User user;

    private final static ThreadLocal <LinkContext> LINK_CONTEXT_THREAD_LOCAL = new ThreadLocal<>();

    public LinkContext(Server server,Configuration configuration,Link link){
        this.server = server;
        this.configuration = configuration;
        this.link = link;
    }

    public static void setContext(LinkContext linkContext){
        LINK_CONTEXT_THREAD_LOCAL.set(linkContext);
    }

    public static LinkContext get(){
        return LINK_CONTEXT_THREAD_LOCAL.get();
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

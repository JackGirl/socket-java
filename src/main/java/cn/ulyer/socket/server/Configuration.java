package cn.ulyer.socket.server;

import cn.hutool.core.lang.Assert;
import cn.ulyer.socket.command.*;
import cn.ulyer.socket.context.LinkContext;
import cn.ulyer.socket.event.listener.Listener;
import cn.ulyer.socket.event.listener.LoginEventListner;
import cn.ulyer.socket.event.listener.LogoutListener;
import cn.ulyer.socket.handler.DefaultErrorHandler;
import cn.ulyer.socket.handler.ErrorHandler;
import cn.ulyer.socket.security.CMDProxy;
import cn.ulyer.socket.security.DefaultSecurityManager;
import cn.ulyer.socket.security.ServerSecurityManager;
import cn.ulyer.socket.store.LinkStore;
import cn.ulyer.socket.store.MapLinkStore;
import cn.ulyer.socket.store.RedisUserStore;
import cn.ulyer.socket.store.UserStore;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Configuration {

    private final  Map<String,Class<? extends Command>> commandMap = new ConcurrentHashMap<>(64);

    public final  Map<String,String> commandExt = new ConcurrentHashMap<>(64);

    private LinkStore linkStore = new MapLinkStore();

    private final Collection<Listener> listeners = new LinkedList<>();

    private ServerSecurityManager serverSecurityManager = new DefaultSecurityManager();

    private CommandResolver commandResolver = new DefaultCommandResolver();

    private ErrorHandler errorHandler = new DefaultErrorHandler();

    private UserStore userStore = new RedisUserStore();

    private Configuration(){
        commandMap.put("login", LoginCommand.class);
        commandExt.put("login","登录  -u 用户名 -p password");
        commandMap.put("logout", LogoutCommand.class);
        commandExt.put("logout","退出登录");
        commandMap.put("send", SendMessageCommand.class);
        commandExt.put("send","发送信息 -r 指定用户 不加默认所有人 -v 发送的消息");
        commandMap.put("show",ShowCommand.class);
        commandExt.put("show","查看 -c 查看可用命令 ");
        listeners.add(new LoginEventListner());
        listeners.add(new LogoutListener());
    }


    public static Configuration newConfig(){
        return new Configuration();
    }



    /**
     * 连接存储
     * @param linkStore
     */
    public void setLinkStore(LinkStore linkStore){
        this.linkStore = linkStore;
    }

    public LinkStore getLinkStore(){
        Assert.notNull(linkStore,"linkStore is null please init before start server");
        return linkStore;
    }


    /**
     * 命令执行前进行安全检查
     * @param securityManager
     */
    void setSecurityManager(ServerSecurityManager securityManager){
        this.serverSecurityManager = securityManager;
    }

    public ServerSecurityManager getServerSecurityManager(){
        return serverSecurityManager;
    }


    /**
     * 注册命令
     * @param command
     * @param clz
     * @param extMsg
     */
    public void registerCommand(String command,Class<? extends Command> clz,String extMsg){
       Class c = commandMap.putIfAbsent(command,clz);
        if(c.equals(clz)){
            commandExt.put(command,extMsg);
        }
    }

    public CMD resolverCommand(LinkContext context, String value){
       return CMDProxy.proxy(commandResolver.resolverCommand(context,value)) ;
    }

    private CMD newCommand(Class<?extends CMD> commandClass){
        if(commandClass==null||commandClass.equals(UnknowCommand.class)){
            return new UnknowCommand();
        }
        try {
            Constructor constructor = commandClass.getConstructor();
            return (Command) constructor.newInstance();
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return new ErrorCommand();
        }
    }


    public CMD newCommand(String commandName)  {
        Class<? extends Command> clz =this.commandMap.get(commandName);
        return newCommand(clz);
    }

    public void registerListener(Listener listener){
        listeners.add(listener);
    }

    public Collection<Listener> getListeners() {
        return listeners;
    }


    public ErrorHandler getErrorHandler() {
        return errorHandler;
    }

    public void setErrorHandler(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }


    public UserStore getUserStore() {
        return userStore;
    }

    public void setUserStore(UserStore userStore) {
        this.userStore = userStore;
    }
}

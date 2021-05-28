package cn.ulyer.socket.server;

import cn.hutool.core.lang.Assert;
import cn.ulyer.socket.command.*;
import cn.ulyer.socket.context.LinkContext;
import cn.ulyer.socket.event.listener.Listener;
import cn.ulyer.socket.handler.DefaultErrorHandler;
import cn.ulyer.socket.handler.DefaultMessageHandler;
import cn.ulyer.socket.handler.ErrorHandler;
import cn.ulyer.socket.handler.MessageHandler;
import cn.ulyer.socket.security.DefaultSecurityManager;
import cn.ulyer.socket.security.ServerSecurityManager;
import cn.ulyer.socket.store.LinkStore;
import cn.ulyer.socket.store.MapLinkStore;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Configuration {

    private final  Map<String,Class<? extends Command>> commandMap = new ConcurrentHashMap<>(64);

    private final  Map<String,String> commandExt = new ConcurrentHashMap<>(64);

    private LinkStore linkStore = new MapLinkStore();

    private final Collection<Listener> listeners = new LinkedList<>();

    private ServerSecurityManager serverSecurityManager = new DefaultSecurityManager();

    private CommandResolver commandResolver = new DefaultCommandResolver();

    private MessageHandler messageHandler = new DefaultMessageHandler();

    private ErrorHandler errorHandler = new DefaultErrorHandler();

    private Configuration(){
        commandMap.put("login", LoginCommand.class);
        commandExt.put("login","登录 \n -u 用户名 -p password");
        commandMap.put("logout", LogoutCommand.class);
        commandExt.put("logout","退出登录");
        commandMap.put("send", SendMessageCommand.class);
        commandExt.put("send","发送信息 -r 指定用户 不加默认所有人 当用户下线");
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

    public Command resolverCommand(LinkContext context, String value){
        return commandResolver.resolverCommand(context,value);
    }

    public Command newInstanceCommand(String commandName)  {
        Class<? extends Command> clz =this.commandMap.get(commandName);
        if(clz==null||clz.equals(UnknowCommand.class)){
            return new UnknowCommand();
        }
        try {
          Constructor constructor = clz.getConstructor();
          return (Command) constructor.newInstance();
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return new SystemErrorCommand();
        }
    }

    public void registerListener(Listener listener){
        listeners.add(listener);
    }

    public Collection<Listener> getListeners() {
        return listeners;
    }

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }

    public void setMessageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public ErrorHandler getErrorHandler() {
        return errorHandler;
    }

    public void setErrorHandler(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }
}

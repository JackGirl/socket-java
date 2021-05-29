package cn.ulyer.socket.security;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.ulyer.socket.command.CMD;
import cn.ulyer.socket.command.ErrorCommand;
import cn.ulyer.socket.context.LinkContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;

public class CMDProxy implements InvocationHandler {

    private CMD command ;

    private LinkContext linkContext;

    public CMDProxy(CMD command){
        this.command = command;
    }

    public static CMD proxy(CMD command){
        Class commandClass = command.getClass();
        HashSet  classSet = new HashSet();
        for(; commandClass != null; commandClass = commandClass.getSuperclass()) {
            Class[] classes = commandClass.getInterfaces();
            Class[] var3 = classes;
            int var4 = classes.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Class aClass = var3[var5];
                classSet.add(aClass);
            }
        }
        return (CMD) Proxy.newProxyInstance(CMDProxy.class.getClassLoader(), ((Class[])classSet.toArray(new Class[classSet.size()])),new CMDProxy(command));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        LinkContext context = LinkContext.get();
        ServerSecurityManager serverSecurityManager =context.getConfiguration().getServerSecurityManager();
        if(serverSecurityManager.checkCommand(command)){
                command.execute();
        }
        return null;
    }
}

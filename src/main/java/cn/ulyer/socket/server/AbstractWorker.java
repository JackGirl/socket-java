package cn.ulyer.socket.server;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.ulyer.socket.command.Command;
import cn.ulyer.socket.command.LogoutCommand;
import cn.ulyer.socket.context.LinkContext;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public abstract class AbstractWorker implements Runnable{

    protected LinkContext context;

    public AbstractWorker(LinkContext context){
        this.context = context;
    }

    protected void startWorker(){
        String s;
        while (!context.getLink().isClosed()) {
            try {
                s = context.getLink().readLine();
                if (s != null) {
                    log.info("received one command:" + s);
                    Command command = context.getConfiguration().resolverCommand(context, s);
                    context.getConfiguration().getServerSecurityManager().checkCommand(command);
                    command.execute();
                }
            } catch (IOException e) {
                log.error("socket task error ：{}", ExceptionUtil.stacktraceToString(e));
                new LogoutCommand().setLinkContext(context).execute();
                context.getLink().close();
            }
        }
    }

    @Override
    public void run() {
        LinkContext.setContext(context);
        this.startWorker();
    }
}

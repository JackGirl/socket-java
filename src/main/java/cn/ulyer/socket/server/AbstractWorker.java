package cn.ulyer.socket.server;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.map.MapUtil;
import cn.ulyer.socket.command.CMD;
import cn.ulyer.socket.command.LogoutCommand;
import cn.ulyer.socket.command.ShowCommand;
import cn.ulyer.socket.context.LinkContext;
import cn.ulyer.socket.util.CharUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;

@Slf4j
public abstract class AbstractWorker extends Thread implements Runnable {

    protected LinkContext context;

    public AbstractWorker(LinkContext context,boolean damon) {
        this.context = context;
        this.setDaemon(true);
    }

    protected void startWorker() {
        String s;
        try {
            while (!context.getLink().isClosed()) {
                s = context.getLink().readLine();
                if (s != null) {
                    log.info("received one command:" + s);
                    CMD command = context.getConfiguration().resolverCommand(context, s);
                    command.execute();
                }
            }
        } catch (IOException e) {
            log.error("socket task error ：{}", ExceptionUtil.stacktraceToString(e));
        }
        new LogoutCommand().execute();
        context.getLink().close();

    }

    @Override
    public void run() {
        LinkContext.setContext(context);
        Map<String, String> result = MapUtil.newHashMap();
        result.put("c", null);
        ShowCommand s
                = new ShowCommand();
        s.setArgMap(result).execute();
        this.startWorker();
    }
}

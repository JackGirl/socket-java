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
                    CMD command = context.getConfiguration().resolverCommand(context, CharUtil.encode(s));
                    command.execute();
                }
            } catch (IOException e) {
                log.error("socket task error ：{}", ExceptionUtil.stacktraceToString(e));
                new LogoutCommand().execute();
                context.getLink().close();
            }
        }
    }

    @Override
    public void run() {
        LinkContext.setContext(context);
        Map<String,String> result = MapUtil.newHashMap();
        result.put("c",null);
        ShowCommand s
         = new ShowCommand();
        s.setArgMap(result).execute();
        this.startWorker();
    }
}

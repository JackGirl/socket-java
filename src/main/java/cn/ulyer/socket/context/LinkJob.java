package cn.ulyer.socket.context;

import cn.ulyer.socket.link.Link;
import cn.ulyer.socket.model.User;
import cn.ulyer.socket.server.Configuration;
import cn.ulyer.socket.store.LinkStore;
import cn.ulyer.socket.store.UserStore;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class LinkJob {

   public final static ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);


    public static void start(Configuration configuration,long delaySeconds){
      log.info("clean link job start");
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            log.info("clear job timer======");
            LinkStore store = configuration.getLinkStore();
            UserStore userStore = configuration.getUserStore();
            Collection<? extends Link> links = store.getAllLink();
            User u = new User();
            for (Link link : links) {
                User user = link.getUser().orElse(u);
                if(link.isClosed()){
                    store.remove(user.getUsername());
                    userStore.remove(user.getUsername());
                }
                link = null;
            }

        }, delaySeconds, delaySeconds, TimeUnit.SECONDS);

    }

    public static void end(){
        scheduledExecutorService.shutdown();
    }


}

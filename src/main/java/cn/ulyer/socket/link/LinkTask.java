package cn.ulyer.socket.link;

import cn.hutool.cron.task.Task;
import cn.ulyer.socket.model.User;
import cn.ulyer.socket.server.Configuration;
import cn.ulyer.socket.store.LinkStore;
import cn.ulyer.socket.store.UserStore;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public class LinkTask implements Task {

    public Configuration configuration;

    public LinkTask(Configuration configuration){
        this.configuration = configuration;
    }

    @Override
    public void execute() {
        log.info("link task job running");
        try {
            UserStore store = configuration.getUserStore();
            LinkStore linkStore = configuration.getLinkStore();
            Collection<User> users = store.getAllUser();
            for (User user : users) {
                Link link = linkStore.getLink(user.getUsername());
                if(link==null||link.isClosed()){
                    store.remove(user.getUsername());
                    linkStore.remove(user.getUsername());
                    log.info("removed one user :{}",user.getUsername());
                }
            }
        }catch (Exception e){

        }
    }
}

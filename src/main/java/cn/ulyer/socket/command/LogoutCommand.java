package cn.ulyer.socket.command;

import cn.ulyer.socket.context.LinkContext;
import cn.ulyer.socket.event.LogoutEvent;
import cn.ulyer.socket.model.User;
import lombok.NoArgsConstructor;

import java.io.IOException;

@NoArgsConstructor
public class LogoutCommand  extends Command{

    @Override
    public void execute()  {
        LinkContext linkContext = LinkContext.get();
        User user = linkContext.getUser();
        linkContext.getLink().writeToClient("您已退出");
        linkContext.getServer().publishEvent(new LogoutEvent(user));
    }

    @Override
    public String getName() {
        return "logout";
    }



}

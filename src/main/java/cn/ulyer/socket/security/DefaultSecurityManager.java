package cn.ulyer.socket.security;

import cn.ulyer.socket.command.*;
import cn.ulyer.socket.context.LinkContext;
import cn.ulyer.socket.link.Link;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultSecurityManager implements ServerSecurityManager{


    @Override
    public boolean checkLink(Link link) {
        return true;
    }

    @Override
    public boolean checkCommand(CMD command) {
        if(command instanceof LoginCommand ||
        command instanceof UnknowCommand ||
        command instanceof ShowCommand) {
            return true;
        }
        LinkContext context =  LinkContext.get();
        if(context.getUser()==null){
            log.info("systemSecurityManager===========");
            ErrorCommand errorCommand =  new ErrorCommand("未登录");
            errorCommand.execute();
            return false;
        }
        return true;
    }
}

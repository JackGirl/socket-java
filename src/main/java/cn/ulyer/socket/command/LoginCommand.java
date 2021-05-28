package cn.ulyer.socket.command;

import cn.ulyer.socket.constants.MessageConst;
import cn.ulyer.socket.db.GlobalDb;
import cn.ulyer.socket.db.UserMaper;
import cn.ulyer.socket.link.Link;
import cn.ulyer.socket.model.User;
import cn.ulyer.socket.store.LinkStore;
import lombok.NoArgsConstructor;


/**
 * tag  -u -p
 */
@NoArgsConstructor
public class LoginCommand  extends Command{




    @Override
    public void execute() {
        String username = argMap.get("u");
        String password = argMap.get("p");
        LinkStore store = linkContext.getConfiguration().getLinkStore();
        Link alreadyLink = store.getLink(username);
        if(alreadyLink!=null){
            linkContext.getLink().writeToClient(MessageConst.SYSTEM_MESSAGE_PREFIX+"该用户已登录");
            return;
        }
        UserMaper userMaper = GlobalDb.getMapper(UserMaper.class);
        User user = userMaper.findUserByUserName(username);
        if(user==null||!user.getPassword().equals(password)){
            linkContext.getLink().writeToClient(MessageConst.SYSTEM_MESSAGE_PREFIX+"用户名或密码错误");
            return;
        }
        linkContext.getLink().setUser(user);
        linkContext.getConfiguration().getLinkStore().put(username,linkContext.getLink());
        linkContext.getLink().writeToClient(MessageConst.SYSTEM_MESSAGE_PREFIX+"成功登录");
    }


}

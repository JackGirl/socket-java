package cn.ulyer.socket.server.command;

import cn.ulyer.socket.model.User;
import cn.ulyer.socket.server.constants.MessageConst;
import cn.ulyer.socket.server.db.GlobalDb;
import cn.ulyer.socket.server.db.UserMaper;
import cn.ulyer.socket.server.link.Link;
import cn.ulyer.socket.server.store.LinkStore;
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
        LinkStore store = server.getConfiguration().getLinkStore();
        Link alreadyLink = store.getLink(username);
        if(alreadyLink!=null){
            clientLink.writeToClient(MessageConst.SYSTEM_MESSAGE_PREFIX+"该用户已登录");
            return;
        }
        UserMaper userMaper = GlobalDb.getMapper(UserMaper.class);
        User user = userMaper.findUserByUserName(username);
        if(user==null||!user.getPassword().equals(password)){
            clientLink.writeToClient(MessageConst.SYSTEM_MESSAGE_PREFIX+"用户名或密码错误");
            return;
        }
        clientLink.setUser(user);
        server.getConfiguration().getLinkStore().put(username,clientLink);
        clientLink.writeToClient(MessageConst.SYSTEM_MESSAGE_PREFIX+"成功登录");
    }


}

package cn.ulyer.socket.command;

import cn.ulyer.socket.context.LinkContext;
import cn.ulyer.socket.db.GlobalDb;
import cn.ulyer.socket.db.UserMaper;
import cn.ulyer.socket.enums.MessageType;
import cn.ulyer.socket.event.LoginEvent;
import cn.ulyer.socket.model.User;
import cn.ulyer.socket.store.UserStore;
import cn.ulyer.socket.util.MessageFormatter;
import lombok.NoArgsConstructor;


/**
 * tag  -u -p
 */
@NoArgsConstructor
public class LoginCommand  extends Command{




    @Override
    public void execute()  {
        String username = argMap.get("u");
        String password = argMap.get("p");
        LinkContext linkContext = LinkContext.get();
        UserStore store = linkContext.getConfiguration().getUserStore();
        User alreadyLink = store.getUser(username);
        User currentUser = linkContext.getUser();
        if(store.getUser(currentUser.getUsername())!=null){
            new ErrorCommand("当前已登录").execute();
            return;
        }
        if(alreadyLink!=null){
            new ErrorCommand("该用户已登录").execute();
            return;
        }
        UserMaper userMaper = GlobalDb.getMapper(UserMaper.class);
        User user = userMaper.findUserByUserName(username);
        if(user==null||!user.getPassword().equals(password)){
            new ErrorCommand("用户名或密码错误").execute();
            return;
        }
        linkContext.getLink().writeToClient(MessageFormatter.formatterMessage(MessageType.SYSTEM_MESSAGE.getPrefix(),"成功登录"));
        LinkContext.get().getServer().publishEvent(new LoginEvent(user));
    }

    @Override
    public String getName() {
        return "login";
    }


}

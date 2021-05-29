package cn.ulyer.socket.command;

import cn.hutool.core.util.StrUtil;
import cn.ulyer.socket.context.LinkContext;
import cn.ulyer.socket.enums.MessageType;
import cn.ulyer.socket.link.Link;
import cn.ulyer.socket.model.User;
import cn.ulyer.socket.store.LinkStore;
import cn.ulyer.socket.util.MessageFormatter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

public class SendMessageCommand extends Command {


    @Override
    public void execute()  {
        LinkContext linkContext = LinkContext.get();
        User user = linkContext.getUser();
        boolean privateMessage = argMap.containsKey("r");
        LinkStore store = linkContext.getConfiguration().getLinkStore();
        String message = argMap.get("v");
        if(message==null){
            linkContext.getLink()
                    .writeToClient(MessageFormatter.formatterMessage(MessageType.SYSTEM_MESSAGE.getPrefix() ,"消息不能为空哦"));
            return;
        }
        if (privateMessage) {
            String toUserName = argMap.get("u");
            if (StrUtil.isBlank(toUserName)) {
                linkContext.getLink().writeToClient(MessageFormatter.formatterMessage(MessageType.SYSTEM_MESSAGE.getPrefix(), "需要输入用户账号"));
                return;
            }
            Link toLink = store.getLink(toUserName);
            if (toLink == null) {
                linkContext.getLink().writeToClient(MessageFormatter.formatterMessage(MessageType.SYSTEM_MESSAGE.getPrefix(), "用户不在线或者用户不存在"));
                return;
            }
            toLink.writeToClient(MessageFormatter.formatterNickMessage(MessageType.PRIVATE_MESSAGE.getPrefix(),message,user.getName()+"__你"));
            User toUser = toLink.getUser().get();
            linkContext.getLink().writeToClient(MessageFormatter.formatterNickMessage(MessageType.PRIVATE_MESSAGE.getPrefix(),message,"你__"+toUser.getName()));
            return;
        }

        Collection<? extends Link> links = store.getAllLink();
        Iterator<? extends Link> linkIterator = links.iterator();
        while (linkIterator.hasNext()) {
            Link link = linkIterator.next();
            if(link.getUser().equals(linkContext.getLink().getUser())){
                linkContext.getLink().writeToClient(MessageFormatter.formatterNickMessage(MessageType.PUBLIC_MESSAGE.getPrefix(),message,"你"));
            }else{
                link.writeToClient(MessageFormatter.formatterNickMessage(MessageType.PUBLIC_MESSAGE.getPrefix(),message,user.getName()));
            }
        }
    }

    @Override
    public String getName() {
        return "send";
    }

}

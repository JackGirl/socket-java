package cn.ulyer.socket.server.command;

import cn.hutool.core.util.StrUtil;
import cn.ulyer.socket.server.constants.MessageConst;
import cn.ulyer.socket.server.link.Link;
import cn.ulyer.socket.server.store.LinkStore;

import java.util.Collection;
import java.util.Iterator;

public class SendMessageCommand extends Command {


    @Override
    public void execute() {
        boolean privateMessage = argMap.containsKey("pr");
        LinkStore store = server.getConfiguration().getLinkStore();
        String message = argMap.get("v");
        if (privateMessage) {
            String toUser = argMap.get("u");
            if (StrUtil.isBlank(toUser)) {
                clientLink.writeToClient(MessageConst.SYSTEM_MESSAGE_PREFIX + "需要输入用户账号");
                return;
            }
            Link link = store.getLink(toUser);
            if (link == null) {
                clientLink.writeToClient(MessageConst.SYSTEM_MESSAGE_PREFIX + "用户不在线或者用户不存在");
                return;
            }
            link.writeToClient(MessageConst.PRIVATE_MESSAGE_PREFIX+clientLink.getUser().get().getName()+"对你说："+message);
            clientLink.writeToClient(MessageConst.PRIVATE_MESSAGE_PREFIX+"你对"+toUser+"说："+message);
            return;
        }

        Collection<? extends Link> links = store.getAllLink();
        Iterator<? extends Link> linkIterator = links.iterator();
        while (linkIterator.hasNext()) {
            Link link = linkIterator.next();
            if(link.getUser().equals(clientLink.getUser())){
                link.writeToClient(MessageConst.PUBLIC_MESSAGE_PRFIX +"你说:"+message);
            }else{
                link.writeToClient(MessageConst.PUBLIC_MESSAGE_PRFIX+clientLink.getUser().get().getName()+"说:"+message);
            }
        }
    }

}

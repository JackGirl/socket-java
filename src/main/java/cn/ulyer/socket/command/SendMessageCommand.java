package cn.ulyer.socket.command;

import cn.hutool.core.util.StrUtil;
import cn.ulyer.socket.constants.MessageConst;
import cn.ulyer.socket.link.Link;
import cn.ulyer.socket.store.LinkStore;

import java.util.Collection;
import java.util.Iterator;

public class SendMessageCommand extends Command {


    @Override
    public void execute() {
        boolean privateMessage = argMap.containsKey("pr");
        LinkStore store = linkContext.getConfiguration().getLinkStore();
        String message = argMap.get("v");
        if(message==null){
            linkContext.getLink().writeToClient(MessageConst.SYSTEM_MESSAGE_PREFIX+"消息不能为空哦");
            return;
        }
        if (privateMessage) {
            String toUser = argMap.get("u");
            if (StrUtil.isBlank(toUser)) {
                linkContext.getLink().writeToClient(MessageConst.SYSTEM_MESSAGE_PREFIX + "需要输入用户账号");
                return;
            }
            Link link = store.getLink(toUser);
            if (link == null) {
                linkContext.getLink().writeToClient(MessageConst.SYSTEM_MESSAGE_PREFIX + "用户不在线或者用户不存在");
                return;
            }
            link.writeToClient(MessageConst.PRIVATE_MESSAGE_PREFIX+linkContext.getLink().getUser().get().getName()+"对你说："+message);
            linkContext.getLink().writeToClient(MessageConst.PRIVATE_MESSAGE_PREFIX+"你对"+toUser+"说："+message);
            return;
        }

        Collection<? extends Link> links = store.getAllLink();
        Iterator<? extends Link> linkIterator = links.iterator();
        while (linkIterator.hasNext()) {
            Link link = linkIterator.next();
            if(link.getUser().equals(linkContext.getLink().getUser())){
                link.writeToClient(MessageConst.PUBLIC_MESSAGE_PRFIX +"你说:"+message);
            }else{
                link.writeToClient(MessageConst.PUBLIC_MESSAGE_PRFIX+linkContext.getLink().getUser().get().getName()+"说:"+message);
            }
        }
    }

}

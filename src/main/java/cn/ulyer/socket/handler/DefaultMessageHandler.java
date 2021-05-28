package cn.ulyer.socket.handler;

import cn.ulyer.socket.context.LinkContext;
import cn.ulyer.socket.model.Message;

/**
 * 默认消息处理器 定义如何发送
 */
public class DefaultMessageHandler  implements MessageHandler{

    @Override
    public boolean send(Message message) {
        LinkContext context = LinkContext.get();
        if(context==null){
            return false;
        }
        return false;
    }

}

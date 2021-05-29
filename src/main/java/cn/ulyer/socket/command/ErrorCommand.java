package cn.ulyer.socket.command;

import cn.ulyer.socket.context.LinkContext;
import cn.ulyer.socket.enums.MessageType;
import cn.ulyer.socket.util.MessageFormatter;

import java.io.IOException;

public class ErrorCommand extends Command{

    String msg;

    public ErrorCommand(){}

    public ErrorCommand(String msg){
        this.msg = msg;
    }

    @Override
    public void execute()  {
        LinkContext context = LinkContext.get();
        context.getLink().writeToClient(MessageFormatter.formatterMessage(MessageType.SYSTEM_MESSAGE.getPrefix(),msg));
    }

    @Override
    public String getName() {
        return "error";
    }
}

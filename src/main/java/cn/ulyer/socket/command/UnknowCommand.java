package cn.ulyer.socket.command;

import cn.ulyer.socket.context.LinkContext;
import lombok.NoArgsConstructor;

import java.io.IOException;

@NoArgsConstructor
public class UnknowCommand extends Command{

    @Override
    public void execute() {
        LinkContext.get().getLink().writeToClient("unknowcommand");
    }

    @Override
    public String getName() {
        return "unknow";
    }
}

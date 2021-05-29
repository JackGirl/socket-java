package cn.ulyer.socket.command;

import cn.ulyer.socket.context.LinkContext;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UnknowCommand extends Command{

    @Override
    public void execute() {
        LinkContext.get().getLink().writeToClient("未知命令，请重新输入");
    }

    @Override
    public String getName() {
        return "unknow";
    }
}

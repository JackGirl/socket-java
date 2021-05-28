package cn.ulyer.socket.command;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UnknowCommand extends Command{

    @Override
    public void execute() {
        linkContext.getLink().writeToClient("未知命令，请重新输入");
    }
}

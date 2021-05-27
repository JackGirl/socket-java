package cn.ulyer.socket.server.command;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UnknowCommand extends Command{

    @Override
    public void execute() {
        clientLink.writeToClient("未知命令，请重新输入");
    }
}

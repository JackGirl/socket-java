package cn.ulyer.socket.server.command;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LogoutCommand  extends Command{



    @Override
    public void execute() {
        clientLink.writeToClient("您已退出");
    }

}

package cn.ulyer.socket.command;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LogoutCommand  extends Command{

    @Override
    public void execute() {
        linkContext.getLink().writeToClient("您已退出");
    }

}

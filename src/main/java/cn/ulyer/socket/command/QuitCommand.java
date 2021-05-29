package cn.ulyer.socket.command;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class QuitCommand  extends Command{

    @Override
    public void execute() {

    }

    @Override
    public String getName() {
        return "quit";
    }


}

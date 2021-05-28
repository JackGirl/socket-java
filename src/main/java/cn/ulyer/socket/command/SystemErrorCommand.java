package cn.ulyer.socket.command;

public class SystemErrorCommand extends Command{

    String msg;

    public SystemErrorCommand(){}

    public SystemErrorCommand(String msg){
        this.msg = msg;
    }

    @Override
    public void execute() {

    }
}

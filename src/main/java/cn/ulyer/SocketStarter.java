package cn.ulyer;

import cn.ulyer.socket.server.BlockServer;
import cn.ulyer.socket.server.Configuration;
import cn.ulyer.socket.server.Server;

import java.io.IOException;

public class SocketStarter {


    public static void main(String[] args) throws IOException {
        Server blockServer = new BlockServer(8080,"blockServer");
        blockServer.setConfiguration(Configuration.newConfig());
        blockServer.start();
    }

}

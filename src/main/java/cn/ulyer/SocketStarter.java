package cn.ulyer;

import cn.ulyer.socket.context.LinkJob;
import cn.ulyer.socket.server.BlockServer;
import cn.ulyer.socket.server.Configuration;
import cn.ulyer.socket.server.Server;

import java.io.IOException;

public class SocketStarter {


    public static void main(String[] args) throws IOException {
        Server blockServer = new BlockServer(Integer.valueOf(System.getProperty("server.port")),"blockServer");
        Configuration configuration = Configuration.newConfig();
        blockServer.setConfiguration(configuration);
        LinkJob.start(configuration,15L);
        blockServer.start();
    }

}

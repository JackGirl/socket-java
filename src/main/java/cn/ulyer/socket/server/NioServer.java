package cn.ulyer.socket.server;

import cn.ulyer.socket.AbstractServer;
import cn.ulyer.socket.server.link.Link;
import cn.ulyer.socket.server.link.NioLink;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class NioServer extends AbstractServer {

    ServerSocketChannel serverSocketChannel;

    ExecutorService executor = new ThreadPoolExecutor(20, 128, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());


    public NioServer(int port, String serverId) {
        super(port, serverId);
    }

    @Override
    public void start() throws IOException {
        this.serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(this.port));
        while (true) {
            SocketChannel channel = serverSocketChannel.accept();
            NioLink nioLink = new NioLink(channel);
        }
    }


    class NioWorker implements Runnable {

        private Server server;

        private NioLink nioLink;


        public NioWorker(Server server, NioLink link) {
            this.server = server;
            this.nioLink = link;
        }

        @Override
        public void run() {
            try {
                while (!nioLink.isClosed()) {
                    String s = nioLink.readLine();
                    if(s!=null){

                    }
                }
            } catch (Exception e) {

            }
        }


    }


}

package cn.ulyer.socket.server;


import cn.hutool.core.exceptions.ExceptionUtil;
import cn.ulyer.socket.AbstractServer;
import cn.ulyer.socket.server.command.Command;
import cn.ulyer.socket.server.command.LogoutCommand;
import cn.ulyer.socket.server.link.SocketLink;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Setter
@Getter
@Slf4j
public class BlockServer extends AbstractServer {

    ServerSocket serverSocket;

    ExecutorService executor = new ThreadPoolExecutor(20, 128, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

    public BlockServer(int port, String serverId) {
        super(port, serverId);
    }


    @Override
    public void start() throws IOException {
            serverSocket = new ServerSocket(this.port);

            log.info("server start port is " + port);
            while (true) {
                SocketLink socketLink = null;
                try {
                    Socket socket = serverSocket.accept();
                    InputStream clientInput = socket.getInputStream();
                    socketLink = new SocketLink(socket);
                    this.configuration.getServerSecurityManager().checkLink(socketLink);

                    log.info("has one connected :" + socket.getInetAddress().getHostName());
                } catch (Exception e) {

                    log.error("socket link error:{}", ExceptionUtil.stacktraceToString(e));
                }
                executor.execute(new DefaultWorkRunner(this, socketLink));
            }
    }


    static class DefaultWorkRunner implements Runnable {

        private Server server;

        private SocketLink socketLink;


        public DefaultWorkRunner(Server server, SocketLink socketLink) throws IOException {
            this.server = server;
            this.socketLink = socketLink;
        }

        @Override
        public void run() {
            String s;
            while (!socketLink.isClosed()) {
                try {
                    s = socketLink.readLine();
                    if (s != null) {
                        log.info("received one message:" + s);
                        Command command = server.getConfiguration().resolverCommand(server, socketLink, s);
                        server.getConfiguration().getServerSecurityManager().checkCommand(command);
                        command.execute();
                    }
                } catch (IOException e) {
                    log.error("socket task error ：{}", ExceptionUtil.stacktraceToString(e));
                    socketLink.close();
                    new LogoutCommand().setClientLink(socketLink).setServer(this.server).execute();
                }
            }

        }
    }

}

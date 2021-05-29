package cn.ulyer.socket.server;


import cn.hutool.core.exceptions.ExceptionUtil;
import cn.ulyer.socket.context.LinkContext;
import cn.ulyer.socket.link.SocketLink;
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

/**
 * 阻塞服务器
 */
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
                    if(this.configuration.getServerSecurityManager().checkLink(socketLink)){
                        executor.execute(new AbstractWorker(new LinkContext(this,this.configuration,socketLink)) {
                            @Override
                            protected void startWorker() {
                                super.startWorker();
                            }
                        });
                    }else{
                        socketLink.close();
                    }
                    log.info("has one connected :" + socket.getInetAddress().getHostName());
                } catch (Exception e) {

                    log.error("socket link error:{}", ExceptionUtil.stacktraceToString(e));
                }
            }
    }


}

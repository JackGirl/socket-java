package cn.ulyer.socket.link;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.ulyer.socket.model.User;
import cn.ulyer.socket.util.CharUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.util.Optional;

@Slf4j
public class SocketLink implements Link{


    private User user;

    Socket socket;

    PrintWriter writer ;

    InputStream stream ;

    BufferedReader  reader;


    public SocketLink(Socket socket) throws IOException {
        this.writer =  new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        stream = socket.getInputStream();
        this.reader = new BufferedReader(new InputStreamReader(stream,CharUtil.ENCODE));
        this.socket = socket;
    }

    @Override
    public void writeToClient(String msg) {
        writer.print(msg+CharUtil.enter());
        writer.flush();
    }

    @Override
    public String readLine() throws IOException {
           return  reader.readLine();
    }


    @Override
    public Optional<User> getUser() {
        return Optional.ofNullable(user);
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }


    @Override
    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            log.error("socket close error:{}", ExceptionUtil.stacktraceToString(e));
        }
    }

    @Override
    public boolean isClosed() {
        try {
            socket.sendUrgentData(0xFF);
            return false;
        } catch (IOException e) {
            return true;
        }
    }
}

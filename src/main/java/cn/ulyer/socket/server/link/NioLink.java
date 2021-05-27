package cn.ulyer.socket.server.link;

import cn.ulyer.socket.model.User;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
public class NioLink implements Link{

    private User user;

    private SocketChannel channel;

    public NioLink(SocketChannel channel){
        this.channel = channel;
    }

    @Override
    public void writeToClient(String msg) {
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8));
        try {
            buffer.flip();
            channel.write(buffer);
        } catch (IOException e) {
            log.error("发送消息失败",e);
        }
    }

    @Override
    public String readLine() throws IOException {
        ByteBuffer buffer =  ByteBuffer.allocate(256);
         channel.read(buffer);
         return new String(buffer.array(),StandardCharsets.UTF_8);
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
    public void close()  {
        try {
            channel.close();
        } catch (IOException e) {
            log.error("close nio channel error");
        }
    }

    @Override
    public boolean isClosed() {
        return channel.isOpen();
    }
}

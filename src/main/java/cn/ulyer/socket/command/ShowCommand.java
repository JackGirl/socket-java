package cn.ulyer.socket.command;

import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.StrUtil;
import cn.ulyer.socket.context.LinkContext;
import cn.ulyer.socket.enums.MessageType;
import cn.ulyer.socket.model.User;
import cn.ulyer.socket.store.UserStore;
import cn.ulyer.socket.util.MessageFormatter;
import lombok.NoArgsConstructor;

import java.util.Collection;

@NoArgsConstructor
public class ShowCommand extends Command {


    /**
     * -c 查看所有命令
     * -u 0-25查看在线用户
     */
    @Override
    public void execute() {
        LinkContext linkContext = LinkContext.get();
        boolean showCommand = argMap.containsKey("c");
        String uCommand = argMap.get("u");
        if (showCommand) {
            StrBuilder builder = new StrBuilder();
            builder.append(MessageType.SYSTEM_MESSAGE.getPrefix()).append("\r");
            linkContext.getConfiguration().commandExt.forEach((k, v) -> {
                builder.append(k).append(" ").append(v).append("\r");
            });
            linkContext.getLink().writeToClient(builder.toString());
            return;
        }
        if (StrUtil.isBlank(uCommand)) {
            new ErrorCommand("need arg start-end").execute();
            return;
        }
        String[] list = uCommand.split("-");
        if (list.length < 2) {
            new ErrorCommand("参数错误").execute();
        }
        UserStore userStore = linkContext.getConfiguration().getUserStore();
        Collection<User> users = userStore.range(Integer.parseInt(list[0]), Integer.parseInt(list[1]));
        StringBuilder builder = new StringBuilder();
        builder.append("\r");
        builder.append("name").append("\t").append("username\r");
        for (User user : users) {
            builder.append(user.getName()).append("\t").append(user.getUsername()).append("\r");
        }
        linkContext.getLink().writeToClient(MessageFormatter.formatterMessage(MessageType.SYSTEM_MESSAGE.getPrefix(), builder.toString()));
    }

    @Override
    public String getName() {
        return "show";
    }
}

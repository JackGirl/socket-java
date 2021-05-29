package cn.ulyer.socket.command;

import java.io.IOException;
import java.util.Map;

public interface CMD {

    void execute();

    /**
     * 命令名  login show..
     * @return
     */
    String getName();

    /**
     * 当前命令的字符
     * @return
     */
    String getValue();

    CMD setValue(String value);

    Map<String,String> getArgMap();

    CMD setArgMap(Map<String,String > argMap);
}

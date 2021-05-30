package cn.ulyer.socket.command;


import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Map;


/**
 * -v  输入的值 所有命令都有
 */
@Accessors(chain = true)
@NoArgsConstructor
public abstract class Command implements CMD{


    protected Map<String,String> argMap;

    String value;

    @Override
    public void execute()  {}


    @Override
    public abstract String getName();

    @Override
    public Map<String, String> getArgMap() {
        return this.argMap;
    }

    @Override
    public CMD setValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public CMD setArgMap(Map<String, String> argMap) {
        this.argMap = argMap;
        return this;
    }

    @Override
    public String getValue() {
        return value;
    }
}

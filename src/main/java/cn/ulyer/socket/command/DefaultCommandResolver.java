package cn.ulyer.socket.command;

import cn.hutool.core.util.StrUtil;
import cn.ulyer.socket.context.LinkContext;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * 根据输入的字符解析命令
 */
public class DefaultCommandResolver implements CommandResolver{


    public static void setArgValue(String [] sr,int index,Map<String,String> argMap){
        if (index<sr.length&&sr.length>0){
            String str =  sr[index];
            if(StrUtil.startWith(str,'-')){
                int valueIndex = getTagValue(sr,index);
                String key = str.replace("-","");
                if(valueIndex==-1){
                    argMap.put(key,null);
                    setArgValue(sr,++index,argMap);
                    return;
                }
                argMap.put(key,sr[valueIndex]);
                setArgValue(sr,++valueIndex,argMap);
                return;
            }
            setArgValue(sr,++index,argMap);
        }
    }


    public static int getTagValue(String [] sr,int index){
        int valueIndex = index+1;
        if(valueIndex>=sr.length){
            return -1;
        }
        String value = sr[valueIndex];
        if(StrUtil.startWith(value,'-')){
            return -1;
        }else{
            return valueIndex;
        }
    }

    @Override
    public CMD resolverCommand(LinkContext linkContext, String value) {
        if(StrUtil.isBlank(value)){
            return new UnknowCommand();
        }
        String [] sr = value.split("\\s+");
        String mainCommand = sr[0];
        CMD command = null;
        command = linkContext.getConfiguration().newCommand(mainCommand);
        Map<String,String> argMap = new LinkedHashMap<>();
        command.setValue(value).setArgMap(argMap) ;
        if(sr.length==1){
            return command;
        }
        setArgValue(sr,1,argMap);
        return command;
    }
}

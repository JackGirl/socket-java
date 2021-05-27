package cn.ulyer;


import cn.hutool.core.util.StrUtil;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class CommanTest {


    @Test
    public void resolverArg(){
        String command = "send     -r sada -d assa da";
        String [] sr = command.split("\\s+");
        String manCommand = sr[0];
        Map<String,String> argMap = new LinkedHashMap<>();
        setArgValue(sr,1,argMap);
        argMap.entrySet().stream().forEach(e->{
            System.out.print(e.getKey() + " ");
            System.out.print(e.getValue());
            System.out.println();
        });
    }

    public static void setArgValue(String [] sr,int index,Map<String,String> argMap){
        if (index<sr.length){
            String str =  sr[index];
            if(StrUtil.startWith(str,'-')){
                int valueIndex = getTagValue(sr,index);
                String key = str.replace("-","");
                if(valueIndex==-1){
                    argMap.put(key,null);
                    setArgValue(sr,index++,argMap);
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
        if(valueIndex>sr.length){
            return -1;
        }
        String value = sr[valueIndex];
        if(StrUtil.startWith(value,'-')){
            return -1;
        }else{
            return valueIndex;
        }
    }


}

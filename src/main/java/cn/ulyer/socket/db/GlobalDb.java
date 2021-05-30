package cn.ulyer.socket.db;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.beetl.sql.core.*;
import org.beetl.sql.ext.DebugInterceptor;

import java.util.Properties;

@Slf4j
public class GlobalDb {

    private static SQLManager sqlManager;

    static {
        try {
            Properties properties = new Properties();
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("jdbc.properties"));

            DruidDataSource dataSource = new DruidDataSource();
            dataSource.setDriverClassName(properties.getProperty("jdbc.driverClassName"));
            dataSource.setUsername(properties.getProperty("jdbc.username"));
            dataSource.setUrl(properties.getProperty("jdbc.url"));
            dataSource.setPassword(properties.getProperty("jdbc.password"));

            ConnectionSource source = ConnectionSourceHelper.getSingle(dataSource);
            sqlManager = new SQLManagerBuilder(source).build();
            sqlManager.setInters(new Interceptor[]{new DebugInterceptor()});


        } catch (Exception e) {
            log.error("db config init error:{}", ExceptionUtil.stacktraceToString(e));
        }
    }


    public static  <T> T getMapper(Class<T> clz){
        return sqlManager.getMapper(clz);
    }


}

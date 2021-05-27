package cn.ulyer.socket.server.db;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.ulyer.orm.config.OrmConfiguration;
import cn.ulyer.orm.config.ResourceConfigurationLoader;
import cn.ulyer.orm.factory.DefaultOrmFactory;
import cn.ulyer.orm.factory.OrmFactory;
import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

@Slf4j
public class GlobalDb {

    public  static OrmConfiguration configuration ;
    private static OrmFactory ormFactory;

    static {
        try {
            configuration = ResourceConfigurationLoader.loadConfiguration("orm.yml");
            ormFactory = new DefaultOrmFactory(configuration);

            Properties properties = new Properties();
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("jdbc.properties"));

            DruidDataSource dataSource = new DruidDataSource();
            dataSource.setDriverClassName(properties.getProperty("jdbc.driverClassName"));
            dataSource.setUsername(properties.getProperty("jdbc.username"));
            dataSource.setUrl(properties.getProperty("jdbc.url"));
            dataSource.setPassword(properties.getProperty("jdbc.password"));

            ormFactory.setDataSource(dataSource);
        } catch (Exception e) {
            log.error("db config init error:{}", ExceptionUtil.stacktraceToString(e));
        }
    }


    public static  <T> T getMapper(Class<T> clz){
        return ormFactory.getMapper(clz);
    }


}

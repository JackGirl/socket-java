package cn.ulyer.socket.db;


import cn.ulyer.socket.model.User;
import org.beetl.sql.mapper.BaseMapper;
import org.beetl.sql.mapper.annotation.Select;
import org.beetl.sql.mapper.annotation.Sql;

public interface UserMaper extends BaseMapper<User> {

    @Sql("select * from user where username = ?")
    @Select
    User findUserByUserName( String username);

}

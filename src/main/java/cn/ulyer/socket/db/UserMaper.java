package cn.ulyer.socket.db;

import cn.ulyer.orm.annotation.Param;
import cn.ulyer.orm.annotation.Select;
import cn.ulyer.socket.model.User;

public interface UserMaper {

    @Select(sql = "select * from user where username = #{username}")
    User findUserByUserName(@Param("username") String username);

}

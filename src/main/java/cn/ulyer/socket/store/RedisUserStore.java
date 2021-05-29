package cn.ulyer.socket.store;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.nosql.redis.RedisDS;
import cn.ulyer.socket.model.User;
import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RedisUserStore implements UserStore{

    RedisDS redisDS = new RedisDS();

    private final static String KEY = "MAP_USER";


    private Jedis connect(){
        Jedis jedis =redisDS.getJedis();
        return jedis;
    }


    @Override
    public User getUser(String username) {
        String value = connect().hget(KEY,username);
        if(StrUtil.isBlank(value)){
            return null;
        }
        return JSON.parseObject(value,User.class);
    }

    @Override
    public Collection<User> getAllUser() {
        Map<String,String> mapUser =  connect().hgetAll(KEY);
        if(CollectionUtil.isEmpty(mapUser)){
            return CollectionUtil.newArrayList();
        }
        List<User> user = mapUser.entrySet().stream().map(e->JSON.parseObject(e.getValue(),User.class)).collect(Collectors.toList());
        return user;
    }

    @Override
    public Collection<User> range(int page, int size) {
        List<User> users = (List<User>) getAllUser();
        List<User> us = new LinkedList<>();
        IntStream.range(0,size>users.size()?users.size():size).forEach(i->us.add(users.get(i)));
        return us;
    }

    @Override
    public void store(User user) {
        connect().hset(KEY,user.getUsername(),JSON.toJSONString(user));
    }

    @Override
    public void remove(String username) {
        connect().hdel(KEY,username);
    }

    public static void main(String[] args) {
       IntStream.range(0,0).forEach(System.out::println);
    }
}

package cn.ulyer.socket.store;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.nosql.redis.RedisDS;
import cn.ulyer.socket.model.User;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class RedisUserStore implements UserStore {

    RedisDS redisDS = new RedisDS();

    private final static String KEY = "MAP_USER";


    private Jedis connect() {
        log.info("get redis connect");
        Jedis jedis = redisDS.getJedis();
        return jedis;
    }


    @Override
    public User getUser(String username) {
        Jedis jedis = connect();
        String value = jedis.hget(KEY, username);
        close(jedis);
        if (StrUtil.isBlank(value)) {
            return null;
        }
        return JSON.parseObject(value, User.class);
    }

    @Override
    public Collection<User> getAllUser() {
        Jedis jedis = connect();
        Map<String, String> mapUser = jedis.hgetAll(KEY);
        close(jedis);
        if (CollectionUtil.isEmpty(mapUser)) {
            return CollectionUtil.newArrayList();
        }
        List list = new ArrayList(16);
        mapUser.forEach((k, v) -> list.add(JSON.parseObject(v, User.class)));
        return list;
    }

    @Override
    public Collection<User> range(int page, int size) {
        List<User> users = (List<User>) getAllUser();
        List<User> us = new LinkedList<>();
        IntStream.range(page, size > users.size() ? users.size() : size).forEach(i -> us.add(users.get(i)));
        return us;
    }

    @Override
    public void store(User user) {
        Jedis jedis = connect();
        jedis.hset(KEY, user.getUsername(), JSON.toJSONString(user));
        close(jedis);
    }

    @Override
    public void remove(String username) {
        Jedis jedis = connect();
        jedis.hdel(KEY, username);
        close(jedis);
    }


    private void close(Jedis jedis){
        log.info("close connect redis");
        jedis.close();
    }

}

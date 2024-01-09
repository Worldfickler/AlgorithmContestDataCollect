package org.algotithmcontestdatacollect.displaybackend.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author yjz
 * @date 2023/8/15$ 15:21$
 * @description:
 */
@Component
public class RedisUtils {
    @Autowired
    RedisConnectionFactory redisConnectionFactory;
    @Value("${OkCodeforcesKey}")
    String OkCodeforcesKey;
    public void storeMapInRedis(long key, List<String> values) {
        try (RedisConnection connection = redisConnectionFactory.getConnection()) {
            // Convert List<String> to byte[]
            byte[] valuesBytes = String.join(",", values).getBytes();
            // Convert Long key to byte[]
            byte[] keyBytes = Long.toString(key).getBytes();
            // Use HSET to store the values in the Hashes
            connection.hSet(OkCodeforcesKey.getBytes(), keyBytes, valuesBytes);
        }
    }


    public List<String> getValuesFromRedis(long key) {
        try (RedisConnection connection = redisConnectionFactory.getConnection()) {
            // Use HGET to retrieve the values from the Hashes
            byte[] valuesBytes = connection.hGet(OkCodeforcesKey.getBytes(), Long.toString(key).getBytes());

            // Convert the byte array back to List<String>
            if (valuesBytes != null) {
                String[] valuesArray = new String(valuesBytes).split(",");
                return Arrays.asList(valuesArray);
            }
        }
        return Collections.emptyList();
    }

}

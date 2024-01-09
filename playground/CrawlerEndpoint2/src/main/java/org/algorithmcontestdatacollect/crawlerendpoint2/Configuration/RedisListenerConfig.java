package org.algorithmcontestdatacollect.crawlerendpoint2.Configuration;

import org.algorithmcontestdatacollect.crawlerendpoint2.RedisListeners.NormalListener;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.redis.connection.stream.Consumer;
@Configuration
public class RedisListenerConfig {
    @Bean
    public SelfDefineRedisListenLifeCycle redisStreamListenThread(RedisConnectionFactory redisConnectionFactory,Environment environment,NormalListener normalListener) {
        SelfDefineRedisListenLifeCycle t = new SelfDefineRedisListenLifeCycle(redisConnectionFactory,environment,normalListener);
        t.start();
        return t;
    }

}

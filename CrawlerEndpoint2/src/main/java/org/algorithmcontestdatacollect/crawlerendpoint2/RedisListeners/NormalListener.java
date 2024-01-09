package org.algorithmcontestdatacollect.crawlerendpoint2.RedisListeners;

import org.algorithmcontestdatacollect.crawlerendpoint2.Handlers.IHandler;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Spider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.Executor;

@Component
public class NormalListener implements StreamListener<String, MapRecord<String, String, String>> {
    @Autowired
    private Environment environment;

    private static final Logger LOGGER = LoggerFactory.getLogger(NormalListener.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Override
    public void onMessage(MapRecord<String, String, String> message) {
        LOGGER.info(message.getId().toString());
        LOGGER.info(message.getValue().toString());
        try {
            IHandler handler = applicationContext.getBean(message.getValue().get("handler"), IHandler.class);
            Spider spider = applicationContext.getBean(message.getValue().get("spider"), Spider.class);
            handler.handleRequest(spider, message.getValue().get("params"));
            var conn = redisConnectionFactory.getConnection();
            conn.xAck(message.getStream().getBytes(), environment.getProperty("crawlerTask.group"), message.getId());
            conn.xDel(message.getStream().getBytes(), message.getId());
        } catch (BeansException beansException) {
            LOGGER.error(beansException.getMessage());
        }
    }
}

package org.algotithmcontestdatacollect.crawlerreceiver.RedisListeners;


import org.algotithmcontestdatacollect.crawlerreceiver.Handlers.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

@Component
public class ErrorListener implements StreamListener<String, MapRecord<String,String,String>> {
    @Autowired
    private Environment environment;
    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorListener.class);
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Override
    public void onMessage(MapRecord<String, String, String> message) {
        LOGGER.info(message.getId().toString());
        LOGGER.info(message.getStream());
        applicationContext.getBean(message.getValue().get("handler"),AbstractHandler.class).handle(message.getValue().get("errorInfo"));
        var conn = redisConnectionFactory.getConnection();
        conn.xAck(message.getStream().getBytes(),environment.getProperty("errorQueue.group"),message.getId());
        conn.close();
    }
}

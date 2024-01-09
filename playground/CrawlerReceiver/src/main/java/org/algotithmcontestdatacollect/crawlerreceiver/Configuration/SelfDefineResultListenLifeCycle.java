package org.algotithmcontestdatacollect.crawlerreceiver.Configuration;

import org.algotithmcontestdatacollect.crawlerreceiver.RedisListeners.ResultListener;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.Lifecycle;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.*;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class SelfDefineResultListenLifeCycle implements Lifecycle {
    private static final Logger logger = LoggerFactory.getLogger(SelfDefineResultListenLifeCycle.class);
    private final AtomicBoolean running = new AtomicBoolean(false);
    RedisConnectionFactory redisConnectionFactory;
    Consumer consumer;
    StreamOffset<byte[]> streamOffset;
    StreamReadOptions streamReadOptions;
    Thread t;
    ResultListener resultListener;


    SelfDefineResultListenLifeCycle(RedisConnectionFactory redisConnectionFactory, Environment environment, ResultListener resultListener) {
        this.redisConnectionFactory = redisConnectionFactory;
        consumer = Consumer.from(environment.getProperty("resultQueue.group","consumer"),
                environment.getProperty("server.name", RandomStringUtils.random(10)));
        streamOffset =  StreamOffset.create(
                environment.getProperty("resultQueue.stream","ResultQueue").getBytes(), ReadOffset.lastConsumed());
        streamReadOptions = StreamReadOptions.empty().block(Duration.of(1, ChronoUnit.SECONDS));
        this.resultListener = resultListener;
        t = new Thread(()->{
            while (running.get()) {
                try {
                    var conn = redisConnectionFactory.getConnection();
                    var messages = conn.xReadGroup(consumer,streamReadOptions,streamOffset);
                    conn.close();
                    if (messages == null) {
                        logger.info("no parser request receive");
                        continue;
                    }
                    for (var message : messages) {
                        RecordId recordId = message.getId();
                        Map<String,String> map = new HashMap<>();
                        for (var entry : message.getValue().entrySet()) {
                            map.put(new String(entry.getKey()),new String(entry.getValue()));
                        }
                        MapRecord<String,String,String> mapRecord = StreamRecords.newRecord().withId(recordId).in(new String(message.getStream())).ofStrings(map);
                        resultListener.onMessage(mapRecord);
                    }

                }
                catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        });
    }

    @Override
    public void start() {
        running.set(true);
        t.start();
    }

    @Override
    public void stop() {
        running.set(false);
        try {
            t.join();
        } catch (InterruptedException e) {
            if(running.get()) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean isRunning() {
        return running.get();
    }
}

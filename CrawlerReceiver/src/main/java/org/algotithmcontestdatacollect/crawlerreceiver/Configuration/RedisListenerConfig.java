package org.algotithmcontestdatacollect.crawlerreceiver.Configuration;

import org.algotithmcontestdatacollect.crawlerreceiver.RedisListeners.ErrorListener;
import org.algotithmcontestdatacollect.crawlerreceiver.RedisListeners.ResultListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
public class RedisListenerConfig {

    //    @Bean("resultQueueListener")
//    public Subscription resultQueueListener(){
//        var options = StreamMessageListenerContainer
//                .StreamMessageListenerContainerOptions
//                .builder()
//                .pollTimeout(Duration.ofMillis(500))
//                .build();
//        var listenerContainer = StreamMessageListenerContainer.create(redisConnectionFactory,options);
//        var subscription = listenerContainer.receive(
//                Consumer.from(environment.getProperty("resultQueue.group","consumer"),
//                        environment.getProperty("server.name",RandomStringUtils.random(10))),
//                StreamOffset.create(
//                        environment.getProperty("resultQueue.stream","ResultQueue"), ReadOffset.lastConsumed()
//                ),
//                resultListener
//        );
//        listenerContainer.start();
//        return subscription;
//    }
//
//    @Bean("errorQueueListener")
//    public Subscription errorQueueListener(){
//        var options = StreamMessageListenerContainer
//                .StreamMessageListenerContainerOptions
//                .builder()
//                .pollTimeout(Duration.ofMillis(500))
//                .build();
//        var listenerContainer = StreamMessageListenerContainer.create(redisConnectionFactory,options);
//        var subscription = listenerContainer.receive(
//                Consumer.from(environment.getProperty("errorQueue.group","consumer"),
//                        environment.getProperty("server.name",RandomStringUtils.random(10))),
//                StreamOffset.create(
//                        environment.getProperty("errorQueue.stream","ErrorQueue"), ReadOffset.lastConsumed()
//                ),
//                errorListener
//        );
//        listenerContainer.start();
//        return subscription;
//    }
    @Bean
    public SelfDefineResultListenLifeCycle resultStreamListenThread(RedisConnectionFactory redisConnectionFactory, Environment environment, ResultListener resultListener) {
        SelfDefineResultListenLifeCycle t = new SelfDefineResultListenLifeCycle(redisConnectionFactory, environment, resultListener);
        t.start();
        return t;
    }

    @Bean
    public SelfDefineErrorListenLifeCycle errorStreamListenThread(RedisConnectionFactory redisConnectionFactory, Environment environment, ErrorListener errorListener) {
        SelfDefineErrorListenLifeCycle t = new SelfDefineErrorListenLifeCycle(redisConnectionFactory, environment, errorListener);
        t.start();
        return t;
    }

}

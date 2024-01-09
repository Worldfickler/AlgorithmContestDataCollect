package org.algotithmcontestdatacollect.crawlerdispatcher.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamInfo;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Iterator;

@Component
public class RedisInit {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisInit.class);
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Autowired
    private Environment environment;
    @PostConstruct
    public void init(){
        String streamName = environment.getProperty("crawlerTask.stream");
        String groupName = environment.getProperty("crawlerTask.group");
        quickInitRedisStream(streamName,groupName);
        quickInitRedisStream(environment.getProperty("resultQueue.stream"),environment.getProperty("resultQueue.group"));
        quickInitRedisStream(environment.getProperty("errorQueue.stream"),environment.getProperty("errorQueue.group"));
    }
    private void quickInitRedisStream(String streamName,String groupName){
        RedisConnection redisConnection = redisConnectionFactory.getConnection();
        Boolean res = redisConnection.exists(streamName.getBytes());
        if(res != null && res){
            LOGGER.info("队列{}已存在",streamName);
            StreamInfo.XInfoGroups xInfoGroups = redisConnection.xInfoGroups(streamName.getBytes());
            if(xInfoGroups!=null&&!checkGroupExist(xInfoGroups,groupName)){
                LOGGER.info("开始创建消费者组:{}-{}",streamName,groupName );
                String ret= redisConnection.xGroupCreate(streamName.getBytes(),groupName, ReadOffset.latest(),false);
                LOGGER.info(ret);
            }else{
                LOGGER.info("消费者组{}已存在",groupName );
            }
        }else{
            LOGGER.info("开始创建队列-消费者组:{}-{}",streamName,groupName );
            String ret = redisConnection.xGroupCreate(streamName.getBytes(),groupName, ReadOffset.latest(),true);
            LOGGER.info(ret);
        }
        redisConnection.close();
    }
    private boolean checkGroupExist(StreamInfo.XInfoGroups xInfoGroups,String groupName){
        Iterator<StreamInfo.XInfoGroup> it = xInfoGroups.iterator();
         while(it.hasNext()){
             StreamInfo.XInfoGroup group = it.next();
             if(group.groupName().equals(groupName)) return true;
         }
         return false;
    }
}

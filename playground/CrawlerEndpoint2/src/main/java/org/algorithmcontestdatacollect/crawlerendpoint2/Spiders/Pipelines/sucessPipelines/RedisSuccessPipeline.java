package org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Pipelines.sucessPipelines;

import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Pipelines.IPipeline;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Task;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;

public class RedisSuccessPipeline implements IPipeline {
    Environment environment;

    RedisConnectionFactory redisConnectionFactory;

    public RedisSuccessPipeline(RedisConnectionFactory redisConnectionFactory,Environment environment) {
        this.redisConnectionFactory = redisConnectionFactory;
        this.environment = environment;
    }

    @Override
    public void process(Task task) {
        var conn = redisConnectionFactory.getConnection();
        var result = task.getResult();
        var resultStreamName = environment.getProperty("resultQueue.stream","ResultQueue");
        Map<byte[],byte[]> toUpload = new HashMap<>();
        toUpload.put("handler".getBytes(),result.get("handler").toString().getBytes());
        toUpload.put("result".getBytes(),result.get("result").toString().getBytes());
        conn.xAdd(resultStreamName.getBytes(),toUpload);
        conn.close();
    }
}

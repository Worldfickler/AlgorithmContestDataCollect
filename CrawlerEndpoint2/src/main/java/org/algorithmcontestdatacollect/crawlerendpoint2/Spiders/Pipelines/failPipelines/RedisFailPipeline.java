package org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Pipelines.failPipelines;

import org.algorithmcontestdatacollect.crawlerendpoint2.Services.ErrorHandlerToRedis;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Pipelines.IPipeline;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;


public class RedisFailPipeline implements IPipeline {

    ErrorHandlerToRedis errorHandlerToRedis;

    public RedisFailPipeline(ErrorHandlerToRedis errorHandlerToRedis) {
        this.errorHandlerToRedis = errorHandlerToRedis;
    }

    @Override
    public void process(Task task) {
        errorHandlerToRedis.handleTask(task.getFault(),task.getRequestParams());
    }
}

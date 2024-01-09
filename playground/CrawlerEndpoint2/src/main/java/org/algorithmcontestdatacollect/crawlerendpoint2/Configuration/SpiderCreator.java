package org.algorithmcontestdatacollect.crawlerendpoint2.Configuration;

import org.algorithmcontestdatacollect.crawlerendpoint2.Services.ErrorHandlerToRedis;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Downloaders.RestTemplateDownloader;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Pipelines.failPipelines.LoggerFailPipeline;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Pipelines.failPipelines.RedisFailPipeline;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Pipelines.sucessPipelines.LogSuccessPipeline;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Pipelines.sucessPipelines.RedisSuccessPipeline;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Processors.*;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Schedules.NormalSchedule;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Spider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SpiderCreator {
    @Bean(name = "CodeforcesContestList")
    public Spider CodeforcesContestList(RestTemplate restTemplate,TaskExecutor executor, RedisConnectionFactory redisConnectionFactory, Environment environment) {
        return null;
    }

    @Bean(name = "CodeforcesContestRecordSpider")
    public Spider CodeforcesContestRecordSpider(RestTemplate restTemplate, TaskExecutor executor, RedisConnectionFactory redisConnectionFactory, Environment environment) {
        var spider = new Spider("CodeforcesContestRecordSpider");
        var processErrorHandler = new ErrorHandlerToRedis(environment.getProperty("errorQueue.stream"), "ProcessErrorHandler",redisConnectionFactory,environment.getProperty("spring.application.name"));
        var errorHandler = new ErrorHandlerToRedis(environment.getProperty("errorQueue.stream"),"CodeforcesRecordErrorHandler",redisConnectionFactory,environment.getProperty("spring.application.name"));
        spider.setDownloader(new RestTemplateDownloader(restTemplate))
                .setProcessor(new CodeforcesContestRecordProcessor())
                .addSuccessPipeline(new LogSuccessPipeline())
                .addSuccessPipeline(new RedisSuccessPipeline(redisConnectionFactory,environment))
                .addFailPipeline(new LoggerFailPipeline())
                .addFailPipeline(new RedisFailPipeline(errorHandler))
                .setSchedule(new NormalSchedule())
                .setExecutor(executor)
                .setErrorHandler(processErrorHandler)
                .setWorkThreadNum(1)
                .start();
        return spider;
    }

    @Bean(name = "AtcoderContestSubmitSpider")
    public Spider AtcoderContestSubmitSpider(RestTemplate restTemplate, TaskExecutor executor, RedisConnectionFactory redisConnectionFactory, Environment environment) {
        var spider = new Spider("AtcoderContestSubmitSpider");
        var processErrorHandler = new ErrorHandlerToRedis(environment.getProperty("errorQueue.stream"), "ProcessErrorHandler",redisConnectionFactory,environment.getProperty("spring.application.name"));
        var errorHandler = new ErrorHandlerToRedis(environment.getProperty("errorQueue.stream"),"AtcoderContestSubmitErrorHandler",redisConnectionFactory,environment.getProperty("spring.application.name"));
        spider.setDownloader(new RestTemplateDownloader(restTemplate))
                .setProcessor(new AtcoderContestSubmitProcessor())
                .addSuccessPipeline(new LogSuccessPipeline())
                .addSuccessPipeline(new RedisSuccessPipeline(redisConnectionFactory,environment))
                .addFailPipeline(new RedisFailPipeline(errorHandler))
                .addFailPipeline(new LoggerFailPipeline())
                .setSchedule(new NormalSchedule())
                .setExecutor(executor)
                .setErrorHandler(processErrorHandler)
                .setWorkThreadNum(2)
                .start();
        return spider;
    }

    @Bean(name = "AtcoderPastContestList")
    public Spider AtcoderPastContestList(RestTemplate restTemplate, TaskExecutor executor, RedisConnectionFactory redisConnectionFactory, Environment environment) {
        return null;
    }

    @Bean(name = "AtcoderPresentContestList")
    public Spider AtcoderPresentContestList(RestTemplate restTemplate, TaskExecutor executor, RedisConnectionFactory redisConnectionFactory, Environment environment) {

        return null;
    }

    @Bean(name = "AtcoderStucontest")
    public Spider AtcoderStucontest(RestTemplate restTemplate, TaskExecutor executor, RedisConnectionFactory redisConnectionFactory, Environment environment) {
        var spider = new Spider("AtcoderStucontest");
        var processErrorHandler = new ErrorHandlerToRedis(environment.getProperty("errorQueue.stream"), "ProcessErrorHandler",redisConnectionFactory,environment.getProperty("spring.application.name"));
        var errorHandler = new ErrorHandlerToRedis(environment.getProperty("errorQueue.stream"),"AtcoderStucontestErrorHandler",redisConnectionFactory,environment.getProperty("spring.application.name"));
        spider.setDownloader(new RestTemplateDownloader(restTemplate))
                .setProcessor(new AtcoderStucontestProcessor())
                .addSuccessPipeline(new LogSuccessPipeline())
                .addSuccessPipeline(new RedisSuccessPipeline(redisConnectionFactory,environment))
                .addFailPipeline(new LoggerFailPipeline())
                .addFailPipeline(new RedisFailPipeline(errorHandler))
                .setSchedule(new NormalSchedule())
                .setErrorHandler(processErrorHandler)
                .setExecutor(executor)
                .setWorkThreadNum(1)
                .start();
        return spider;
    }
    @Bean(name = "AtcoderSubmitCodeSpider")
    public Spider AtcoderSubmitCodeSpider(RestTemplate restTemplate, TaskExecutor executor, RedisConnectionFactory redisConnectionFactory, Environment environment) {
        var spider = new Spider("AtcoderSubmitCodeSpider");
        var processErrorHandler = new ErrorHandlerToRedis(environment.getProperty("errorQueue.stream"), "ProcessErrorHandler",redisConnectionFactory,environment.getProperty("spring.application.name"));
        var errorHandler = new ErrorHandlerToRedis(environment.getProperty("errorQueue.stream"),"AtcoderSubmitCodeErrorHandler",redisConnectionFactory,environment.getProperty("spring.application.name"));
        spider.setDownloader(new RestTemplateDownloader(restTemplate))
                .setProcessor(new AtcoderSubmitCodeProcessor())
                .addSuccessPipeline(new LogSuccessPipeline())
                .addSuccessPipeline(new RedisSuccessPipeline(redisConnectionFactory,environment))
                .addFailPipeline(new LoggerFailPipeline())
                .addFailPipeline(new RedisFailPipeline(errorHandler))
                .setSchedule(new NormalSchedule())
                .setErrorHandler(processErrorHandler)
                .setExecutor(executor)
                .setWorkThreadNum(2)
                .start();
        return spider;
    }

    @Bean(name = "CodeforcesContestSubmitSpider")
    public Spider CodeforcesContestSubmitSpider(RestTemplate restTemplate, TaskExecutor executor, RedisConnectionFactory redisConnectionFactory, Environment environment) {
        var spider = new Spider("CodeforcesContestSubmitSpider");
        var processErrorHandler = new ErrorHandlerToRedis(environment.getProperty("errorQueue.stream"), "ProcessErrorHandler",redisConnectionFactory,environment.getProperty("spring.application.name"));
        var errorHandler = new ErrorHandlerToRedis(environment.getProperty("errorQueue.stream"),"CodeforcesContestSubmitErrorHandler",redisConnectionFactory,environment.getProperty("spring.application.name"));
        spider.setDownloader(new RestTemplateDownloader(restTemplate))
                .setProcessor(new CodeforcesContestSubmitProcessor())
                .addSuccessPipeline(new LogSuccessPipeline())
                .addSuccessPipeline(new RedisSuccessPipeline(redisConnectionFactory,environment))
                .addFailPipeline(new LoggerFailPipeline())
                .addFailPipeline(new RedisFailPipeline(errorHandler))
                .setSchedule(new NormalSchedule())
                .setErrorHandler(processErrorHandler)
                .setExecutor(executor)
                .setWorkThreadNum(2)
                .start();
        return spider;
    }

    @Bean(name = "CodeforcesRatingChangeContestSpider")
    public Spider CodeforcesRatingChangeContestSpider(RestTemplate restTemplate, TaskExecutor executor, RedisConnectionFactory redisConnectionFactory, Environment environment) {
        return null;
    }

    @Bean(name = "CodeforcesSubmitCodeAPISpider")
    public Spider CodeforcesSubmitCodeAPISpider(RestTemplate restTemplate, TaskExecutor executor, RedisConnectionFactory redisConnectionFactory, Environment environment) {
        var spider = new Spider("CodeforcesSubmitCodeAPISpider");
        var processErrorHandler = new ErrorHandlerToRedis(environment.getProperty("errorQueue.stream"), "ProcessErrorHandler",redisConnectionFactory,environment.getProperty("spring.application.name"));
        var errorHandler = new ErrorHandlerToRedis(environment.getProperty("errorQueue.stream"),"CodeforcesSubmitCodeAPIErrorHandler",redisConnectionFactory,environment.getProperty("spring.application.name"));
        spider.setDownloader(new RestTemplateDownloader(restTemplate))
                .setProcessor(new CodeforcesSubmitCodeAPIProcessor())
                .addSuccessPipeline(new LogSuccessPipeline())
                .addSuccessPipeline(new RedisSuccessPipeline(redisConnectionFactory,environment))
                .addFailPipeline(new LoggerFailPipeline())
                .addFailPipeline(new RedisFailPipeline(errorHandler))
                .setErrorHandler(processErrorHandler)
                .setSchedule(new NormalSchedule())
                .setExecutor(executor)
                .setWorkThreadNum(2)
                .start();
        return spider;
    }

    @Bean(name = "CodeforcesContestTotalQuestionSpider")
    public Spider CodeforcesContestTotalQuestionSpider(RestTemplate restTemplate, TaskExecutor executor, RedisConnectionFactory redisConnectionFactory, Environment environment) {
        var spider = new Spider("CodeforcesContestTotalQuestionSpider");
        var processErrorHandler = new ErrorHandlerToRedis(environment.getProperty("errorQueue.stream"), "ProcessErrorHandler",redisConnectionFactory,environment.getProperty("spring.application.name"));
        var errorHandler = new ErrorHandlerToRedis(environment.getProperty("errorQueue.stream"),"CodeforcesContestTotalQuestionErrorHandler",redisConnectionFactory,environment.getProperty("spring.application.name"));
        spider.setDownloader(new RestTemplateDownloader(restTemplate))
                .setProcessor(new CodeforcesContestTotalQuestionProcessor())
                .addSuccessPipeline(new LogSuccessPipeline())
                .addSuccessPipeline(new RedisSuccessPipeline(redisConnectionFactory,environment))
                .addFailPipeline(new LoggerFailPipeline())
//                .addFailPipeline(new RedisFailPipeline(errorHandler))
                .setErrorHandler(processErrorHandler)
                .setSchedule(new NormalSchedule())
                .setExecutor(executor)
                .setWorkThreadNum(2)
                .start();
        return spider;
    }

    @Bean(name = "CodeforcesContestQuestionSpider")
    public Spider CodeforcesContestQuestionSpider(RestTemplate restTemplate, TaskExecutor executor, RedisConnectionFactory redisConnectionFactory, Environment environment) {
        var spider = new Spider("CodeforcesContestQuestionSpider");
        var processErrorHandler = new ErrorHandlerToRedis(environment.getProperty("errorQueue.stream"), "ProcessErrorHandler",redisConnectionFactory,environment.getProperty("spring.application.name"));
        var errorHandler = new ErrorHandlerToRedis(environment.getProperty("errorQueue.stream"),"CodeforcesContestTotalQuestionErrorHandler",redisConnectionFactory,environment.getProperty("spring.application.name"));
        spider.setDownloader(new RestTemplateDownloader(restTemplate))
                .setProcessor(new CodeforcesContestQuestionProcessor())
                .addSuccessPipeline(new LogSuccessPipeline())
//                .addSuccessPipeline(new RedisSuccessPipeline(redisConnectionFactory,environment))
                .addFailPipeline(new LoggerFailPipeline())
//                .addFailPipeline(new RedisFailPipeline(errorHandler))
                .setErrorHandler(processErrorHandler)
                .setSchedule(new NormalSchedule())
                .setExecutor(executor)
                .setWorkThreadNum(2)
                .start();
        return spider;
    }

}

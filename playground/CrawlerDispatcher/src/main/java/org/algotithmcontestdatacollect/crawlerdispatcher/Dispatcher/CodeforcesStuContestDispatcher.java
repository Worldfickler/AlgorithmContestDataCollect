package org.algotithmcontestdatacollect.crawlerdispatcher.Dispatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service // 为该类创建一个Bean实例，一定要加
public class CodeforcesStuContestDispatcher extends AbstractDispatcher {
    @Autowired
    private Environment environment;
    @Override
    protected String getSpider() {
        return "CodeforcesRatingChangeContestSpider";// Endpoint的Spider名称,会根据该名称调用Spider
    }

    @Override
    protected String getHandler() {
        return "SingleCodeforcesStuContestHandler"; // Endpoint的Handler名称,会根据该名称调用Handler
    }

    @Override
    protected String getStream() {
        return environment.getProperty("crawlerTask.stream"); // 自定义接收器，可以自行设置上传的redis Stream，为以后分布式爬虫留作接口
    }
}

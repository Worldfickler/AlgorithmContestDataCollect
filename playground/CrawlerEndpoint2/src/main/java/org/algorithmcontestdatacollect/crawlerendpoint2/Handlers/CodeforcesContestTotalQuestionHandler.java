package org.algorithmcontestdatacollect.crawlerendpoint2.Handlers;

import com.alibaba.fastjson.JSONArray;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.RequestParams;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Spider;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Task;
import org.springframework.stereotype.Service;

@Service("CodeforcesContestTotalQuestionHandler")
public class CodeforcesContestTotalQuestionHandler implements IHandler {
    @Override
    public void handleRequest(Spider spider, String params) {
        RequestParams requestParams = new RequestParams("https://codeforces.com/api/problemset.problems");
        spider.addTask(new Task(requestParams));
    }
}

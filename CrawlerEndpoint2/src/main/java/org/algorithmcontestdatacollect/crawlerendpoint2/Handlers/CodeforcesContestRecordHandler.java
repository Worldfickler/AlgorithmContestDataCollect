package org.algorithmcontestdatacollect.crawlerendpoint2.Handlers;


import com.alibaba.fastjson.JSONArray;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.RequestParams;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Spider;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Task;
import org.springframework.stereotype.Service;


@Service("CodeforcesContestRecordHandler")
public class CodeforcesContestRecordHandler implements IHandler{
    @Override
    public void handleRequest(Spider spider, String params) {
        JSONArray JSONParams = JSONArray.parseArray(params);
        RequestParams requestParams = new RequestParams("https://codeforces.com/api/user.status?handle="+JSONParams.getString(0));
        spider.addTask(new Task(requestParams));
    }
}

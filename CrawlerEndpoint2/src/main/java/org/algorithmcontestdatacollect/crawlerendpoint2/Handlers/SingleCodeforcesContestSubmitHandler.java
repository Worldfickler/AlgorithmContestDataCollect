package org.algorithmcontestdatacollect.crawlerendpoint2.Handlers;

import com.alibaba.fastjson.JSONArray;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.RequestParams;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Spider;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("SingleCodeforcesContestSubmitHandler")
public class SingleCodeforcesContestSubmitHandler implements IHandler{

    @Override
    public void handleRequest(Spider spider, String params) {
        JSONArray JSONParams = JSONArray.parseArray(params);
        String cfid = JSONParams.getString(0); // 使用JSONArray获取参数
        Long cid = JSONParams.getLong(1);
        spider.addTask(new Task(new RequestParams("https://codeforces.com/api/contest.status?contestId="+cid+"&handle="+cfid)));
    }
}

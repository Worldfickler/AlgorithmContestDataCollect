package org.algorithmcontestdatacollect.crawlerendpoint2.Handlers;

import com.alibaba.fastjson.JSONArray;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.RequestParams;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Spider;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("SingleCodeforcesSubmitCodeHandler")
public class SingleCodeforcesSubmitCodeHandler implements IHandler{
    @Override
    public void handleRequest(Spider spider, String params) {
        JSONArray JSONParams = JSONArray.parseArray(params);
        Long sid = JSONParams.getLong(0); // 使用JSONArray获取参数
        Long cid = JSONParams.getLong(1);
        spider.addTask(new Task(new RequestParams("https://codeforces.com/contest/"+cid+"/submission/"+sid)));
    }
}

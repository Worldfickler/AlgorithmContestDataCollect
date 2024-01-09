package org.algorithmcontestdatacollect.crawlerendpoint2.Handlers;

import com.alibaba.fastjson.JSONArray;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.RequestParams;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Spider;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Task;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("SingleCodeforcesSubmitCodeAPIHandler")
public class SingleCodeforcesSubmitCodeAPIHandler implements IHandler {


    @Override
    public void handleRequest(Spider spider, String params) {
        JSONArray JSONParams = JSONArray.parseArray(params);
        Long sid = JSONParams.getLong(0); // 使用JSONArray获取参数
        Long cid = JSONParams.getLong(1);
        Map<String,String> forms = new HashMap<>();
        forms.put("submissionId",sid.toString());
        RequestParams requestParams = new RequestParams("https://codeforces.com/data/submitSource", HttpMethod.POST,forms);
        spider.addTask(new Task(requestParams));
    }
}

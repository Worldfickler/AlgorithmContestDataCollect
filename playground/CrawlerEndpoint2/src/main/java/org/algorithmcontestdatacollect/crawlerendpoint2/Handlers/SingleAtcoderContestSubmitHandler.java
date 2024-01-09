package org.algorithmcontestdatacollect.crawlerendpoint2.Handlers;

import com.alibaba.fastjson.JSONArray;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.RequestParams;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Spider;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("SingleAtcoderContestSubmitHandler")
public class SingleAtcoderContestSubmitHandler implements IHandler{
    @Override
    public void handleRequest(Spider spider, String params) {
        JSONArray JSONParams = JSONArray.parseArray(params);
        String atid = JSONParams.getString(0); // 使用JSONArray获取参数
        String nickname = JSONParams.getString(1);
        spider.addTask(new Task(new RequestParams("https://atcoder.jp/contests/"+nickname+"/submissions?f.User="+atid+"&page=1")));
    }
}

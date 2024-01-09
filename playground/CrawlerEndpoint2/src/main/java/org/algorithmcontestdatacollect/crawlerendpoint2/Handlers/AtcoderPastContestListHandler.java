package org.algorithmcontestdatacollect.crawlerendpoint2.Handlers;

import com.alibaba.fastjson.JSONArray;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.RequestParams;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Spider;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("AtcoderPastContestListHandler")
public class AtcoderPastContestListHandler implements IHandler{

    @Override
    public void handleRequest(Spider spider, String params) {
    }
}

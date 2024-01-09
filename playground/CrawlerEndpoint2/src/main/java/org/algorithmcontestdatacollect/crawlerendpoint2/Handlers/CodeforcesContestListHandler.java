package org.algorithmcontestdatacollect.crawlerendpoint2.Handlers;

import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.RequestParams;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Spider;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("CodeforcesContestListHandler")
public class CodeforcesContestListHandler implements IHandler{
    @Override
    public void handleRequest(Spider spider, String params) {
    }
}

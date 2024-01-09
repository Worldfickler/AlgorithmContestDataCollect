package org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Processors;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Fault;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.RequestParams;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Task;
import org.algorithmcontestdatacollect.crawlerendpoint2.Utils.RegexUtil;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class AtcoderPastContestListProcessor implements IProcessor{
    private static final Logger LOGGER = LoggerFactory.getLogger(AtcoderPastContestListProcessor.class);
    @Override
    public void process(Task task) {

    }
}

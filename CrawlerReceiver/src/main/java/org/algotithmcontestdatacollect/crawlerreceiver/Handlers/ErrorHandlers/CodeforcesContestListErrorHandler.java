package org.algotithmcontestdatacollect.crawlerreceiver.Handlers.ErrorHandlers;

import com.alibaba.fastjson.JSONObject;
import org.algotithmcontestdatacollect.crawlerreceiver.Handlers.AbstractHandler;
import org.algotithmcontestdatacollect.crawlerreceiver.Repositories.SpiderLogRepository;
import org.algotithmcontestdatacollect.crawlerreceiver.Services.ErrorAnalyseService;
import org.algotithmcontestdatacollect.crawlerreceiver.TableEntity.SpiderLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("CodeforcesContestListErrorHandler")
public class CodeforcesContestListErrorHandler extends AbstractHandler {
    private Logger logger = LoggerFactory.getLogger(CodeforcesContestListErrorHandler.class);
    @Autowired
    ErrorAnalyseService errorAnalyseService;

    @Autowired
    SpiderLogRepository spiderLogRepository;
    @Override
    public void handle(String result) {
        var fault = errorAnalyseService.getFaultFromResult(result);
        var requestParams = errorAnalyseService.getRequestParamsFromResult(result);
        var spiderName = JSONObject.parseObject(result).getString("spiderName");
        var log = SpiderLog.fromFaultAndRequestParam(fault,requestParams,spiderName);
        spiderLogRepository.saveAndFlush(log);
    }
}

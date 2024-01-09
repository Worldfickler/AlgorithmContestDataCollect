package org.algotithmcontestdatacollect.crawlerreceiver.Handlers.ErrorHandlers;

import com.alibaba.fastjson.JSONObject;
import org.algotithmcontestdatacollect.crawlerreceiver.Handlers.AbstractHandler;
import org.algotithmcontestdatacollect.crawlerreceiver.Repositories.SpiderLogRepository;
import org.algotithmcontestdatacollect.crawlerreceiver.Services.ErrorAnalyseService;
import org.algotithmcontestdatacollect.crawlerreceiver.TableEntity.SpiderLogEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("AtcoderContestSubmitErrorHandler")
public class AtcoderContestSubmitErrorHandler extends AbstractHandler {
    private Logger logger = LoggerFactory.getLogger(AtcoderContestSubmitErrorHandler.class);
    @Autowired
    ErrorAnalyseService errorAnalyseService;

    @Autowired
    SpiderLogRepository spiderLogRepository;
    @Override
    public void handle(String result) {
        var fault = errorAnalyseService.getFaultFromResult(result);
        var requestParams = errorAnalyseService.getRequestParamsFromResult(result);
        var spiderName = JSONObject.parseObject(result).getString("spiderName");
        var log = SpiderLogEntity.fromFaultAndRequestParam(fault,requestParams,spiderName);
        if(!fault.getStatusCode().equals(404)) {
            spiderLogRepository.saveAndFlush(log);
        }
//


    }
}

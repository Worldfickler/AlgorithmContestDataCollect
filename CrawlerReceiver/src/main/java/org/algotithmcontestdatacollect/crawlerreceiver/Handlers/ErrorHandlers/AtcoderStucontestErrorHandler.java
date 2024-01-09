package org.algotithmcontestdatacollect.crawlerreceiver.Handlers.ErrorHandlers;

import com.alibaba.fastjson.JSONObject;
import org.algotithmcontestdatacollect.crawlerreceiver.Handlers.AbstractHandler;
import org.algotithmcontestdatacollect.crawlerreceiver.Repositories.SpiderLogRepository;
import org.algotithmcontestdatacollect.crawlerreceiver.Services.ErrorAnalyseService;
import org.algotithmcontestdatacollect.crawlerreceiver.TableEntity.SpiderLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("AtcoderStucontestErrorHandler")
public class AtcoderStucontestErrorHandler extends AbstractHandler {
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

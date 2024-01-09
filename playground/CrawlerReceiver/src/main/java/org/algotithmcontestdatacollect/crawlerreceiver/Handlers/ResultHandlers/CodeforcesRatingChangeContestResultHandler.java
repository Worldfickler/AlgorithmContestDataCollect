package org.algotithmcontestdatacollect.crawlerreceiver.Handlers.ResultHandlers;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.algotithmcontestdatacollect.crawlerreceiver.Handlers.AbstractHandler;
import org.algotithmcontestdatacollect.crawlerreceiver.Repositories.CfAccountRepository;
import org.algotithmcontestdatacollect.crawlerreceiver.Repositories.CfStucontestRepository;
import org.algotithmcontestdatacollect.crawlerreceiver.TableEntity.CfStucontestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // 为该类创建一个Bean实例，一定要加
public class CodeforcesRatingChangeContestResultHandler extends AbstractHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CodeforcesRatingChangeContestResultHandler.class);
    @Override
    public void handle(String result) {
    }
}

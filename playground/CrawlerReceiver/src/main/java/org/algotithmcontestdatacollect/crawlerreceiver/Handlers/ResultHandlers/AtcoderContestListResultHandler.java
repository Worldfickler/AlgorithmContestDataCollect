package org.algotithmcontestdatacollect.crawlerreceiver.Handlers.ResultHandlers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.algotithmcontestdatacollect.crawlerreceiver.Handlers.AbstractHandler;
import org.algotithmcontestdatacollect.crawlerreceiver.Repositories.AcContestRepository;
import org.algotithmcontestdatacollect.crawlerreceiver.TableEntity.AcContestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AtcoderContestListResultHandler extends AbstractHandler {
    private static Logger LOGGER = LoggerFactory.getLogger(AtcoderContestListResultHandler.class);

    @Override
    public void handle(String result) {
    }
}

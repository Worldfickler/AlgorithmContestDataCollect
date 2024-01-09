package org.algotithmcontestdatacollect.crawlerreceiver.Handlers.ResultHandlers;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.algotithmcontestdatacollect.crawlerreceiver.Handlers.AbstractHandler;
import org.algotithmcontestdatacollect.crawlerreceiver.Repositories.CfContestRepository;
import org.algotithmcontestdatacollect.crawlerreceiver.TableEntity.CfContest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CodeforcesContestListResultHandler extends AbstractHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CodeforcesContestListResultHandler.class);
    @Autowired
    private CfContestRepository cfContestRepository;
    public void handle(String result){
        JSONArray contestList = JSONArray.parseArray(result);
        List<CfContest> CodeforcesContestList = cfContestRepository.findAll();
        Map<Long, CfContest> mp = new HashMap<>();
        for(var contest:CodeforcesContestList){
            mp.put(contest.getCid(),contest);
        }
        for(int i = 0;i<contestList.size();i++){
            JSONObject contest = contestList.getJSONObject(i);
            CfContest entity = CfContest.CreateCfContestEntityFromCFContestList(contest);
            var cid = contest.getLong("id");
            if (!mp.containsKey(cid)||!mp.get(cid).equals(entity)){
                LOGGER.info("更新或添加CodeforcesContest{}",entity.getCid());
                cfContestRepository.save(entity);
            }
        }
        cfContestRepository.flush();
    }
}

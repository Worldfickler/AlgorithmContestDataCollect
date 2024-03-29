package org.algotithmcontestdatacollect.crawlerreceiver.Handlers.ResultHandlers;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.algotithmcontestdatacollect.crawlerreceiver.Handlers.AbstractHandler;
import org.algotithmcontestdatacollect.crawlerreceiver.Repositories.CfContestRepository;
import org.algotithmcontestdatacollect.crawlerreceiver.TableEntity.CfContest;
import org.algotithmcontestdatacollect.crawlerreceiver.Repositories.CfAccountRepository;
import org.algotithmcontestdatacollect.crawlerreceiver.Repositories.CfStucontestRepository;
import org.algotithmcontestdatacollect.crawlerreceiver.TableEntity.CfSubmit;
import org.algotithmcontestdatacollect.crawlerreceiver.Repositories.CfSubmitRepository;
import org.algotithmcontestdatacollect.crawlerreceiver.Utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service // 为该类创建一个Bean实例，一定要加
public class CodeforcesContestRecordResultHandler extends AbstractHandler {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(CodeforcesContestRecordResultHandler.class);
    @Autowired
    private CfSubmitRepository cfSubmitRepository;
    @Autowired
    private CfStucontestRepository cfStucontestRepository;
    @Autowired
    private CfAccountRepository cfAccountRepository;
    @Autowired
    private CfContestRepository cfContestRepository;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public void handle(String result) {
//        LOGGER.info(result);
        JSONObject res = JSONObject.parseObject(result);//将数据序列化
        String codeforcesID = res.getString("handler"); // 获取cf账户
        JSONArray submitRecordList = res.getJSONArray("contestRecordList");// 结果列表
        List<CfContest> CodeforcesContestList = cfContestRepository.findAll();
        Map<Long, CfContest> mp = new HashMap<>();//将比赛信息存到hashmap中方便比对时间
        var cfAccountEntity =
                cfAccountRepository.getCfAccountEntityByCodeforcesId(codeforcesID); // 因为不是直接用cf账户存放，需要获取对应cf账户的ID
        if (cfAccountEntity == null) {
            LOGGER.error("账户{}:不在数据库中", codeforcesID);
            return;
        }
        for (var contest : CodeforcesContestList) {
            mp.put(contest.getCid(), contest);
        }
        Long cfid = cfAccountEntity.getId();
        for (int i = 0; i < submitRecordList.size(); i++) { // 一个一个查
            JSONObject submitRecord = submitRecordList.getJSONObject(i);
            Long sid = submitRecord.getLong("id");
            Long cid = submitRecord.getLong("contestId");
            String index = submitRecord.getJSONObject("problem").getString("index");
            String language = submitRecord.getString("programmingLanguage");
            String status = submitRecord.getString("verdict");
            long submitTime = submitRecord.getLongValue("creationTimeSeconds");
            byte isAfter = 1;
            if(!mp.containsKey(cid)){
//                LOGGER.info("比赛{ "+ cid +" }不存在");
                continue;
            }
            if (mp.containsKey(cid) && mp.get(cid).getEndTimeStamp() >= submitTime) {//在比赛结束前的提交记录
                isAfter = 0;
            }
            if (!cfSubmitRepository.existsCfSubmitEntityBySid(sid)) {
                CfSubmit cfSubmitEntity = new CfSubmit();
                cfSubmitEntity.setCfid(cfid);
                cfSubmitEntity.setCid(cid);
                cfSubmitEntity.setSid(sid);
                cfSubmitEntity.setSubmitTime(submitTime);
                cfSubmitEntity.setIsAfter(isAfter);
                cfSubmitEntity.setQindex(index);
                cfSubmitEntity.setLanguage(language);
                cfSubmitEntity.setStatus(status);
                cfSubmitRepository.save(cfSubmitEntity);
                if("OK".equals(status)){ //正确提交
                    List<String> doneProblems = redisUtils.getValuesFromRedis(cfAccountEntity.getUid());
                    String problem = cfSubmitEntity.getCid() + cfSubmitEntity.getQindex();
                    if (!doneProblems.contains(problem)) {
                        doneProblems.add(problem);
                        redisUtils.storeMapInRedis(cfAccountEntity.getUid(),doneProblems);
                    }
                }
            }
        }

    }
}


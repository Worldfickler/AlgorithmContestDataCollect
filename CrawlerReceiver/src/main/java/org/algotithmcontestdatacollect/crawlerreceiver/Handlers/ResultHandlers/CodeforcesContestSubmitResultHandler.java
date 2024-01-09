package org.algotithmcontestdatacollect.crawlerreceiver.Handlers.ResultHandlers;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.algotithmcontestdatacollect.crawlerreceiver.Handlers.AbstractHandler;
import org.algotithmcontestdatacollect.crawlerreceiver.Repositories.CfAccountRepository;
import org.algotithmcontestdatacollect.crawlerreceiver.Repositories.CfContestRepository;
import org.algotithmcontestdatacollect.crawlerreceiver.Repositories.CfSubmitRepository;
import org.algotithmcontestdatacollect.crawlerreceiver.TableEntity.CfContest;
import org.algotithmcontestdatacollect.crawlerreceiver.TableEntity.CfSubmit;
import org.algotithmcontestdatacollect.crawlerreceiver.Utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service // 为该类创建一个Bean实例，一定要加
public class CodeforcesContestSubmitResultHandler extends AbstractHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CodeforcesContestSubmitResultHandler.class);
    @Autowired
    private CfAccountRepository cfAccountRepository;
    @Autowired
    private CfSubmitRepository cfSubmitRepository;
    @Autowired
    private CfContestRepository cfContestRepository;

    @Autowired
    private RedisUtils redisUtils;
    @Override
    public void handle(String result) {
        JSONObject res = JSONObject.parseObject(result);//将数据序列化
        String codeforcesID = res.getString("handler"); // 获取cf账户
        JSONArray contestSubmitList = res.getJSONArray("contestSubmitList");// 结果列表
        var cfAccountEntity = cfAccountRepository.getCfAccountEntityByCodeforcesId(codeforcesID); // 因为不是直接用cf账户存放，需要获取对应cf账户的ID

        if (cfAccountEntity == null){
            LOGGER.error("账户{}:不在数据库中",codeforcesID);
            return;
        }
        Long cfid = cfAccountEntity.getId();//获取submit数据库的Cfid
        int correctNum = 0;//正确提交题目数
        List<CfContest> CodeforcesContestList = cfContestRepository.findAll();
        Map<Long, CfContest> mp = new HashMap<>();//将比赛信息存到hashmap中方便比对时间
        for(var contest:CodeforcesContestList){
            mp.put(contest.getCid(),contest);
        }
        for (int i = 0;i<contestSubmitList.size();i++){
            JSONObject contestSubmit = contestSubmitList.getJSONObject(i);
            Long sid = contestSubmit.getLong("id");
            Long cid = contestSubmit.getLong("contestId");
            String index = contestSubmit.getJSONObject("problem").getString("index");
            long submitTime = contestSubmit.getLongValue("creationTimeSeconds");
            String status = contestSubmit.getString("verdict");
            String language = contestSubmit.getString("programmingLanguage");
            if(!cfSubmitRepository.existsCfSubmitEntityBySid(sid)){  // 没有才放进数据库
                CfSubmit cfSubmitEntity = new CfSubmit();
                cfSubmitEntity.setSid(sid);
                cfSubmitEntity.setCid(cid);
                cfSubmitEntity.setCfid(cfid);
                cfSubmitEntity.setQindex(index);
                cfSubmitEntity.setSubmitTime(submitTime);
                cfSubmitEntity.setStatus(status);
                cfSubmitEntity.setLanguage(language);
                byte isAfter = 1;
                if("OK".equals(status)){ //正确提交
                    List<String> doneProblems = redisUtils.getValuesFromRedis(cfAccountEntity.getUid());
                    String problem = cfSubmitEntity.getCid() + cfSubmitEntity.getQindex();
                    if (!doneProblems.contains(problem)) {
                        doneProblems.add(problem);
                        redisUtils.storeMapInRedis(cfAccountEntity.getUid(),doneProblems);
                    }
                    correctNum++;
                }
                if(mp.containsKey(cid) && mp.get(cid).getEndTimeStamp() >= submitTime){//在比赛结束前的提交记录
//                    cfSubmitEntity.setIsAfter((byte) 0);
                    isAfter = 0;
                }
//                else{
//                    cfSubmitEntity.setIsAfter((byte) 1);
//                }
                cfSubmitEntity.setIsAfter(isAfter);
                cfSubmitRepository.save(cfSubmitEntity);//更新当前提交记录

            }

        }


    }
}


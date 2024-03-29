package org.algotithmcontestdatacollect.crawlerreceiver.Handlers.ResultHandlers;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.algotithmcontestdatacollect.crawlerreceiver.Handlers.AbstractHandler;
import org.algotithmcontestdatacollect.crawlerreceiver.Repositories.CfAccountRepository;
import org.algotithmcontestdatacollect.crawlerreceiver.Repositories.CfStucontestRepository;
import org.algotithmcontestdatacollect.crawlerreceiver.TableEntity.CfStucontest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // 为该类创建一个Bean实例，一定要加
public class CodeforcesRatingChangeContestResultHandler extends AbstractHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CodeforcesRatingChangeContestResultHandler.class);
    @Autowired
    private CfStucontestRepository cfStucontestRepository;
    @Autowired
    private CfAccountRepository cfAccountRepository;
    @Override
    public void handle(String result) {
        JSONObject res = JSONObject.parseObject(result);//将数据序列化
        String codeforcesID = res.getString("handler"); // 获取cf账户
        JSONArray ratingChangeList = res.getJSONArray("ratingChangeList");// 结果列表
        var cfAccountEntity = cfAccountRepository.getCfAccountEntityByCodeforcesId(codeforcesID); // 因为不是直接用cf账户存放，需要获取对应cf账户的ID
        if (cfAccountEntity == null){
            LOGGER.error("账户{}:不在数据库中",codeforcesID);
            return;
        }
        int NowRating = 0;
        for (int i = 0;i<ratingChangeList.size();i++){ // 一个一个查
            JSONObject ratingChange = ratingChangeList.getJSONObject(i);
            int newRating = ratingChange.getInteger("newRating");
            int oldRating = ratingChange.getInteger("oldRating");
            int rank = ratingChange.getInteger("rank");
            Long cid = ratingChange.getLong("contestId");
            NowRating = newRating;
            if(!cfStucontestRepository.existsCfStucontestEntityByCfidAndCid(cfAccountEntity.getId(),cid)){ // 没有才放进数据库
                CfStucontest cfStucontest = new CfStucontest();
                cfStucontest.setDiff(newRating - oldRating);
                cfStucontest.setCid(cid);
                cfStucontest.setCfid(cfAccountEntity.getId());
                cfStucontest.setRank(rank);
                cfStucontest.setRating(newRating);
                cfStucontestRepository.save(cfStucontest);
             }
        }
        cfAccountEntity.setRating(NowRating);
        cfAccountRepository.save(cfAccountEntity); // 更新当前cf账户
    }
}

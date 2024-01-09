package org.algotithmcontestdatacollect.managebackend.Services.ApplicationHandler;

import com.alibaba.fastjson.JSONObject;
import org.algotithmcontestdatacollect.managebackend.Entities.Application;
import org.algotithmcontestdatacollect.managebackend.Entities.CodeforcesEntities.CfAccount;
import org.algotithmcontestdatacollect.managebackend.Repositories.CfRepository.CfAccountRepository;
import org.algotithmcontestdatacollect.managebackend.Services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.REQUIRED)
@Service
public class CodeforcesAccountAddHandler extends AbstarctHandler {
    @Autowired
    LogService logService;
    @Autowired
    private CfAccountRepository cfAccountRepository;
    public void Handle(Application entity) {
        CfAccount NewEntity;
        var uid = entity.getUid();
        var params = JSONObject.parseObject(entity.getParameter());
        try {
            params.put("uid",uid);
            params.put("is_using",1);
            NewEntity = CfAccount.fromParam(params);
        }catch (Exception err){
            logService.log("工单Id：" + entity.getId()+"\n参数不足,具体报错:"+err.getMessage());
            throw new RuntimeException("参数不足");
        }
        if(cfAccountRepository.existsByCodeforcesId(NewEntity.getCodeforcesId())) {
            logService.log("工单Id：" + entity.getId()+"\n已存在该codeforces账户");
            throw new RuntimeException("已存在该codeforces账户");
        }
        int userAccountNum = cfAccountRepository.countByUid(uid);
        if (userAccountNum >= 3){
            logService.log("工单Id：" + entity.getId()+"\n用户Codeforces账户数量已为三个");
            throw new RuntimeException("用户账号数量已为3个");
        }
        if (userAccountNum == 0) {
            NewEntity.setIsMain(Byte.parseByte("1"));
        } else if (params.getByte("is_main") == 1) {
            cfAccountRepository.resetAllMain(uid);
        }
        try{
            cfAccountRepository.save(NewEntity);
        }catch (Exception err){
            logService.log("工单Id：" + entity.getId()+"\n用户名有重复或数据库连接错误,具体报错:"+err.getMessage());
            throw new RuntimeException("用户名有重复或数据库连接错误");
        }

    }
}


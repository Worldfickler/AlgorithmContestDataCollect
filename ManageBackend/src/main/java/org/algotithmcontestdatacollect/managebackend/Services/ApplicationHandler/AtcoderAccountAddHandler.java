package org.algotithmcontestdatacollect.managebackend.Services.ApplicationHandler;

import com.alibaba.fastjson.JSONObject;
import org.algotithmcontestdatacollect.managebackend.Entities.AtcoderEntities.AcAccount;
import org.algotithmcontestdatacollect.managebackend.Entities.Application;
import org.algotithmcontestdatacollect.managebackend.Repositories.AcRepositories.AcAccountRepository;
import org.algotithmcontestdatacollect.managebackend.Services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.REQUIRED)
@Service
public class AtcoderAccountAddHandler extends AbstarctHandler {

    @Autowired
    LogService logService;
    @Autowired
    private AcAccountRepository acAccountRepository;
    @Override
    public void Handle(Application entity) {
        AcAccount NewEntity;
        var uid = entity.getUid();
        var params = JSONObject.parseObject(entity.getParameter());
        try {
            params.put("uid",uid);
            params.put("is_using",1);
            NewEntity = AcAccount.fromParam(params);
        }catch (Exception err){
            logService.log("工单Id：" + entity.getId()+"\n参数不足,具体报错:"+err.getMessage());
            throw new RuntimeException("参数不足");
        }
        if(acAccountRepository.existsByAtcoderId(NewEntity.getAtcoderId())){
            logService.log("工单Id：" + entity.getId()+"\n已存在该atcoder账户");
            throw new RuntimeException("已存在该atcoder账户");
        }
        int userAccountNum = acAccountRepository.countByUid(uid);
        if (userAccountNum >= 1){
            logService.log("工单Id：" + entity.getId()+"\n用户Atcoder账户数量已为1个");
            throw new RuntimeException("用户账号数量已为1个");
        }
        if (userAccountNum == 0) {
            NewEntity.setIsMain(Byte.parseByte("1"));
        } else if (params.getByte("is_main") == 1) {
            acAccountRepository.resetAllMain(uid);
        }
        try{
            acAccountRepository.saveAndFlush(NewEntity);
        }catch (Exception err){
            logService.log("工单Id：" + entity.getId()+"\n用户名有重复或数据库连接错误,具体报错:"+err.getMessage());
            throw new RuntimeException("用户名有重复或数据库连接错误");
        }

    }
}

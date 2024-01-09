package org.algotithmcontestdatacollect.managebackend.Services.ApplicationHandler;

import org.algotithmcontestdatacollect.managebackend.Services.LogService;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import org.algotithmcontestdatacollect.managebackend.Entities.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.algotithmcontestdatacollect.managebackend.Repositories.ApplicationRepository;
import org.algotithmcontestdatacollect.managebackend.Entities.NormalUser;
import org.algotithmcontestdatacollect.managebackend.Repositories.NormalUserRepository;
import org.springframework.transaction.annotation.Propagation;

@Transactional(propagation = Propagation.REQUIRED)
@Service
public class CreateNewUserHandler extends AbstarctHandler {
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private NormalUserRepository userRepository;

    @Autowired
    private LogService logService;

    @Override
    public void Handle(Application entity) {
        try{
            var params = JSONObject.parseObject(entity.getParameter());
            var user = NormalUser.fromParam(params);
            userRepository.save(user);
        }catch (Exception err){
            logService.log("工单Id：" + entity.getId()+"\n用户名有重复或参数不足,具体报错:"+err.getMessage());
            if(err.getMessage().contains("constraint [normal_user.username]")){
                throw new RuntimeException("已存在该用户名");
            }
            throw new RuntimeException("用户名有重复或参数不足");
        }

    }
    
}

package org.algotithmcontestdatacollect.managebackend.Services;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.algotithmcontestdatacollect.managebackend.Entities.AtcoderEntities.AcAccount;
import org.algotithmcontestdatacollect.managebackend.Entities.CodeforcesEntities.CfAccount;
import org.algotithmcontestdatacollect.managebackend.Entities.NormalUser;
import org.algotithmcontestdatacollect.managebackend.Repositories.AcRepositories.AcAccountRepository;
import org.algotithmcontestdatacollect.managebackend.Repositories.CfRepository.CfAccountRepository;
import org.algotithmcontestdatacollect.managebackend.Repositories.NormalUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional(propagation = Propagation.REQUIRED)
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    public NormalUserRepository normalUserRepository;
    @Autowired
    CfAccountRepository cfAccountRepository;

    @Autowired
    AcAccountRepository acAccountRepository;

    public void addUser(JSONObject user) throws Exception {
        try {
            var cfids = user.getJSONArray("codeforcesId");
            var acids = user.getJSONArray("atcoderId");
            List<String> codeforcesUsername = new ArrayList<>();
            List<String> atcoderUsername = new ArrayList<>();
            for(int j = 0;j<cfids.size();j++){
                codeforcesUsername.add(cfids.getString(j));
            }
            for(int j= 0;j<acids.size();j++){
                atcoderUsername.add(acids.getString(j));
            }
            addSingleUserWithCodeforcesAccountAndAtcoderAccount(NormalUser.fromParam(user),codeforcesUsername,atcoderUsername);
        }catch (Exception err){
            logger.info(err.toString());
            throw new Exception("第"+user.getString("username")+"条数据有误");
        }
    }

    public void addUserBatch(JSONArray users) throws Exception {
        for (int i = 0 ; i < users.size();i++) {
            try {
                var info =users.getJSONObject(i);
                var cfids = info.getJSONArray("codeforcesId");
                var acids = info.getJSONArray("atcoderId");
                List<String> codeforcesUsername = new ArrayList<>();
                List<String> atcoderUsername = new ArrayList<>();
                for(int j = 0;j<cfids.size();j++){
                    codeforcesUsername.add(cfids.getString(j));
                }
                for(int j= 0;j<acids.size();j++){
                    atcoderUsername.add(acids.getString(j));
                }
                addSingleUserWithCodeforcesAccountAndAtcoderAccount(NormalUser.fromParam(info),codeforcesUsername,atcoderUsername);
            }catch (Exception err){
                logger.info(err.toString());
                throw new Exception(users.getJSONObject(i).toJSONString() + "出现错误");
            }
        }
        normalUserRepository.flush();
    }
    public void addSingleUserWithCodeforcesAccountAndAtcoderAccount(NormalUser user, List<String> codeforcesUsername, List<String> atcoderUsername) {
        var newUser = normalUserRepository.save(user);
        for(int i = 0;i<codeforcesUsername.size();i++) {
            var newCodeforcesAccount = new CfAccount();
            newCodeforcesAccount.setIsUsing(Byte.parseByte("1"));
            newCodeforcesAccount.setUid(newUser.getId());
            newCodeforcesAccount.setCodeforcesId(codeforcesUsername.get(i));
            if (i == 0) {
                newCodeforcesAccount.setIsMain(Byte.parseByte("1"));
            }else{
                newCodeforcesAccount.setIsMain(Byte.parseByte("0"));
            }
            cfAccountRepository.save(newCodeforcesAccount);
        }
        for(int i = 0;i<atcoderUsername.size();i++) {
            var newAcAccount = new AcAccount();
            newAcAccount.setIsUsing(Byte.parseByte("1"));
            newAcAccount.setUid(newUser.getId());
            newAcAccount.setAtcoderId(atcoderUsername.get(i));
            if (i == 0) {
                newAcAccount.setIsMain(Byte.parseByte("1"));
            }else{
                newAcAccount.setIsMain(Byte.parseByte("0"));
            }
            acAccountRepository.save(newAcAccount);
        }
    }
}

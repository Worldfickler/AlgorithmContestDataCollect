package org.algotithmcontestdatacollect.managebackend.Controllers;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.algotithmcontestdatacollect.managebackend.Entities.CodeforcesEntities.CfAccountWithUsername;
import org.algotithmcontestdatacollect.managebackend.Repositories.CfRepository.CfAccountRepository;
import org.algotithmcontestdatacollect.managebackend.Repositories.CfRepository.CfAccountWithUsernameRepository;
import org.algotithmcontestdatacollect.managebackend.Utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
public class CodeforcesAccountController {
    private static final Logger logger = LoggerFactory.getLogger(CodeforcesAccountController.class);
    @Autowired
    private CfAccountWithUsernameRepository cfAccountWithUsernameRepository;

    @Autowired
    private CfAccountRepository cfAccountRepository;
    @GetMapping("/api/codeforces/account")
    public String getAllAccounts(HttpServletRequest request) {
        List<CfAccountWithUsername> cfcountEntity;
        try{
            if (request.getAttribute("isSuper").equals("1")) {
                cfcountEntity = cfAccountWithUsernameRepository.findAll();
            }else{
                cfcountEntity = cfAccountWithUsernameRepository.findBySchool(Long.parseLong((String) request.getAttribute("school")));
            }
        }catch (Exception e){
            return ResponseUtil.JSONReturn(404,"后端异常");
        }
        return ResponseUtil.JSONReturn(200, (JSONArray) JSONArray.toJSON(cfcountEntity));
    }

    @DeleteMapping("/api/codeforces/account")
    public String getAccount(@RequestBody JSONObject data, HttpServletRequest request) {
        Long id = data.getLong("id");
        Optional<CfAccountWithUsername> accountEntity;
        try{
            if (request.getAttribute("isSuper").equals("1")) {
                accountEntity = cfAccountWithUsernameRepository.findById(id);
            }else{
                accountEntity = cfAccountWithUsernameRepository.findByIdAndSchool(id,Long.parseLong((String) request.getAttribute("school")));
            }
        }catch (Exception e) {
            return ResponseUtil.JSONReturn(404,"数据库异常");
        }
        if(accountEntity.isPresent()) {
            cfAccountRepository.deleteById(accountEntity.get().getId());
            return ResponseUtil.JSONReturn(200,"删除成功");
        }else{
            return ResponseUtil.JSONReturn(404,"权限不足或账号不存在");
        }
    }



}

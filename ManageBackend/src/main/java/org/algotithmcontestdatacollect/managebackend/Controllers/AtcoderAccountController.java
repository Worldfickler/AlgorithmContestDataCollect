package org.algotithmcontestdatacollect.managebackend.Controllers;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.algotithmcontestdatacollect.managebackend.Entities.AtcoderEntities.AcAccountWithUsername;
import org.algotithmcontestdatacollect.managebackend.Repositories.AcRepositories.AcAccountRepository;
import org.algotithmcontestdatacollect.managebackend.Repositories.AcRepositories.AcAccountWithUsernameRepository;
import org.algotithmcontestdatacollect.managebackend.Utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
public class AtcoderAccountController {
    @Autowired
    AcAccountWithUsernameRepository acAccountWithUsernameRepository;

    @Autowired
    AcAccountRepository acAccountRepository;

    @GetMapping("/api/atcoder/account")
    public String getAllAccounts(HttpServletRequest request) {
        List<AcAccountWithUsername> accountEntity;
        try{
            if (request.getAttribute("isSuper").equals("1")) {
                accountEntity = acAccountWithUsernameRepository.findAll();
            }else{
                accountEntity = acAccountWithUsernameRepository.findBySchool(Long.parseLong((String) request.getAttribute("school")));
            }
        }catch (Exception e){
            return ResponseUtil.JSONReturn(404,"后端异常");
        }
        return ResponseUtil.JSONReturn(200, (JSONArray) JSONArray.toJSON(accountEntity));
    }

    @DeleteMapping("/api/atcoder/account")
    public String getAccount(@RequestBody JSONObject data, HttpServletRequest request) {
        Long id = data.getLong("id");
        Optional<AcAccountWithUsername> accountEntity;
        try{
            if (request.getAttribute("isSuper").equals("1")) {
                accountEntity = acAccountWithUsernameRepository.findById(id);
            }else{
                accountEntity = acAccountWithUsernameRepository.findByIdAndSchool(id,Long.parseLong((String) request.getAttribute("school")));
            }
        }catch (Exception e) {
            return ResponseUtil.JSONReturn(404,"数据库异常");
        }
        if(accountEntity.isPresent()) {
            acAccountRepository.deleteById(accountEntity.get().getId());
            return ResponseUtil.JSONReturn(200,"删除成功");
        }else{
            return ResponseUtil.JSONReturn(404,"权限不足或账号不存在");
        }
    }
}

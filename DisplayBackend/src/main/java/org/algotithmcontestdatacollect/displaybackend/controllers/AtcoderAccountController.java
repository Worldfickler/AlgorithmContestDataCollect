package org.algotithmcontestdatacollect.displaybackend.controllers;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.algotithmcontestdatacollect.displaybackend.entities.AtcoderEntities.AcAccountWithUsername;
import org.algotithmcontestdatacollect.displaybackend.entities.Application;
import org.algotithmcontestdatacollect.displaybackend.repositories.AcRepositories.AcAccountWithUsernameRepositories;
import org.algotithmcontestdatacollect.displaybackend.repositories.ApplicationRepository;
import org.algotithmcontestdatacollect.displaybackend.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class AtcoderAccountController {

    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private AcAccountWithUsernameRepositories acAccountWithUsernameRepositories;
    @GetMapping("/api/user/AtcoderAccount")
    public String getAllAccounts(HttpServletRequest request) {
        Long uid = Long.parseLong((String) request.getAttribute("id"));
        List<AcAccountWithUsername> accountEntity = acAccountWithUsernameRepositories.getAcAccountWithUsernameEntitiesByUid(uid);
        return ResponseUtil.JSONReturn(200, (JSONArray) JSONArray.toJSON(accountEntity));
    }
    @PostMapping("/api/user/AtcoderAccount")
    public String createNewAccount(@RequestBody JSONObject data, HttpServletRequest request){
        var atcoder_id = data.getString("atcoder_id");
        var is_main = data.getByte("is_main");
        if (checkExistAndEmpty(atcoder_id) || is_main == null) {
            return ResponseUtil.JSONReturn(404,"参数不足");
        }
        var uid = Long.parseLong((String) request.getAttribute("id"));
        var school = Long.parseLong((String) request.getAttribute("school"));
        var newAccount = Application.addAtcoderAccount(atcoder_id,uid,is_main,school);
        try{
            newAccount = applicationRepository.saveAndFlush(newAccount);
        }catch (Exception err){
            return ResponseUtil.JSONReturn(404,"添加失败");
        }
        return ResponseUtil.JSONReturn(200,"添加成功");
    }
    private boolean checkExistAndEmpty(String param){
        return param == null || param.equals("");
    }
}

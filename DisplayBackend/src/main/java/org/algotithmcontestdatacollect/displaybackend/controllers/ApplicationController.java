package org.algotithmcontestdatacollect.displaybackend.controllers;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.algotithmcontestdatacollect.displaybackend.entities.Application;
import org.algotithmcontestdatacollect.displaybackend.entities.ApplicationWithUserinfo;
import org.algotithmcontestdatacollect.displaybackend.repositories.ApplicationRepository;
import org.algotithmcontestdatacollect.displaybackend.repositories.ApplicationWithUserinfoRepository;
import org.algotithmcontestdatacollect.displaybackend.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class ApplicationController {
    @Autowired
    private ApplicationWithUserinfoRepository applicationWithUserinfoRepository;
    @Autowired
    private ApplicationRepository applicationRepository;
    @GetMapping("/api/user/application")
    public String getApplication(HttpServletRequest request){

        Long uid = Long.parseLong((String) request.getAttribute("id"));
        List<ApplicationWithUserinfo> datas = applicationWithUserinfoRepository.getApplicationWithUserinfoEntitiesByUid(uid);
        return ResponseUtil.JSONReturn(200, (JSONArray) JSONArray.toJSON(datas));
    }

    @PostMapping("/api/user/application")
    public String SubmitApplication(@RequestBody JSONObject data,HttpServletRequest request) {
        Long uid = Long.parseLong((String) request.getAttribute("id"));
        var school = Long.parseLong((String) request.getAttribute("school"));
        var op = data.getString("operation");
        var params = data.getJSONObject("params");
        var ent = Application.ParseRequest(op,uid,school,params);
        applicationRepository.saveAndFlush(ent);
        return ResponseUtil.JSONReturn(200,"提交成功");
    }
}

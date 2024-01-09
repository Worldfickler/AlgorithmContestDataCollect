package org.algotithmcontestdatacollect.displaybackend.controllers;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.algotithmcontestdatacollect.displaybackend.entities.AtcoderEntities.AcSubmit;
import org.algotithmcontestdatacollect.displaybackend.entities.CodeforcesEntities.CfSubmit;
import org.algotithmcontestdatacollect.displaybackend.repositories.AcRepositories.AcSubmitRepository;
import org.algotithmcontestdatacollect.displaybackend.repositories.CfRepository.CfSubmitRepository;
import org.algotithmcontestdatacollect.displaybackend.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/submit")
public class SubmitDataController {
    @Autowired
    CfSubmitRepository cfSubmitRepository;
    @Autowired
    AcSubmitRepository acSubmitRepository;

    @GetMapping("/week")
    public String weekSubmit() {
        JSONArray ret = new JSONArray();
        Long NowTime = (System.currentTimeMillis() / 1000);
        Long todayZeroTimeStamp = NowTime - (NowTime + 3600*8) % 86400;
        Long endStamp = todayZeroTimeStamp + 86400;
        Long startStamp = todayZeroTimeStamp - 6*86400;
        List<CfSubmit> cfList = cfSubmitRepository.getCfSubmitEntitiesBySubmitTimeBetween(startStamp,endStamp);
        List<AcSubmit> acList = acSubmitRepository.getAcSubmitEntitiesBySubmitTimeBetween(startStamp,endStamp);
        for(int i = 0;i<7;i++){
            JSONObject todayInfo = new JSONObject();
            todayInfo.put("total",0);
            todayInfo.put("after",0);
            todayInfo.put("solve",0);
            todayInfo.put("aftersolve",0);
            ret.add(todayInfo);
        }
        for(var submit:cfList) {
            int idx = (int) ((endStamp - submit.getSubmitTime())/86400);
            if(idx==7) {
                idx--;
            }
            var obj = ret.getJSONObject(idx);
            obj.put("total",obj.getInteger("total") + 1);
            if (submit.getIsAfter() == 1) {
                obj.put("after",obj.getInteger("after") + 1);
            }
            if (submit.getStatus().equals("OK")) {
                obj.put("solve",obj.getInteger("solve") + 1);
                if(submit.getIsAfter() == 1) {
                    obj.put("aftersolve",obj.getInteger("aftersolve") + 1);
                }
            }
        }
        for(var submit:acList) {
            int idx = (int) ((endStamp - submit.getSubmitTime())/86400);
            if(idx==7) {
                idx--;
            }
            var obj = ret.getJSONObject(idx);
            obj.put("total",obj.getInteger("total") + 1);
            if (submit.getIsAfter() == 1) {
                obj.put("after",obj.getInteger("after") + 1);
            }
            if (submit.getStatus().equals("AC")) {
                obj.put("solve",obj.getInteger("solve") + 1);
                if(submit.getIsAfter() == 1) {
                    obj.put("aftersolve",obj.getInteger("aftersolve") + 1);
                }
            }
        }
        return ResponseUtil.JSONReturn(200,ret);
    }
}

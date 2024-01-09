package org.algotithmcontestdatacollect.displaybackend.controllers;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.algotithmcontestdatacollect.displaybackend.repositories.AcRepositories.AcMonthSubmitRepository;
import org.algotithmcontestdatacollect.displaybackend.repositories.AcRepositories.AcStucontestWithCinfoUserinfoRepository;
import org.algotithmcontestdatacollect.displaybackend.repositories.AcRepositories.AcSubmitWithUserinfoRepository;
import org.algotithmcontestdatacollect.displaybackend.repositories.CfRepository.CfMonthSubmitRepository;
import org.algotithmcontestdatacollect.displaybackend.repositories.CfRepository.CfStucontestWithCinfoUserinfoRepository;
import org.algotithmcontestdatacollect.displaybackend.repositories.CfRepository.CfSubmitWithUserinfoRepository;
import org.algotithmcontestdatacollect.displaybackend.repositories.CfRepository.CodeforcesOkSubmitWithTagsRepository;
import org.algotithmcontestdatacollect.displaybackend.repositories.TagRepositories.TagRepository;
import org.algotithmcontestdatacollect.displaybackend.repositories.TagRepositories.TagUserDetailRepository;
import org.algotithmcontestdatacollect.displaybackend.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tag")
public class TagController {
    @Autowired
    AcMonthSubmitRepository acMonthSubmitRepository;

    @Autowired
    CfMonthSubmitRepository cfMonthSubmitRepository;

    @Autowired
    AcStucontestWithCinfoUserinfoRepository acStucontestWithCinfoUserinfoRepository;

    @Autowired
    CfStucontestWithCinfoUserinfoRepository cfStucontestWithCinfoUserinfoRepository;
    
    @Autowired
    TagUserDetailRepository tagUserDetailRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    AcSubmitWithUserinfoRepository acSubmitWithUserinfoRepository;
    @Autowired
    CfSubmitWithUserinfoRepository cfSubmitWithUserinfoRepository;

    @Autowired
    CodeforcesOkSubmitWithTagsRepository codeforcesOkSubmitWithTagsRepository;


    @GetMapping("/list")
    public String getTagList() {
        var tagList = tagRepository.findAll();
        return ResponseUtil.JSONReturn(200,(JSONArray) JSONArray.toJSON(tagList));
    }
    @GetMapping("/userInfo/{tid}")
    public String getUserInfo(@PathVariable("tid") Long tid) {
        var tagUserDetail = tagUserDetailRepository.findAllByTid(tid);
        return ResponseUtil.JSONReturn(200, (JSONArray) JSONArray.toJSON(tagUserDetail));
    }
    @GetMapping("/stucontestInfo/{tid}")
    public String getTagStuContest(@PathVariable("tid") Long tid) {
        var acStuContest = acStucontestWithCinfoUserinfoRepository.getAcStucontestWithCinfoUserinfoEntitiesByTid(tid);
        var cfStuContest = cfStucontestWithCinfoUserinfoRepository.getCfStucontestWithCinfoUserinfoEntitiesByTid(tid);
        var result = new JSONObject();
        result.put("acStuContest",acStuContest);
        result.put("cfStuContest",cfStuContest);
        return ResponseUtil.JSONReturn(200,result);
    }
    @GetMapping("/submitInfo/{tid}")
    public String getTagStuMonthSubmitCount(@PathVariable("tid") Long tid) {
        var acStuSubmit = acMonthSubmitRepository.getAcMonthSubmitEntitiesByTid(tid);
        var cfStuSubmit = cfMonthSubmitRepository.getCfMonthSubmitEntitiesByTid(tid);
        var acJSON = (JSONArray) JSONArray.toJSON(acStuSubmit);
        var cfJSON = (JSONArray) JSONArray.toJSON(cfStuSubmit);
        var result = new JSONObject();
        result.put("ac",acJSON);
        result.put("cf",cfJSON);
        return ResponseUtil.JSONReturn(200,result);
    }

    @GetMapping("/OKSubmitInfo/{tid}")
    public String getTagCfOkSubmitInfo(@PathVariable("tid") Long tid) {
        var cfOkSubmit = codeforcesOkSubmitWithTagsRepository.getCodeforcesOkSubmitWithTagsEntitiesByTid(tid);
        return ResponseUtil.JSONReturn(200,(JSONArray) JSONArray.toJSON(cfOkSubmit));
    }

}

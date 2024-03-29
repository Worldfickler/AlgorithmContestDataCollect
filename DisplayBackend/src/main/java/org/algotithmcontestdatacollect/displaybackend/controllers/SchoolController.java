package org.algotithmcontestdatacollect.displaybackend.controllers;

import com.alibaba.fastjson.JSONArray;
import org.algotithmcontestdatacollect.displaybackend.repositories.SchoolRepository;
import org.algotithmcontestdatacollect.displaybackend.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SchoolController {
    @Autowired
    public SchoolRepository schoolRepository;
    @GetMapping("/api/school")
    public String getSchool() {
        var schoolList = schoolRepository.findAll();
        var ret = (JSONArray) JSONArray.toJSON(schoolList);
        return ResponseUtil.JSONReturn(200, ret);
    }
}

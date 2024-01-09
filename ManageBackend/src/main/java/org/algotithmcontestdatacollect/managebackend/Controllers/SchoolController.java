package org.algotithmcontestdatacollect.managebackend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;

import org.algotithmcontestdatacollect.managebackend.Entities.School;
import org.algotithmcontestdatacollect.managebackend.Repositories.SchoolRepository;
import org.algotithmcontestdatacollect.managebackend.Utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import com.alibaba.fastjson.JSONObject;
@RestController
public class SchoolController {
    private static final Logger logger = LoggerFactory.getLogger(SchoolController.class);
    @Autowired
    SchoolRepository schoolRepository;
    @GetMapping("/api/school")
    public String getSchool() {
        var schoolList = schoolRepository.findAll();
        var ret = (JSONArray) JSONArray.toJSON(schoolList);
        return ResponseUtil.JSONReturn(200, ret);
    }
    @PostMapping("/api/admin/school")
    public String addSchool(@RequestBody JSONObject data) {
        var school = data.getString("school");
        if (school == null) {
            return ResponseUtil.JSONReturn(404, "参数不足");
        }
        var schoolEntity = schoolRepository.findByName(school);
        if (schoolEntity != null) {
            return ResponseUtil.JSONReturn(401, "学校已存在");
        }else{
            schoolEntity = new School();
        }
        schoolEntity.setName(school);
        try {
            schoolEntity = schoolRepository.saveAndFlush(schoolEntity);
        } catch (Exception e) {
            return ResponseUtil.JSONReturn(401, "创建失败");
        }
        return ResponseUtil.JSONReturn(200,(JSONObject) JSONObject.toJSON(schoolEntity));
    }

    @DeleteMapping("/api/admin/school")
    public String deleteSchool(@RequestBody JSONObject data) {
        var school = data.getLong("id");
        if (school == null) {
            return ResponseUtil.JSONReturn(404, "参数不足");
        }
        try {
            schoolRepository.deleteById(school);
        } catch (Exception e) {
            return ResponseUtil.JSONReturn(401, "删除失败");
        }
        return ResponseUtil.JSONReturn(200, "删除成功");
    }
}

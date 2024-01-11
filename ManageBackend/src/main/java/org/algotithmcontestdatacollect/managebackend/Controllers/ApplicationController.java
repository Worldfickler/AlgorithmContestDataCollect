package org.algotithmcontestdatacollect.managebackend.Controllers;

import org.algotithmcontestdatacollect.managebackend.Entities.ApplicationWithUserinfo;
import org.algotithmcontestdatacollect.managebackend.Entities.Log;
import org.algotithmcontestdatacollect.managebackend.Repositories.ApplicationWithUserinfoRepository;
import org.algotithmcontestdatacollect.managebackend.Repositories.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.algotithmcontestdatacollect.managebackend.Repositories.ApplicationRepository;
import org.algotithmcontestdatacollect.managebackend.Services.ApplicationService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.algotithmcontestdatacollect.managebackend.Utils.ResponseUtil;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ApplicationController {
    Logger logger = LoggerFactory.getLogger(ApplicationController.class);
    @Autowired
    private ApplicationWithUserinfoRepository applicationWithUserinfoRepository;
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private ApplicationService applicationService;

    @Autowired
    LogRepository logRepository;

    /**
     * 获取申请信息
     * @param request
     * @return
     */
    @GetMapping("/api/application")
    public String getApplication(HttpServletRequest request) {
        List<ApplicationWithUserinfo> list;
        var admin = Short.parseShort((String) request.getAttribute("isSuper"));
        if (admin == 1) {
            list = applicationWithUserinfoRepository.findAll();
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.toString());
            }
            return ResponseUtil.JSONReturn(200, (JSONArray) JSONArray.toJSON(list));
        }
        var school = Long.parseLong((String) request.getAttribute("school"));
        list = applicationWithUserinfoRepository.findBySchool(school);
        if (list == null) {
            return ResponseUtil.JSONReturn(404, "没有申请记录");
        }
        return ResponseUtil.JSONReturn(200, (JSONArray) JSONArray.toJSON(list));
    }

    @PostMapping("/api/application")
    public String handleApplication(@RequestBody JSONObject data, HttpServletRequest request) {
        var id = (Long) data.getLong("id");
        var status = data.getByte("status");
        var aName = (String) request.getAttribute("username");
        if (id == null || status == null) {
            return ResponseUtil.JSONReturn(404, "参数不足");
        }
        var applicationEntity = applicationRepository.findById(id);
        if (applicationEntity.isPresent()) {
            if (applicationEntity.get().getStatus() == 1 || applicationEntity.get().getStatus() == 2) {
                return ResponseUtil.JSONReturn(404, "已经处理过了");
            }
            try {
                if (status == 1) {
                    applicationService.acceptApplication(id, aName);
                } else if (status == 2) {
                    applicationService.rejectApplication(id, aName);
                }
            } catch (Exception E) {
                return ResponseUtil.JSONReturn(404, E.getMessage());
            }
        } else {
            return ResponseUtil.JSONReturn(404, "申请不存在");
        }
        return ResponseUtil.JSONReturn(200, "处理成功");
    }

    @GetMapping("/api/applicationLog")
    public String getApplicationLog(HttpServletRequest request) {
        List<Log> list;
        var admin = Short.parseShort((String) request.getAttribute("isSuper"));
        if (admin == 1) {
            list = logRepository.findAll();
            return ResponseUtil.JSONReturn(200, (JSONArray) JSONArray.toJSON(list));
        }
        var uid = Long.parseLong((String) request.getAttribute("id"));
        list = logRepository.getLogEntitiesByUid(uid);
        return ResponseUtil.JSONReturn(200, (JSONArray) JSONArray.toJSON(list));
    }
}

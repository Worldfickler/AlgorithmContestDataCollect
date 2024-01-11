package org.algotithmcontestdatacollect.managebackend.Controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.algotithmcontestdatacollect.managebackend.Entities.NormalUser;
import org.algotithmcontestdatacollect.managebackend.Repositories.NormalUserRepository;
import org.algotithmcontestdatacollect.managebackend.Services.UserService;
import org.algotithmcontestdatacollect.managebackend.Utils.ResponseUtil;
import org.algotithmcontestdatacollect.managebackend.Utils.UpdateColumnUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    public NormalUserRepository normalUserRepository;
    @Autowired
    public UserService userService;

    @GetMapping("/api/user")
    public String getUser(HttpServletRequest request, HttpServletResponse response) {
        List<NormalUser> userEntity;
        try {
            if (request.getAttribute("isSuper").equals("1")) {
                userEntity = normalUserRepository.findAll();
            } else {
                userEntity = normalUserRepository.getNormalUserEntitiesBySchool(Long.parseLong((String) request.getAttribute("school")));
            }
        } catch (Exception e) {
            return ResponseUtil.JSONReturn(404, "后端异常");
        }
        for (NormalUser user : userEntity) {
            user.setPassword(null);
        }
        return ResponseUtil.JSONReturn(200, (JSONArray) JSONArray.toJSON(userEntity));
    }

    @PostMapping("/api/addUserBatch")
    public String addUserBatch(@RequestBody String dataString, HttpServletRequest request, HttpServletResponse response) {
        JSONArray data = (JSONArray) JSONArray.parse(dataString);
        try {
            userService.addUserBatch(data);
        } catch (Exception e) {
            return ResponseUtil.JSONReturn(404, "数据异常，请检查用户名是否重名");
        }
        return ResponseUtil.JSONReturn(200, "操作成功");
    }

    @PostMapping("/api/addUser")
    public String addUser(@RequestBody JSONObject user, HttpServletRequest request, HttpServletResponse response) {
        try {
            userService.addUser(user);

        } catch (Exception e) {
            return ResponseUtil.JSONReturn(404, "数据异常,请检查用户名是否重名");
        }
        return ResponseUtil.JSONReturn(200, "操作成功");
    }

    @PostMapping("/api/checkUserExist")
    public String checkUserExist(@RequestBody String dataString, HttpServletRequest request, HttpServletResponse response) {
        JSONArray data = (JSONArray) JSONArray.parse(dataString);
        List<String> existList = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            if (normalUserRepository.existsByUsername(data.getString(i))) {
                existList.add(data.getString(i));
            }
        }
        return ResponseUtil.JSONReturn(200, (JSONArray) JSON.toJSON(existList));
    }

    @DeleteMapping("/api/deleteUser/{uid}")
    public String deleteUser(@PathVariable Long uid) {
        Optional<NormalUser> op = normalUserRepository.findById(uid);
        if (op.isPresent()) {
            normalUserRepository.deleteById(uid);
            return ResponseUtil.JSONReturn(200, "删除成功");
        } else {
            return ResponseUtil.JSONReturn(404, "无此用户");
        }
    }

    @PostMapping("/api/changeUserPassword")
    public String changeUserPassword(@RequestBody JSONObject data, HttpServletRequest request, HttpServletResponse response) {
        var uid = data.getLong("uid");
        var newPassword = data.getString("newPassword");
        Optional<NormalUser> op = normalUserRepository.findById(uid);
        if (op.isPresent()) {
            var u = op.get();
            u.setPassword(newPassword);
            normalUserRepository.save(u);
            normalUserRepository.flush();
            return ResponseUtil.JSONReturn(200, "修改成功");
        } else {
            return ResponseUtil.JSONReturn(404, "无此用户");
        }
    }

    @PostMapping("/api/modifyNormalUserInfo")
    public String modifyInfo(@RequestBody NormalUser data, HttpServletRequest request) {
        var uid = data.getId();
        var user = normalUserRepository.findById(uid);
        if (user.isPresent()) {
            var userEntity = user.get();
//            userEntity.setClassname(data.getString("classname"));
//            userEntity.setYear(data.getInteger("year"));
//            userEntity.setStuNo(data.getString("stuNo"));
//            userEntity.setRealname(data.getString("realname"));
//            userEntity.setStatus(data.getByte("status"));
            BeanUtils.copyProperties(data, userEntity, UpdateColumnUtil.getNullPropertyNames(data));
            try {
                normalUserRepository.saveAndFlush(userEntity);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseUtil.JSONReturn(404, "数据库异常");
            }
            return ResponseUtil.JSONReturn(200, "修改成功");
        }
        return ResponseUtil.JSONReturn(404, "用户不存在");
    }

    @PostMapping("/api/modifyNormalUserStatusBatch")
    public String modifyInfo(@RequestBody JSONObject data) {
        var status = data.getByte("status");
        JSONArray ids = data.getJSONArray("ids");
        List<Long> strings = ids.toJavaList(Long.class);
        List<NormalUser> allById = normalUserRepository.findAllById(strings);
        if (allById.size() > 0) {
            allById.forEach(k -> k.setStatus(status));
            try {
                normalUserRepository.saveAllAndFlush(allById);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseUtil.JSONReturn(404, "数据库异常");
            }
            return ResponseUtil.JSONReturn(200, "修改成功");
        }
        return ResponseUtil.JSONReturn(404, "用户不存在");
    }


}

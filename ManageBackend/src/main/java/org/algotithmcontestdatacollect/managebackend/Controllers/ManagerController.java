package org.algotithmcontestdatacollect.managebackend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;

import org.algotithmcontestdatacollect.managebackend.Repositories.AdminRepository;
import java.util.List;
import org.algotithmcontestdatacollect.managebackend.Entities.Admin;
import org.algotithmcontestdatacollect.managebackend.Utils.ResponseUtil;
import com.alibaba.fastjson.JSONObject;

@RestController
public class ManagerController {
    @Autowired
    AdminRepository adminRepository;
    @GetMapping("/api/admin/manager")
    public String getManager() {
        List<Admin> list = adminRepository.findAll();
        for (Admin admin : list) {
            admin.setPassword(null);
        }
        return ResponseUtil.JSONReturn(200, (JSONArray) JSONArray.toJSON(list));
    }

    @PostMapping("/api/admin/manager")
    public String addManager(@RequestBody JSONObject data) {
        var newData = data.toJavaObject(Admin.class);
        if (newData.getUsername() == null || newData.getPassword() == null || newData.getIsSuper() == null) {
            return ResponseUtil.JSONReturn(404, "参数不足");
        }
        var adminEntity = adminRepository.findByUsername(newData.getUsername());
        if (adminEntity != null) {
            return ResponseUtil.JSONReturn(401, "用户已存在");
        }else{
            adminEntity = adminRepository.save(newData);
        }
        return ResponseUtil.JSONReturn(200,(JSONObject) JSONObject.toJSON(adminEntity));
    }
    @DeleteMapping("/api/admin/manager")
    public String deleteManager(@RequestBody JSONObject data) {
        var id = data.getLong("id");
        if (id == null) {
            return ResponseUtil.JSONReturn(404, "参数不足");
        }
        var adminEntity = adminRepository.findById(id);
        if (adminEntity.isPresent()) {
            adminRepository.delete(adminEntity.get());
        }else{
            return ResponseUtil.JSONReturn(404, "用户不存在");
        }
        return ResponseUtil.JSONReturn(200, "删除成功");
    }
    @PutMapping("/api/admin/manager")
    public String modifyPassword(@RequestBody JSONObject data) {
        var id = data.getLong("id");
        var password = data.getString("password");
        if (id == null || password == null) {
            return ResponseUtil.JSONReturn(404, "参数不足");
        }
        var adminEntity = adminRepository.findById(id);
        if (adminEntity.isPresent()) {
            adminEntity.get().setPassword(password);
            adminRepository.save(adminEntity.get());
        }else{
            return ResponseUtil.JSONReturn(404, "用户不存在");
        }
        return ResponseUtil.JSONReturn(200, "修改成功");
    }
}

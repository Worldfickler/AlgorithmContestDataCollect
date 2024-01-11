package org.algotithmcontestdatacollect.managebackend.Controllers;

import com.alibaba.fastjson.JSONObject;
import org.algotithmcontestdatacollect.managebackend.Entities.Admin;
import org.algotithmcontestdatacollect.managebackend.Repositories.AdminRepository;
import org.algotithmcontestdatacollect.managebackend.Utils.JWTUtil;
import org.algotithmcontestdatacollect.managebackend.Utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Timer;
import java.util.TimerTask;

@RestController
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private JWTUtil JWTutil;

    @PostMapping("/api/login")
    public String login(@RequestBody JSONObject data) {
        logger.info("login:{}", data.getString("username"));
        String username = data.getString("username");
        String password = data.getString("password");
        if (checkExistAndEmpty(username) || checkExistAndEmpty(password)) {
            return ResponseUtil.JSONReturn(404, "参数不足");
        }
        Admin userEntity;
        try {
            userEntity = checkUserNameAndPassword(username, password);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (userEntity == null) {
            return ResponseUtil.JSONReturn(401, "用户名不存在或密码不正确");
        }

        var ret = new JSONObject();
        ret.put("token", JWTutil.createToken(userEntity.toStringMap()));
        var info = (JSONObject) JSONObject.toJSON(userEntity);
        info.remove("password");
        ret.put("info", info);
        return ResponseUtil.JSONReturn(200, ret);
    }

    @PostMapping("/api/rewrite")
    public String rewriteToken(HttpServletRequest request, HttpServletResponse response) {
        var userEntity = Admin.fromHTTPRequest(request);
        var ret = new JSONObject();
        ret.put("token", JWTutil.createToken(userEntity.toStringMap()));
        return ResponseUtil.JSONReturn(200, ret);
    }

    boolean checkExistAndEmpty(String param) {
        return param == null || param.equals("");
    }

    public Admin checkUserNameAndPassword(String username, String password) throws InterruptedException {
        final boolean[] end = {false};
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                end[0] = true;
            }
        }, 250);
        var user = adminRepository.findByUsername(username);
        if (user != null) {
            if (!user.getPassword().equals(password)) {
                user = null;
            }
        }
        while (!end[0]) {
            Thread.sleep(0);
        }
        return user;
    }
}

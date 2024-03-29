package org.algotithmcontestdatacollect.displaybackend.controllers;
import com.alibaba.fastjson.JSONArray;
import org.algotithmcontestdatacollect.displaybackend.entities.NormalUser;
import org.algotithmcontestdatacollect.displaybackend.repositories.UserRepository;
import org.algotithmcontestdatacollect.displaybackend.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class UserDataController {
    @Autowired
    UserRepository userRepository;
    @GetMapping("/api/alluser")
    public String getAllUser() {
        List<NormalUser> l;
        try{
            l = userRepository.findAll();
            for (int i = 0;i<l.size();i++) {
                var user = l.get(i);
                user.setPassword(null);
                l.set(i,user);
            }
        }catch (Exception E){
            return ResponseUtil.JSONReturn(404,"数据库异常");
        }
        return ResponseUtil.JSONReturn(200, (JSONArray) JSONArray.toJSON(l));
    }


}

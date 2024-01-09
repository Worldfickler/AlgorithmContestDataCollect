package org.algotithmcontestdatacollect.displaybackend.controllers;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.algotithmcontestdatacollect.displaybackend.entities.Prize;
import org.algotithmcontestdatacollect.displaybackend.repositories.PrizeRepository;
import org.algotithmcontestdatacollect.displaybackend.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class PrizeController {

    @Autowired
    private PrizeRepository prizeRepository;

    @PostMapping("/user/prize/save")
    public String save(@RequestBody @Valid Prize prize, HttpServletRequest request) {
        long requestUid = Long.parseLong(request.getAttribute("id").toString());
        prize.setUid(requestUid);
        prizeRepository.save(prize).toString();
        return ResponseUtil.JSONReturn(200, "保存成功");
    }

    @PostMapping("/user/prize/update")
    public String update(@RequestBody @Valid Prize prize, HttpServletRequest request) {
        long requestUid = Long.parseLong(request.getAttribute("id").toString());
        if (prize.getUid().longValue()==requestUid) {
            prizeRepository.save(prize);
            return ResponseUtil.JSONReturn(200, "更新成功");
        }else{
            return ResponseUtil.JSONReturn(200, "更新失败");
        }
    }
    /**
     * description:展示可见的奖项
     * author:yjz
     * @param uids
     * @return java.lang.String
     */
    @PostMapping("/prize/getByUids")
    public String getByUids(@RequestBody List<Long> uids) {
        Map<String, Object> collect = new HashMap<>(prizeRepository.findByStatusAndUidIn("1",uids).stream().collect(Collectors.groupingBy(a -> a.getUid().toString())));
        return ResponseUtil.JSONReturn(200, new JSONObject(collect));
    }
    /**
     * description:展示用户的奖项，包括不可见的。
     * author:yjz
     * @param request
     * @return java.lang.String
     */
    @PostMapping("/user/prize/getUserPrize")
    public String getUserPrize(HttpServletRequest request) {
        long requestUid = Long.parseLong(request.getAttribute("id").toString());
        List<Long> longs = new ArrayList<>();
        longs.add(requestUid);
        List<Prize> byUidIn = prizeRepository.findByUidIn(longs);
        return ResponseUtil.JSONReturn(200, new JSONArray(byUidIn));
    }

}

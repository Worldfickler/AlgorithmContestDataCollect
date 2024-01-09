package org.algotithmcontestdatacollect.crawlerdispatcher.HTTPInterface.ControlInterface;

import com.alibaba.fastjson.JSONObject;
import org.algotithmcontestdatacollect.crawlerdispatcher.Utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CrawlerInfoInterface {
    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @GetMapping("/GetAllCrawlerStatus")
    public String getAllCrawlerStatus() {
        var entries = redisTemplate.opsForHash().entries("spiderExpire");
        var ret = new JSONObject();
        for(var entry:entries.entrySet()){
            if(Long.parseLong((String) entry.getValue()) >= System.currentTimeMillis() - 60*1000) {
                var stat = new JSONObject();
                stat.put("lastTime",entry.getValue());
                stat.put("status",JSONObject.parseObject((String) redisTemplate.opsForHash().get("spiderStatus",(String)entry.getKey())));
                ret.put((String) entry.getKey(),stat);
            }
        }
        return ResponseUtil.JSONReturn(200,ret);
    }
}

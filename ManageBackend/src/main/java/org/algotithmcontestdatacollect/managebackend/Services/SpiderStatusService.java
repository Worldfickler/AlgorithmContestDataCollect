package org.algotithmcontestdatacollect.managebackend.Services;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.algotithmcontestdatacollect.managebackend.Repositories.SpiderLogRepository;
import org.algotithmcontestdatacollect.managebackend.Utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SpiderStatusService {
    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Autowired
    SpiderLogRepository spiderLogRepository;
    public JSONObject getAllCrawlerStatus() {
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
        return ret;
    }

    public JSONArray getSpiderLogPage(int page,int pageSize) {
        var data = spiderLogRepository.findAll(PageRequest.of(page,pageSize));
        return (JSONArray) JSONArray.toJSON(data);
    }
}

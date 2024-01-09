package org.algotithmcontestdatacollect.managebackend.Services.ApplicationHandler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.algotithmcontestdatacollect.managebackend.Entities.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StringRecord;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;


@Transactional(propagation = Propagation.REQUIRED)
@Service
public class GetAtcoderContestInfoHandler extends AbstarctHandler{
    @Autowired
    RedisConnectionFactory redisConnectionFactory;
    @Autowired
    Environment environment;
    @Override
    public void Handle(Application entity) throws Exception {
        RedisConnection redisConnection = redisConnectionFactory.getConnection();
        Map<String,String> mp = new HashMap<>();
        mp.put("spider","AtcoderStucontest");
        mp.put("handler","AtcoderStuContestHandler");
        var params = new JSONArray();
        var paramObj = JSONObject.parseObject(entity.getParameter());
        params.add(paramObj.getString("atcoderId"));
        mp.put("params",params.toString());
        StringRecord stringRecord = StringRecord.of(mp).withStreamKey(environment.getProperty("crawlerTask.stream"));
        RecordId recordId = redisConnection.xAdd(stringRecord.serialize(RedisSerializer.string()));

    }
}

package org.algotithmcontestdatacollect.displaybackend.init;


import org.algotithmcontestdatacollect.displaybackend.entities.CodeforcesEntities.CodeforcesOkSubmitWithTags;
import org.algotithmcontestdatacollect.displaybackend.repositories.CfRepository.CodeforcesOkSubmitWithTagsRepository;
import org.algotithmcontestdatacollect.displaybackend.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@Component
public class Init {

    @Autowired
    CodeforcesOkSubmitWithTagsRepository codeforcesOkSubmitWithTagsRepository;
    @Autowired
    RedisUtils redisUtils;

    @PostConstruct
    public void init() {
        long startTime = System.currentTimeMillis();  // 获取开始时间
        List<CodeforcesOkSubmitWithTags> all = codeforcesOkSubmitWithTagsRepository.findAll();
        Map<Long, List<CodeforcesOkSubmitWithTags>> map = all.stream().collect(Collectors.groupingBy(CodeforcesOkSubmitWithTags::getUid));
        map.entrySet().parallelStream().forEach(entry -> {
            Long uid = entry.getKey();
            List<String> pids = entry.getValue().stream()
                    .map(record -> record.getCid() + record.getqIndex())
                    .collect(Collectors.toList());
            redisUtils.storeMapInRedis(uid, pids);
        });
        long endTime = System.currentTimeMillis();  // 获取结束时间
        long duration = endTime - startTime;  // 计算方法执行时间
        System.out.println("redis初始化耗时" + duration);
    }


}

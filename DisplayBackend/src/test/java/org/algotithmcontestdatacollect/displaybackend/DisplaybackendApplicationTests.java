package org.algotithmcontestdatacollect.displaybackend;

import com.alibaba.fastjson.JSONObject;
import org.algotithmcontestdatacollect.displaybackend.entities.CodeforcesEntities.CodeforcesOkSubmitWithTags;
import org.algotithmcontestdatacollect.displaybackend.repositories.CfRepository.CodeforcesOkSubmitWithTagsRepository;
import org.algotithmcontestdatacollect.displaybackend.repositories.UserRepository;
import org.algotithmcontestdatacollect.displaybackend.services.RatingChangeService;
import org.algotithmcontestdatacollect.displaybackend.services.SolveService;
import org.algotithmcontestdatacollect.displaybackend.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
class DisplaybackendApplicationTests {

	@Test
	void contextLoads() {

	}

}

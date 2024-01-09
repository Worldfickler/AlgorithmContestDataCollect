package org.algotithmcontestdatacollect.crawlerdispatcher;

import org.algotithmcontestdatacollect.crawlerdispatcher.Services.UserAccountService;
import org.algotithmcontestdatacollect.crawlerdispatcher.TableEntity.AcAccountWithUsernameEntity;
import org.algotithmcontestdatacollect.crawlerdispatcher.TableEntity.CfAccountWithUsernameEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.util.List;

@SpringBootTest
class CrawlerDispatcherApplicationTests {
//    @Autowired
//    private RedisConnectionFactory redisConnectionFactory;
//    @Autowired
//    private Environment environment;
//    @Autowired
//    UserAccountService userAccountService;

//    @Test
//    void contextLoads() {
//        List<CfAccountWithUsernameEntity> cfAccountWithUsernameEntities = userAccountService.getCfAccounts();
//        for(var account:cfAccountWithUsernameEntities) {
//            System.out.println(account.toString());
//        }
//
//    }
//    @Test
//    void InitTest(){
//
//    }

}

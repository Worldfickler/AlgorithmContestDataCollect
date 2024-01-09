package org.algotithmcontestdatacollect.crawlerdispatcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CrawlerDispatcherApplication {
    public static void main(String[] args) {
        SpringApplication.run(CrawlerDispatcherApplication.class, args);
    }
}

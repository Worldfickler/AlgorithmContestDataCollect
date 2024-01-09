package org.algotithmcontestdatacollect.managebackend.Configure;

import org.algotithmcontestdatacollect.managebackend.GenerateFuncs.IGenerateFunc;
import org.algotithmcontestdatacollect.managebackend.GenerateFuncs.TestGenerateFunc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GenerateFuncConfig {
    @Bean("testFunc")
    IGenerateFunc getTestFunc() {
        return new TestGenerateFunc();
    }
}

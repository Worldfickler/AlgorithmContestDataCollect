package org.algotithmcontestdatacollect.crawlerdispatcher.Dispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service // 为该类创建一个Bean实例，一定要加
public class CodeforcesContestRecordDispatcher extends AbstractDispatcher{
    @Autowired
    private Environment environment;
    @Override
    protected String getSpider() {return "CodeforcesContestRecordSpider";
    }
    @Override
    protected String getHandler() {return "CodeforcesContestRecordHandler";}

    @Override
    protected String getStream() {
        return environment.getProperty("crawlerTask.stream");
    }

}

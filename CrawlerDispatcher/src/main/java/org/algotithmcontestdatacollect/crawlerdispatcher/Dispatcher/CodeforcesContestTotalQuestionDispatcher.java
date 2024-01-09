package org.algotithmcontestdatacollect.crawlerdispatcher.Dispatcher;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class CodeforcesContestTotalQuestionDispatcher extends AbstractDispatcher {
    @Autowired
    Environment environment;
    @Override
    protected String getSpider() {
        return "CodeforcesContestTotalQuestionSpider";
    }

    @Override
    protected String getHandler() {
        return "CodeforcesContestTotalQuestionHandler";
    }

    @Override
    protected String getStream() {
        return environment.getProperty("crawlerTask.stream");
    }
}

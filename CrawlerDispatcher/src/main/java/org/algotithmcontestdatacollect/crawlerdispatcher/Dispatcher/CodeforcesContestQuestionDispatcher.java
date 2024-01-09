package org.algotithmcontestdatacollect.crawlerdispatcher.Dispatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class CodeforcesContestQuestionDispatcher extends AbstractDispatcher {
    @Autowired
    Environment environment;
    @Override
    protected String getSpider() {
        return "CodeforcesContestQuestionSpider";
    }

    @Override
    protected String getHandler() {
        return "CodeforcesContestQuestionHandler";
    }

    @Override
    protected String getStream() {
        return environment.getProperty("crawlerTask.stream");
    }
}


package org.algotithmcontestdatacollect.crawlerdispatcher.HTTPInterface.CronInterface;


import org.algotithmcontestdatacollect.crawlerdispatcher.CronService.CodeforcesCron;
import org.algotithmcontestdatacollect.crawlerdispatcher.Utils.ResponseUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cron/codeforces")
public class CodeforcesCronInterface {
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(CodeforcesCronInterface.class);
    @Autowired
    private CodeforcesCron codeforcesCron;


    @GetMapping("/contestList")
    public String getContestList() {
        try{
            codeforcesCron.contestList();
        }catch (Exception e) {
            LOGGER.error(e.toString());
            return ResponseUtil.JSONReturn(404,"error");
        }
        return ResponseUtil.JSONReturn(200,"ok");
    }

    @GetMapping("/getAllStuContest")
    public String getAllStuContest() {
        try{
            codeforcesCron.getStuContest();
        }catch (Exception e) {
            LOGGER.error(e.toString());
            return ResponseUtil.JSONReturn(404,"error");
        }
        return ResponseUtil.JSONReturn(200,"ok");
    }

    @GetMapping("/getAllStuRecord")
    public String getAllStuRecord() {
        try{
            codeforcesCron.getStuRecord();
        }catch (Exception e) {
            LOGGER.error(e.toString());
            return ResponseUtil.JSONReturn(404,"error");
        }
        return ResponseUtil.JSONReturn(200,"ok");
    }

    @GetMapping("/totalQuestion")
    public String getTotalProblem() {
        try{
            codeforcesCron.totalProblem();
        }catch (Exception e) {
            LOGGER.error(e.toString());
            return ResponseUtil.JSONReturn(404,"error");
        }
        return ResponseUtil.JSONReturn(200,"ok");
    }

    @GetMapping("/get200SubmitCode")
    public String get500SubmitCode() {
        try {
            codeforcesCron.getSubmitCode();
        }catch (Exception e) {
            LOGGER.error(e.toString());
            return ResponseUtil.JSONReturn(404,"error");
        }
        return ResponseUtil.JSONReturn(200,"ok");
    }
}

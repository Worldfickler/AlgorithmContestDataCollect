package org.algotithmcontestdatacollect.managebackend;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.algotithmcontestdatacollect.managebackend.Entities.IntelligentTraining.IntelligentTraining;
import org.algotithmcontestdatacollect.managebackend.Entities.IntelligentTraining.IntelligentTrainingWithStu;
import org.algotithmcontestdatacollect.managebackend.Repositories.AcRepositories.AcContestRepository;
import org.algotithmcontestdatacollect.managebackend.Repositories.AcRepositories.AcStucontestWithCinfoUserinfoRepository;
import org.algotithmcontestdatacollect.managebackend.Repositories.CfRepository.CfContestRepository;
import org.algotithmcontestdatacollect.managebackend.Repositories.CfRepository.CfStucontestWithCinfoUserinfoRepository;
import org.algotithmcontestdatacollect.managebackend.Repositories.FutureContestRepository;
import org.algotithmcontestdatacollect.managebackend.Repositories.IntelligentTraining.IntelligentTrainingRepository;
import org.algotithmcontestdatacollect.managebackend.Repositories.IntelligentTraining.IntelligentTrainingWithStuRepository;
import org.algotithmcontestdatacollect.managebackend.Repositories.NormalUserRepository;
import org.algotithmcontestdatacollect.managebackend.Services.SolveProblemService;
import org.algotithmcontestdatacollect.managebackend.WxBot.WXBotService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

import static org.algotithmcontestdatacollect.managebackend.Utils.TimeUtils.timestampToString;


@SpringBootTest
class ManagebackendApplicationTests {
    @Autowired
    FutureContestRepository futureContestRepository;

    @Value("${allow.WxBot}")
    public String allow;

    @Autowired
    CfStucontestWithCinfoUserinfoRepository cfStucontestWithCinfoUserinfoRepository;
    @Autowired
    AcStucontestWithCinfoUserinfoRepository acStucontestWithCinfoUserinfoRepository;
    @Autowired
    CfContestRepository cfContestRepository;

    @Autowired
    AcContestRepository acContestRepository;
    @Autowired
    IntelligentTrainingWithStuRepository intelligentTrainingWithStuRepository;
    @Autowired
    private NormalUserRepository normalUserRepository;
    @Autowired
    WXBotService wxBotService;
    @Autowired
    private IntelligentTrainingRepository intelligentTrainingRepository;
    @Autowired
    private SolveProblemService solveProblemService;
    Set<Long> cfContestIdExist = new HashSet<>();
    Set<Long> acContestIdExist = new HashSet<>();

    @PostConstruct
    public void init() {
        /**
         * description:tset
         */
        List<Long> cfcs = cfStucontestWithCinfoUserinfoRepository.getExistCid();
        List<Long> accs = acStucontestWithCinfoUserinfoRepository.getExistCid();
        cfContestIdExist.addAll(cfcs);
        acContestIdExist.addAll(accs);
    }
}
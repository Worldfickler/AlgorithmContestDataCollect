package org.algotithmcontestdatacollect.managebackend.WxBot;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.algotithmcontestdatacollect.managebackend.Entities.IntelligentTraining.IntelligentTraining;
import org.algotithmcontestdatacollect.managebackend.Entities.IntelligentTraining.IntelligentTrainingWithStu;
import org.algotithmcontestdatacollect.managebackend.Repositories.*;
import org.algotithmcontestdatacollect.managebackend.Repositories.AcRepositories.AcContestRepository;
import org.algotithmcontestdatacollect.managebackend.Repositories.AcRepositories.AcStucontestWithCinfoUserinfoRepository;
import org.algotithmcontestdatacollect.managebackend.Repositories.CfRepository.CfContestRepository;
import org.algotithmcontestdatacollect.managebackend.Repositories.CfRepository.CfStucontestWithCinfoUserinfoRepository;
import org.algotithmcontestdatacollect.managebackend.Repositories.IntelligentTraining.IntelligentTrainingRepository;
import org.algotithmcontestdatacollect.managebackend.Repositories.IntelligentTraining.IntelligentTrainingWithStuRepository;
import org.algotithmcontestdatacollect.managebackend.Services.SolveProblemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

import static org.algotithmcontestdatacollect.managebackend.Utils.TimeUtils.timestampToString;

//@EnableScheduling
//@Service
public class Cron {
    Logger logger = LoggerFactory.getLogger(Cron.class);

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
        List<Long> cfcs = cfStucontestWithCinfoUserinfoRepository.getExistCid();
        List<Long> accs = acStucontestWithCinfoUserinfoRepository.getExistCid();
        cfContestIdExist.addAll(cfcs);
        acContestIdExist.addAll(accs);
    }

    @Scheduled(cron = "0 0 */9 * * *")
    public void broadcastCfStuContest() {
        if ("false".equals(allow)) {
            return;
        }
        List<Long> cfcs = new ArrayList<>(cfContestIdExist);
        List<Long> toBroadcast = cfStucontestWithCinfoUserinfoRepository.getNotInCid(cfcs);

        for (var c : toBroadcast) {
            var op = cfContestRepository.findById(c);
            if (op.isEmpty()) return;
            var cinfo = op.get();
            String header = "<font size=14 >温馨提示:\n" + timestampToString(cinfo.getStartTimeStamp()) + "开始的比赛:" + cinfo.getName() + "结果已出</font>\n\n";
            String tail = "\n\n<font size=14 >如有遗漏请前往[系统内](https://www.buctcoder.com/ACDC/codeforces/contestInfo/" + cinfo.getCid() + ")查看</font>";
            var stucontests = cfStucontestWithCinfoUserinfoRepository.getCfStucontestWithCinfoUserinfoEntitiesByCid(c);
            stucontests.sort((o1, o2) -> o1.gettRank() - o2.gettRank());
            StringBuilder stringBuilder = new StringBuilder();
            for (var stu : stucontests) {
                stringBuilder
                        .append("<font size=14 >")
                        .append(stu.getClassname())
                        .append(stu.getRealname())
                        .append(stu.gettRank())
                        .append("名")
                        .append(stu.getRating())
                        .append("分,")
                        .append("</font>");
                String color;
                String sign = "";
                if (stu.getDiff() > 0) {
                    color = "#dd0000";
                    sign = "+";
                } else
                    color = "#00dd00";
                stringBuilder.append("<font size=14 color=\"" + color + "\">" + sign + stu.getDiff() + "</font>\n");
            }
            wxBotService.sendMarkdown(header + stringBuilder + tail);
            cfContestIdExist.add(c);
        }
    }

    @Scheduled(cron = "0 0 */10 * * *")
    public void broadcastAcStuContest() {
        if ("false".equals(allow)) {
            return;
        }
        List<Long> accs = new ArrayList<>(acContestIdExist);
        List<Long> toBroadcast = acStucontestWithCinfoUserinfoRepository.getNotInCid(accs);

        for (var c : toBroadcast) {
            var op = acContestRepository.findById(c);
            if (op.isEmpty()) return;
            var cinfo = op.get();
            String header = "<font size=14 >温馨提示:\n" + timestampToString(cinfo.getStartTimeStamp()) + "开始的比赛:" + cinfo.getName() + "结果已出</font>\n\n";
            String tail = "\n\n<font size=14 >如有遗漏请前往[系统内](https://www.buctcoder.com/ACDC/atcoder/contestInfo/" + cinfo.getId() + ")查看</font>";
            var stucontests = acStucontestWithCinfoUserinfoRepository.getAcStucontestWithCinfoUserinfoEntitiesByCid(c);
            stucontests.sort((o1, o2) -> o1.gettRank() - o2.gettRank());
            StringBuilder stringBuilder = new StringBuilder();
            for (var stu : stucontests) {
                stringBuilder
                        .append("<font size=14 >")
                        .append(stu.getClassname())
                        .append(stu.getRealname())
                        .append(stu.gettRank())
                        .append("名")
                        .append(stu.getRating())
                        .append("分,")
                        .append("</font>");
                String color;
                String sign = "";
                if (stu.getDiff() > 0) {
                    color = "#dd0000";
                    sign = "+";
                } else
                    color = "#00dd00";
                stringBuilder.append("<font size=14 color=\"" + color + "\">" + sign + stu.getDiff() + "</font>\n");
            }
            wxBotService.sendMarkdown(header + stringBuilder + tail);
            acContestIdExist.add(c);
        }
    }

    @Scheduled(cron = "10 * * * * *")
    public void broadcastNear() {
        if ("false".equals(allow)) {
            return;
        }
        var futures = futureContestRepository.findAll();
        var currentTime = System.currentTimeMillis();
        String title = "温馨提示:\n";
        for (var c : futures) {
            String contestUrl = "";
            if ("cf".equals(c.getType())) {
                contestUrl = "[" + c.getName() + "]" + "(https://codeforces.com/contest/" + c.getId() + ")";
            } else if ("ac".equals(c.getType())) {
                contestUrl = "[" + c.getName() + "]" + "(https://atcoder.jp/contests/" + c.getNickname() + ")";
            }
            var twoHour = c.getStartTimeStamp() * 1000 - 2 * 60 * 60 * 1000;
            if (twoHour - 60 * 1000 <= currentTime && currentTime < twoHour) {
                String detail = "比赛: " + contestUrl + "还有两小时开始";
                wxBotService.sendMarkdown(title + detail);
            }
            if (c.getStartTimeStamp() * 1000 - 60 * 1000 <= currentTime && currentTime < c.getStartTimeStamp() * 1000) {
                String detail = "比赛: " + contestUrl + "正在开始";
                wxBotService.sendMarkdown(title + detail);
            }
        }
    }

    @Scheduled(cron = "0 0 8 * * *")
    public void broadcastForeContest() {
        if ("false".equals(allow)) {
            return;
        }
        var futures = futureContestRepository.findAll();
        futures.sort((o1, o2) -> Long.compare(o1.getStartTimeStamp(), o2.getStartTimeStamp()));
        StringBuilder futureContest = new StringBuilder();
        for (var c : futures) {
            if (c.getStartTimeStamp() * 1000 - System.currentTimeMillis() < 3 * 24 * 60 * 60 * 1000) {
                futureContest.append(timestampToString(c.getStartTimeStamp() * 1000));
                futureContest.append("-");
                futureContest.append(c.getName());
                var url = "";
                if (Objects.equals(c.getType(), "ac")) {
                    url = "https://atcoder.jp/contests/" + c.getNickname();
                } else {
                    url = "https://codeforces.com/contests";
                }
                futureContest.append("  [点此进入](").append(url).append(")");
                futureContest.append("\n");
            }
        }

        if (!"".equals(futureContest.toString())) {
            String title = "温馨提示:近七天的比赛有以下几场";
            wxBotService.sendMarkdown(title + "\n" + futureContest);
        }

    }

    @Scheduled(cron = "0 0 18 * * *")
    public void broadcastIntelligentTrainingSolvedNum() {
        if ("false".equals(allow)) {
            return;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Date current = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date beforeDay = calendar.getTime();
        List<IntelligentTraining> all = intelligentTrainingRepository.findByStartTimestampLessThanEqualAndEndTimestampGreaterThanEqual(current, beforeDay);
        for (int i = 0; i < all.size(); i++) {
            IntelligentTraining intelligentTraining = all.get(i);
            var uList = intelligentTrainingWithStuRepository.getIntelligentTrainingWithStuEntitiesByTid(intelligentTraining.getId());
            List<Long> stuList = new ArrayList<>();
            for (var u : uList) {
                stuList.add(u.getUid());
            }
            var res = solveProblemService.getCodeforcesSolveProblemCountByUserIdsAndStartTimeAndEndTime(
                    stuList,
                    intelligentTraining.getStartTimestamp().getTime() / 1000,
                    intelligentTraining.getEndTimestamp().getTime() / 1000
            );
            ArrayList<IntelligentTrainingWithStu> temp = new ArrayList<>();
            for (int j = 0; j < uList.size(); j++) {
                IntelligentTrainingWithStu intelligentTrainingWithStu = uList.get(j);
                List<String> expectation = JSONArray.parseArray(intelligentTrainingWithStu.getProblems(), String.class);
                String s = res.getOrDefault(intelligentTrainingWithStu.getUid(), "");
                List<String> split = List.of(s.split(","));
                expectation.retainAll(split);
                intelligentTrainingWithStu.setProblems(new JSONArray(expectation).toString());
                temp.add(intelligentTrainingWithStu);
            }
            temp.sort((o1, o2) -> JSONArray.parseArray(o2.getProblems()).size() - JSONArray.parseArray(o1.getProblems()).size());
            String header = "<font size=16 >温馨提示:\n" + intelligentTraining.getName() + "今日完成情况</font>\n\n";
            StringBuilder stringBuilder = new StringBuilder();
            for (int k = 0; k < temp.size(); k++) {
                stringBuilder
                        .append("<font size=16 >")
                        .append(temp.get(k).getClassname() + " ")
                        .append(temp.get(k).getRealname() + " ")
                        .append(JSONArray.parseArray(temp.get(k).getProblems()).size())
                        .append("</font>\n");
            }
            String tail = "\n\n<font size=16 >如有遗漏请前往[系统内](https://www.buctcoder.com/ACDC/intelligent_training/contest/rank/" + intelligentTraining.getId() + ")查看</font>";
            if (stringBuilder.length() != 0) {
                wxBotService.sendMarkdown(header + stringBuilder + tail);
            }
        }
    }

    /**
     * description:每晚8点查看当前比赛是否过期 如果过期设置上总人数，总解题数==，如果没过期则总人数总解题数实时计算。test123
     * author:yjz
     *
     * @return void
     */
    @Scheduled(cron = "0 0 20 * * *")
    public void setIntelligentTrainingInfo() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Date current = calendar.getTime();
        /**
         * description:找到大于当前时间并且没处理过的比赛
         */
        List<IntelligentTraining> intelligentTrainingList = intelligentTrainingRepository.findBysRatingIsNullAndEndTimestampLessThanEqual(current);
        for (int i = 0; i < intelligentTrainingList.size(); i++) {
            IntelligentTraining temp = intelligentTrainingList.get(i);
            var uList = intelligentTrainingWithStuRepository.getIntelligentTrainingWithStuEntitiesByTid(temp.getId());
            int sNum = 0;
            long sRating = 0;
            List<Long> stuList = new ArrayList<>();
            for (var u : uList) {
                stuList.add(u.getUid());
            }
            var res = solveProblemService.getCodeforcesSolveProblemCountByUserIdsAndStartTimeAndEndTime(
                    stuList,
                    temp.getStartTimestamp().getTime() / 1000,
                    temp.getEndTimestamp().getTime() / 1000
            );
            JSONArray jsonArray = uList.stream().map(a -> a.getProblems()).map(JSONArray::parseArray).reduce((s1, s2) -> s1.fluentAddAll(s2)).orElse(new JSONArray());
            List<String> strings = JSONObject.parseArray(jsonArray.toString(), String.class);
            HashSet<String> problems = new HashSet<>(strings);
            Map<String, Integer> problemMap = solveProblemService.findProblems(problems).stream().collect(Collectors.toMap(
                    a -> a.getCid() + a.getQindex(),
                    a -> a.getDifficulty()
            ));
            for (int j = 0; j < uList.size(); j++) {
                IntelligentTrainingWithStu intelligentTrainingWithStu = uList.get(j);
                List<String> expectation = JSONArray.parseArray(intelligentTrainingWithStu.getProblems(), String.class);
                String s = res.getOrDefault(intelligentTrainingWithStu.getUid(), "");
                List<String> split = List.of(s.split(","));
                expectation.retainAll(split);
                sNum += expectation.size();
                for (int i1 = 0; i1 < expectation.size(); i1++) {
                    sRating += problemMap.get(expectation.get(i1));
                }
            }
            temp.setSRating(sRating);
            temp.setPNum(uList.size());
            temp.setSNum(sNum);
        }
        if (intelligentTrainingList.size() > 0) {
            intelligentTrainingRepository.saveAll(intelligentTrainingList);
        }
    }


}

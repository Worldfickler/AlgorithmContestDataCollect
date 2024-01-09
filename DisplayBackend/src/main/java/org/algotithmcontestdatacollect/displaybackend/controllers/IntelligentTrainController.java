package org.algotithmcontestdatacollect.displaybackend.controllers;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import org.algotithmcontestdatacollect.displaybackend.entities.CodeforcesEntities.CodeforcesProblemsWithTags;
import org.algotithmcontestdatacollect.displaybackend.entities.IntelligentTraining.*;
import org.algotithmcontestdatacollect.displaybackend.entities.NormalUser;
import org.algotithmcontestdatacollect.displaybackend.generateFuncs.IGenerateFunc;
import org.algotithmcontestdatacollect.displaybackend.repositories.CfRepository.CodeforcesProblemsRepository;
import org.algotithmcontestdatacollect.displaybackend.repositories.CfRepository.CodeforcesProblemsWithTagsRepository;
import org.algotithmcontestdatacollect.displaybackend.repositories.IntelligentTraining.*;
import org.algotithmcontestdatacollect.displaybackend.repositories.UserRepository;
import org.algotithmcontestdatacollect.displaybackend.services.SolveProblemService;
import org.algotithmcontestdatacollect.displaybackend.utils.RedisUtils;
import org.algotithmcontestdatacollect.displaybackend.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
public class IntelligentTrainController {
    private final Logger logger = LoggerFactory.getLogger(IntelligentTrainController.class);
    @Autowired
    private IntelligentTrainingRepository intelligentTrainingRepository;

    @Autowired
    private IntelligentTrainingQuestionsRepository intelligentTrainingQuestionsRepository;

    @Autowired
    private IntelligentTrainingStrategyRepository intelligentTrainingStrategyRepository;

    @Autowired
    private CodeforcesProblemsWithTagsRepository codeforcesProblemsWithTagsRepository;

    @Autowired
    private SolveProblemService solveProblemService;

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private IntelligentTrainingQuestionClickTimeRepository intelligentTrainingQuestionClickTimeRepository;


    @Autowired
    private IntelligentTrainingGenerateFunctionRepository intelligentTrainingGenerateFunctionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    IntelligentTrainingWithStuRepository intelligentTrainingWithStuRepository;
    @GetMapping("/api/intelligentTrain")
    public String intelligentTrain() {
        List<IntelligentTraining> intelligentTrainingList = intelligentTrainingRepository.findAll();
        /**
         * description:如果没设置则实时计算
         */
        for (int i = 0; i < intelligentTrainingList.size(); i++) {
            IntelligentTraining temp = intelligentTrainingList.get(i);
            if(temp.getPNum()!=null){
                continue;
            }
            var uList = intelligentTrainingWithStuRepository.getIntelligentTrainingWithStuEntitiesByTid(temp.getId());
            int sNum=0;
            long sRating=0;
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
                sNum+=expectation.size();
                for (int i1 = 0; i1 < expectation.size(); i1++) {
                    sRating+=problemMap.get(expectation.get(i1));
                }
            }
            temp.setSRating(sRating);
            temp.setPNum(uList.size());
            temp.setSNum(sNum);
        }
        var ret = (JSONArray) JSONArray.toJSON(intelligentTrainingList);
        return ResponseUtil.JSONReturn(200, ret);
    }


    @GetMapping("/api/intelligentTrain/contestInfo/{id}")
    public String contestInfo(@PathVariable("id") Integer id){
        var ret = intelligentTrainingRepository.findById(id);
        if(ret.isEmpty()){
            return ResponseUtil.JSONReturn(404,"不存在该比赛");
        }
        var uList = intelligentTrainingQuestionsRepository.getIntelligentTrainingQuestionsEntitiesByTid(id);
        JSONArray jsonArray = uList.stream().map(a -> a.getProblems()).map(JSONArray::parseArray).reduce((s1, s2) -> s1.fluentAddAll(s2)).orElse(new JSONArray());
        List<String> strings = JSONObject.parseArray(jsonArray.toString(), String.class);
        HashSet<String> problems = new HashSet<>(strings);
        List<Long> stuList = new ArrayList<>();
        for (var u : uList) {
            stuList.add(u.getUid());
        }
        var res = solveProblemService.getCodeforcesSolveProblemCountByUserIdsAndStartTimeAndEndTime(
                stuList,
                ret.get().getStartTimestamp().getTime() / 1000,
                ret.get().getEndTimestamp().getTime()/1000
        );
        var strategyInfo = intelligentTrainingStrategyRepository.findById(ret.get().getStrategyId());
        if(strategyInfo.isEmpty()){
            return ResponseUtil.JSONReturn(404,"比赛策略不存在");
        }
        List<NormalUser> byIds = userRepository.findByIds(stuList);
        Map<Long, NormalUser> collect = byIds.stream().collect(Collectors.toMap(NormalUser::getId, user -> {
            user.setPassword(null);
            user.setSchool(null);
            user.setStuNo(null);
            user.setYear(null);
            user.setStatus(null);
            return user;
        }));
        var retJson = new JSONObject();
        JSONArray userDetail=new JSONArray();
        for (int i = 0; i < uList.size(); i++) {
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(uList.get(i));
            jsonObject.put("uid",collect.get(jsonObject.getLong("uid")).getId());
            jsonObject.put("classname",collect.get(jsonObject.getLong("uid")).getClassname());
            jsonObject.put("realname",collect.get(jsonObject.getLong("uid")).getRealname());
            jsonObject.put("username",collect.get(jsonObject.getLong("uid")).getUsername());
            userDetail.add(jsonObject);
        }
        Map<String, Integer> problemMap = solveProblemService.findProblems(problems).stream().collect(Collectors.toMap(
                a -> a.getCid() + a.getQindex(),
                a -> a.getDifficulty()
        ));
        retJson.put("problemMap",problemMap);
        retJson.put("userInfo", userDetail);
        retJson.put("contestInfo", ret.get());
        retJson.put("solveProblemInfo", JSONObject.toJSON(res));
        retJson.put("strategyInfo", strategyInfo.get());
        return ResponseUtil.JSONReturn(200,retJson);
    }
    @GetMapping("/api/intelligentTrain/contestSubmitInfo/{id}")
    public String contestSubmitInfo(@PathVariable("id") Integer id){
        var ret = intelligentTrainingRepository.findById(id);
        if(ret.isEmpty()){
            return ResponseUtil.JSONReturn(404,"不存在该比赛");
        }
        var retJson = new JSONObject();
        var uList = intelligentTrainingQuestionsRepository.getIntelligentTrainingQuestionsEntitiesByTid(id);
        JSONArray res = solveProblemService.getIntelligentTrainingSubmitInfoByUidsAndStartAndEnd(uList,
                ret.get().getStartTimestamp().getTime() / 1000,
                ret.get().getEndTimestamp().getTime()/1000);
        retJson.put("contestInfo", ret.get());
        retJson.put("submitInfo", res);
        return ResponseUtil.JSONReturn(200,retJson);
    }

    @GetMapping("/api/intelligentTrain/funcs")
    public String getFuncs() {
        var beanNames = Arrays.asList(applicationContext.getBeanNamesForType(IGenerateFunc.class));
        List<IntelligentTrainingGenerateFunction> names = intelligentTrainingGenerateFunctionRepository.getFuncNameByNameList(beanNames);
        var ret = (JSONArray) JSONArray.toJSON(names);
        return ResponseUtil.JSONReturn(200, ret);
    }

    @GetMapping("/api/intelligentTrain/getUserQuestionList/{id}")
    public String getUserQuestionList(@PathVariable("id") Long id) {
        var ret = intelligentTrainingQuestionsRepository.getIntelligentTrainingQuestionsEntitiesByUid(id);
        var retJson = (JSONArray) JSONArray.toJSON(ret);
        return ResponseUtil.JSONReturn(200, retJson);
    }

    @GetMapping("/api/intelligentTrain/getUserQuestionListDetail/{uid}/{tid}")
    public String getUserQuestionListDetail(@PathVariable("uid") Long uid, @PathVariable("tid") Integer tid) {
        var res = intelligentTrainingQuestionsRepository.getIntelligentTrainingQuestionsEntityByUidAndTid(uid, tid);
        String problemJSONStr = res.getProblems();
        String generateIds = res.getGenerateIds();
        List<String> problemList = JSONArray.parseArray(problemJSONStr, String.class);
        List<String> generateIdList = JSONArray.parseArray(generateIds, String.class);
        var retList = new JSONArray();
        Pattern r = Pattern.compile("(\\d*)([A-Z]+[1-9]?)");
        for (int i = 0; i < problemList.size(); i++) {
            String problemId = problemList.get(i);
            Matcher m = r.matcher(problemId);
            m.find();
            long contestId = Long.parseLong(m.group(1));
            String index = m.group(2);
            CodeforcesProblemsWithTags problem = codeforcesProblemsWithTagsRepository.getCodeforcesProblemsWithTagsEntityByCidAndQindex(contestId, index);
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(problem));
            jsonObject.put("generateName",generateIdList.get(i));
            retList.add(jsonObject);
        }
        return ResponseUtil.JSONReturn(200, retList);
    }

    @PostMapping("/api/user/intelligentTrain/addUserQuestionList/{tid}")
    public String joinTraining(@PathVariable Integer tid, HttpServletRequest request) {
        var uid = Long.parseLong((String) request.getAttribute("id"));
        var training = intelligentTrainingRepository.findById(tid);
        if (training.isEmpty()) {
            return ResponseUtil.JSONReturn(404, "不存在该比赛");
        }
        var sid = training.get().getStrategyId();
        var strategy = intelligentTrainingStrategyRepository.findById(sid);
        if (strategy.isEmpty()) {
            return ResponseUtil.JSONReturn(404, "不存在该策略");
        }
        var useFuncs = strategy.get().getUseFunction();
        var problemList = new JSONArray();
        var generate_ids = new JSONArray();
        var funcList = JSONObject.parseArray(useFuncs, JSONObject.class);
        List<String> doneProblems = redisUtils.getValuesFromRedis(uid);
        ArrayList<String> selectedProblems = new ArrayList<>();
        for(var func :funcList){
            var geneFunc = applicationContext.getBean(func.getString("funcName"),IGenerateFunc.class);
            int num = func.getInteger("num");
            HashMap<String, String> generate = geneFunc.generate(num, uid, doneProblems, selectedProblems);
            if(null==generate) {
                return ResponseUtil.JSONReturn(404, "请添加cf账号");
            }
            problemList.addAll(generate.keySet());
            generate_ids.addAll(generate.values());
        }
        var newItem = new IntelligentTrainingQuestions();
        newItem.setTid(tid);
        newItem.setUid(uid);
        newItem.setProblems(problemList.toString());
        newItem.setGenerateIds(generate_ids.toString());
        intelligentTrainingQuestionsRepository.save(newItem);
        return ResponseUtil.JSONReturn(200, "添加成功");
    }

    @GetMapping("/api/user/intelligentTrain/func/{funcName}/{num}")
    public String useFuncs(@PathVariable String funcName,@PathVariable Integer num, HttpServletRequest request){
        var uid = Long.parseLong((String) request.getAttribute("id"));
        var func = (IGenerateFunc) applicationContext.getBean(funcName,IGenerateFunc.class);
        List<String> valuesFromRedis = redisUtils.getValuesFromRedis(uid);
        HashMap<String, String> ret = func.generate(num, uid, valuesFromRedis, new ArrayList<>());
        var retList = new JSONArray();
        Pattern r = Pattern.compile("(\\d*)([A-Z]+[1-9]?)");
        for (var problemId : ret.keySet()) {
            Matcher m = r.matcher(problemId);
            m.find();
            var contestId = Long.parseLong(m.group(1));
            var index = m.group(2);
            var problem = codeforcesProblemsWithTagsRepository.getCodeforcesProblemsWithTagsEntityByCidAndQindex(contestId, index);
            retList.add(JSON.toJSON(problem));
        }
        return ResponseUtil.JSONReturn(200, retList);
    }

    @PostMapping("/api/user/intelligentTrain/addQuestionClickTime/{cid}/{qIndex}/{generateName}")
    public String addQuestionClickTime(@PathVariable Long cid,@PathVariable String qIndex,@PathVariable String generateName, HttpServletRequest request) {
        var uid = Long.parseLong((String) request.getAttribute("id"));
        /**
         * description:无论如何点击了这个链接都往数据库中插入一条数据，这样能够最大化获取点击时间这个参数。
         * author:yjz
         * @return java.lang.String
         */
        intelligentTrainingQuestionClickTimeRepository.save(new IntelligentTrainingQuestionClickTime(uid,cid,qIndex,new Date(),generateName));
        return ResponseUtil.JSONReturn(200, "添加成功");
    }
}

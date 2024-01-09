package org.algotithmcontestdatacollect.managebackend.Controllers;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.algotithmcontestdatacollect.managebackend.Entities.IntelligentTraining.IntelligentTraining;
import org.algotithmcontestdatacollect.managebackend.Entities.IntelligentTraining.IntelligentTrainingGenerateFunction;
import org.algotithmcontestdatacollect.managebackend.Entities.IntelligentTraining.IntelligentTrainingStrategy;
import org.algotithmcontestdatacollect.managebackend.GenerateFuncs.IGenerateFunc;
import org.algotithmcontestdatacollect.managebackend.Repositories.IntelligentTraining.IntelligentTrainingGenerateFunctionRepository;
import org.algotithmcontestdatacollect.managebackend.Repositories.IntelligentTraining.IntelligentTrainingQuestionsRepository;
import org.algotithmcontestdatacollect.managebackend.Repositories.IntelligentTraining.IntelligentTrainingRepository;
import org.algotithmcontestdatacollect.managebackend.Repositories.IntelligentTraining.IntelligentTrainingStrategyRepository;
import org.algotithmcontestdatacollect.managebackend.Services.SolveProblemService;
import org.algotithmcontestdatacollect.managebackend.Utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class IntelligentTrainController {

    @Autowired
    private IntelligentTrainingRepository intelligentTrainingRepository;

    @Autowired
    private IntelligentTrainingQuestionsRepository intelligentTrainingQuestionsRepository;

    @Autowired
    private IntelligentTrainingStrategyRepository intelligentTrainingStrategyRepository;

    @Autowired
    private SolveProblemService solveProblemService;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private IntelligentTrainingGenerateFunctionRepository intelligentTrainingGenerateFunctionRepository;

    @GetMapping("/api/intelligentTrain")
    public String intelligentTrain(){
        List<IntelligentTraining> list = intelligentTrainingRepository.findAll();
        var ret = (JSONArray) JSONArray.toJSON(list);
        return ResponseUtil.JSONReturn(200,ret);
    }
    @PostMapping("/api/intelligentTrain")
    public String addContest(@RequestBody JSONObject data) {
        var contest = new IntelligentTraining();
        contest.setName(data.getString("name"));
        contest.setStrategyId(data.getInteger("strategyId"));
        contest.setStartTimestamp(new Timestamp(data.getLong("startTimestamp")));
        contest.setEndTimestamp(new Timestamp(data.getLong("endTimestamp")));
        try{
            var c = intelligentTrainingRepository.save(contest);
            return ResponseUtil.JSONReturn(200,"添加成功");
        }catch (Exception e){
            return ResponseUtil.JSONReturn(500,"添加失败");
        }
    }
    @DeleteMapping("/api/intelligentTrain/{id}")
    public String deleteContest(@PathVariable("id") Integer id){
        try{
            intelligentTrainingRepository.deleteById(id);
            return ResponseUtil.JSONReturn(200,"删除成功");
        }catch (Exception e){
            return ResponseUtil.JSONReturn(500,"删除失败");
        }
    }


    @GetMapping("/api/intelligentTrain/contestInfo/{id}")
    public String contestInfo(@PathVariable("id") Integer id){
        var ret = intelligentTrainingRepository.findById(id);
        if(ret.isEmpty()){
            return ResponseUtil.JSONReturn(404,"不存在该比赛");
        }
        var uList = intelligentTrainingQuestionsRepository.getIntelligentTrainingQuestionsEntitiesByTid(id);
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
        var retJson = new JSONObject();
        retJson.put("userInfo",(JSONArray) JSONArray.toJSON(uList));
        retJson.put("contestInfo",ret.get());
        retJson.put("solveProblemInfo",JSONObject.toJSON(res));
        retJson.put("strategyInfo",strategyInfo.get());
        return ResponseUtil.JSONReturn(200,retJson);
    }

    @GetMapping("/api/intelligentTrain/strategy")
    public String getStrategy(){
        var strategies =  intelligentTrainingStrategyRepository.findAll();
        var ret = (JSONArray) JSONArray.toJSON(strategies);
        return ResponseUtil.JSONReturn(200,ret);
    }

    @PostMapping("/api/intelligentTrain/strategy")
    public String addStrategy(@RequestBody JSONObject data){
        var name = data.getString("name");
        var useFunc = data.getJSONArray("useFunc");
        var strategyEntity = new IntelligentTrainingStrategy();
        strategyEntity.setUseFunction(useFunc.toString());
        strategyEntity.setStrategyName(name);
        intelligentTrainingStrategyRepository.save(strategyEntity);
        return ResponseUtil.JSONReturn(200,"添加成功");
    }

//    @PutMapping("/api/intelligentTrain/strategy/{id}")
//    public String updateStrategy(@PathVariable("id") Integer id,@RequestBody JSONObject data){
//        var useFunc = data.getJSONArray("useFunc");
//        var strategyEntity = intelligentTrainingStrategyRepository.findById(id);
//        if(strategyEntity.isEmpty()){
//            return ResponseUtil.JSONReturn(404,"不存在该策略");
//        }
//        var rel = strategyEntity.get();
//        rel.setUseFunction(useFunc.toString());
//        intelligentTrainingStrategyRepository.save(rel);
//        return ResponseUtil.JSONReturn(200,"修改成功");
//    }
    @DeleteMapping("/api/intelligentTrain/strategy/{id}")
    public String deleteStrategy(@PathVariable("id") Integer id){
        var strategyEntity = intelligentTrainingStrategyRepository.findById(id);
        if(strategyEntity.isEmpty()){
            return ResponseUtil.JSONReturn(404,"不存在该策略");
        }
        intelligentTrainingStrategyRepository.deleteById(id);
        return ResponseUtil.JSONReturn(200,"删除成功");
    }



    @GetMapping("/api/intelligentTrain/funcs")
    public String getFuncs(){
        var beanNames = Arrays.asList(applicationContext.getBeanNamesForType(IGenerateFunc.class));
        List<IntelligentTrainingGenerateFunction> names = intelligentTrainingGenerateFunctionRepository.getFuncNameByNameList(beanNames);
        var ret = (JSONArray) JSONArray.toJSON(names);
        return ResponseUtil.JSONReturn(200,ret);
    }

    @PostMapping("/api/intelligentTrain/funcs")
    public String addFunc(@RequestBody JSONObject data){
        var name = data.getString("name");
        var desc = data.getString("desc");
        var func = new IntelligentTrainingGenerateFunction();
        func.setName(name);
        func.setDescription(desc);
        intelligentTrainingGenerateFunctionRepository.save(func);
        return ResponseUtil.JSONReturn(200,"添加成功");
    }
    @DeleteMapping("/api/intelligentTrain/funcs/{id}")
    public String deleteFunc(@PathVariable("id") Integer id){
        var func = intelligentTrainingGenerateFunctionRepository.findById(id);
        if(func.isEmpty()){
            return ResponseUtil.JSONReturn(404,"不存在该函数");
        }
        intelligentTrainingGenerateFunctionRepository.deleteById(id);
        return ResponseUtil.JSONReturn(200,"删除成功");
    }






}
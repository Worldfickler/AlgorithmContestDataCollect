package org.algotithmcontestdatacollect.displaybackend.generateFuncs;

import org.algotithmcontestdatacollect.displaybackend.entities.CodeforcesEntities.CodeforcesProblemsWithTags;
import org.algotithmcontestdatacollect.displaybackend.entities.IntelligentTraining.CfProblemNoTag;
import org.algotithmcontestdatacollect.displaybackend.repositories.CfRepository.CodeforcesOkSubmitWithTagsRepository;
import org.algotithmcontestdatacollect.displaybackend.repositories.CfRepository.CodeforcesProblemsWithTagsRepository;
import org.algotithmcontestdatacollect.displaybackend.repositories.IntelligentTraining.CfProblemNoTagRepository;
import org.algotithmcontestdatacollect.displaybackend.repositories.IntelligentTraining.NoRecommendContestRepository;
import org.algotithmcontestdatacollect.displaybackend.repositories.ProblemTagRepository;
import org.algotithmcontestdatacollect.displaybackend.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service("lessTagSolveBasedGenerateFunc")
public class LessTagSolveBasedGenerateFunc implements IGenerateFunc{
    private static Logger logger = LoggerFactory.getLogger(LessTagSolveBasedGenerateFunc.class);
    @Autowired
    CodeforcesOkSubmitWithTagsRepository codeforcesOkSubmitWithTagsRepository;
    @Autowired
    ProblemTagRepository problemTagRepository;
    @Autowired
    CodeforcesProblemsWithTagsRepository codeforcesProblemsWithTagsRepository;
    @Autowired
    NoRecommendContestRepository noRecommendContestRepository;
    @Autowired
    CfProblemNoTagRepository cfProblemNoTagRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public HashMap<String,String> generate(int n, Long uid,List<String>doneProbles,List<String>selectedProblems) {
        var user = userRepository.findById(uid);
        if(user.isEmpty()) {
            return null;
        }
        var problems = codeforcesOkSubmitWithTagsRepository.getCodeforcesOkSubmitWithTagsEntitiesByUid(uid);
        //没有标签的题目还有不推荐的比赛都不推荐给学生
        Set<Long> collect = noRecommendContestRepository.findAll().stream().map(a -> a.getCid()).collect(Collectors.toSet());
        Map<String, Integer> NoTagHashMap = cfProblemNoTagRepository.findAll().stream().collect(Collectors.toMap(CfProblemNoTag::getCidQindex, CfProblemNoTag::getId));
        var tags = problemTagRepository.findAll();
        var tagMap = new HashMap<String,Integer>();
        for(var tag : tags) {
            tagMap.put(tag.getTagName(),0);
        }
        for(var problem : problems) {
            if(problem.getTags() == null) continue;
            for(var tag : problem.getTags().split(",")) {
                tagMap.put(tag,tagMap.get(tag) + 1);
            }
        }
        // 排序tagMap 并选择最小的n个
        List<Map.Entry<String,Integer>> list=new ArrayList<>(tagMap.entrySet());
        list.sort(Map.Entry.comparingByValue());
        HashMap<String, String> ret = new HashMap<>();
        for(int i = 0;i<n;i++) {
            var nowTag = list.get(i).getKey();
            var selectProblems = codeforcesProblemsWithTagsRepository.getCodeforcesProblemsWithTagsEntitiesByTagsContaining(nowTag);
            ArrayList<CodeforcesProblemsWithTags> filtered = new ArrayList<>();
            for (int j = 0; j < selectProblems.size(); j++) {
                String problemDetail = selectProblems.get(j).getCid() + selectProblems.get(j).getQindex();
                if(collect.contains(selectProblems.get(j).getCid())||NoTagHashMap.containsValue(selectProblems.get(j).getId())||doneProbles.contains(problemDetail)||selectedProblems.contains(problemDetail)){
                    continue;
                }
                filtered.add(selectProblems.get(j));
            }
            int randomElementIndex
                    = ThreadLocalRandom.current().nextInt(filtered.size()) % filtered.size();
            ret.put(filtered.get(randomElementIndex).getCid()+ filtered.get(randomElementIndex).getQindex(),getClass().getSimpleName());
            selectedProblems.add(filtered.get(randomElementIndex).getCid() + filtered.get(randomElementIndex).getQindex());
        }
        return ret;
    }
}

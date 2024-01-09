package org.algotithmcontestdatacollect.displaybackend.generateFuncs;

import org.algotithmcontestdatacollect.displaybackend.entities.CodeforcesEntities.CodeforcesProblemsWithTags;
import org.algotithmcontestdatacollect.displaybackend.entities.IntelligentTraining.CfProblemNoTag;
import org.algotithmcontestdatacollect.displaybackend.repositories.CfRepository.CodeforcesAccountWitheUsernameRepository;
import org.algotithmcontestdatacollect.displaybackend.repositories.CfRepository.CodeforcesProblemsWithTagsRepository;
import org.algotithmcontestdatacollect.displaybackend.repositories.IntelligentTraining.CfProblemNoTagRepository;
import org.algotithmcontestdatacollect.displaybackend.repositories.IntelligentTraining.NoRecommendContestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service("ratingBasedRandomGenerateFunc")
public class RatingBasedRandomGenerateFunc implements IGenerateFunc{
    @Autowired
    CodeforcesAccountWitheUsernameRepository codeforcesAccountWitheUsernameRepository;
    @Autowired
    CodeforcesProblemsWithTagsRepository codeforcesProblemsWithTagsRepository;
    @Autowired
    NoRecommendContestRepository noRecommendContestRepository;
    @Autowired
    CfProblemNoTagRepository cfProblemNoTagRepository;
    @Override
    public HashMap<String,String> generate(int n, Long uid,List<String>doneProbles,List<String>selectedProblems) {
        var user = codeforcesAccountWitheUsernameRepository.getCfAccountWithUsernameEntitiesByUid(uid);
        if(user.size() == 0) {
            return null;
        }
        //没有标签的题目还有不推荐的比赛都不推荐给学生
        Set<Long> collect = noRecommendContestRepository.findAll().stream().map(a -> a.getCid()).collect(Collectors.toSet());
        Map<String, Integer> NoTagHashMap = cfProblemNoTagRepository.findAll().stream().collect(Collectors.toMap(CfProblemNoTag::getCidQindex, CfProblemNoTag::getId));
        int maxRating = 800;
        for(var u : user) {
            int userRating=Optional.ofNullable(u.getRating()).orElse(800);
            maxRating = Math.max(maxRating, userRating);
        }
        var problems = codeforcesProblemsWithTagsRepository.getCodeforcesProblemsWithTagsEntitiesByDifficultyBetween(maxRating,maxRating + 200);
        ArrayList<CodeforcesProblemsWithTags> filtered = new ArrayList<>();
        for (int i = 0; i < problems.size(); i++) {
            String problemDetail = problems.get(i).getCid() + problems.get(i).getQindex();
            if (collect.contains(problems.get(i).getCid())||NoTagHashMap.containsValue(problems.get(i).getId())||doneProbles.contains(problemDetail)||selectedProblems.contains(problemDetail)) {
                continue;
            }
            filtered.add(problems.get(i));
        }
        HashMap<String, String> ret = new HashMap<>();
        for(int i = 0;i<n;i++) {
            int randomElementIndex
                    = ThreadLocalRandom.current().nextInt(filtered.size()) % filtered.size();
            ret.put(filtered.get(randomElementIndex).getCid()+ filtered.get(randomElementIndex).getQindex(),getClass().getSimpleName());
            filtered.remove(randomElementIndex);
        }
        selectedProblems.addAll(ret.keySet());
        return ret;
    }
}

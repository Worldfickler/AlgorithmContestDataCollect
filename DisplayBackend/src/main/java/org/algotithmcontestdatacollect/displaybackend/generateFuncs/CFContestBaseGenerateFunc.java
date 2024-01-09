package org.algotithmcontestdatacollect.displaybackend.generateFuncs;

import org.algotithmcontestdatacollect.displaybackend.entities.CodeforcesEntities.CfProblemsWithContestInfo;
import org.algotithmcontestdatacollect.displaybackend.entities.IntelligentTraining.CfProblemNoTag;
import org.algotithmcontestdatacollect.displaybackend.repositories.CfRepository.*;
import org.algotithmcontestdatacollect.displaybackend.repositories.IntelligentTraining.CfProblemNoTagRepository;
import org.algotithmcontestdatacollect.displaybackend.repositories.IntelligentTraining.NoRecommendContestRepository;
import org.algotithmcontestdatacollect.displaybackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("cfContestBaseGenerateFunc")
public class CFContestBaseGenerateFunc implements IGenerateFunc{
    @Autowired
    CodeforcesAccountWitheUsernameRepository codeforcesAccountWitheUsernameRepository;
    @Autowired
    CodeforcesProblemsWithTagsRepository codeforcesProblemsWithTagsRepository;
    @Autowired
    CfStucontestWithCinfoUserinfoRepository cfStucontestWithCinfoUserinfoRepository;
    @Autowired
    CfContestMaxsolveRepository cfContestMaxsolveRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CfProblemsWithContestInfoRepository cfProblemsWithContestInfoRepository;
    @Autowired
    NoRecommendContestRepository noRecommendContestRepository;
    @Autowired
    RatingBasedRandomGenerateFunc ratingBasedRandomGenerateFunc;
    @Autowired
    CfProblemNoTagRepository cfProblemNoTagRepository;
    @Override
    public HashMap<String,String> generate(int n, Long uid,List<String>doneProbles,List<String>selectedProblems) {
        var user = userRepository.findById(uid);
        if(user.isEmpty()) {
            return null;
        }
        //没有标签的题目还有不推荐的比赛还有学生做过的题目都不推荐给学生
        Map<String, Integer> NoTagHashMap = cfProblemNoTagRepository.findAll().stream().collect(Collectors.toMap(CfProblemNoTag::getCidQindex, CfProblemNoTag::getId));
        Set<Long> collect = noRecommendContestRepository.findAll().stream().map(a -> a.getCid()).collect(Collectors.toSet());

        var entries = cfContestMaxsolveRepository.getCfContestMaxsolveEntitiesByUsernameOrderByStartTimeStampDesc(user.get().getUsername());
        HashMap<String, String> ret = new HashMap<>();
        if(entries.size()==0){
            //如果没参加过比赛则根据积分生成
            return ratingBasedRandomGenerateFunc.generate(n,uid,doneProbles,selectedProblems);
        }
        for(int i = 0;i<n;i++){
            var idx = i % entries.size();
            var entry = entries.get(idx);
            var next = Character.toChars(entry.getMaxSolve().charAt(0) + 1);
            var list = cfProblemsWithContestInfoRepository.getCfProblemsWithContestInfoEntitiesByLevelAndQindex(entry.getLevel(),String.valueOf(next));
            if(list.size() == 0) {
                list = cfProblemsWithContestInfoRepository.getCfProblemsWithContestInfoEntitiesByLevelAndQindex(entry.getLevel() - 1,"A");
            }
            if(list.size() == 0) {
                n++;
                continue;
            }
            ArrayList<CfProblemsWithContestInfo> filtered = new ArrayList<>();
            for (int j = 0; j < list.size(); j++) {
                String problemDetail = list.get(j).getCid() + list.get(j).getQindex();
                if (collect.contains(list.get(j).getCid()) || NoTagHashMap.containsValue(list.get(j).getId()) || doneProbles.contains(problemDetail) || selectedProblems.contains(problemDetail)) {
                    continue;
                }
                filtered.add(list.get(j));
            }
            int randomElementIndex
                    = new Random().nextInt(filtered.size()) % filtered.size();
            var problem = filtered.get(randomElementIndex);
            ret.put(problem.getCid() + problem.getQindex(),getClass().getSimpleName());
            selectedProblems.add(problem.getCid() + problem.getQindex());
        }
        return ret;
    }
}

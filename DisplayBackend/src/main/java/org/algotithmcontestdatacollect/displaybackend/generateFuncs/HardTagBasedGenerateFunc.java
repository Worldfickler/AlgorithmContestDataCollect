package org.algotithmcontestdatacollect.displaybackend.generateFuncs;

import org.algotithmcontestdatacollect.displaybackend.entities.CodeforcesEntities.CodeforcesProblemsWithTags;
import org.algotithmcontestdatacollect.displaybackend.entities.IntelligentTraining.CfProblemNoTag;
import org.algotithmcontestdatacollect.displaybackend.entities.ProblemTag;
import org.algotithmcontestdatacollect.displaybackend.repositories.CfRepository.CodeforcesOkSubmitWithTagsRepository;
import org.algotithmcontestdatacollect.displaybackend.repositories.CfRepository.CodeforcesProblemsWithTagsRepository;
import org.algotithmcontestdatacollect.displaybackend.repositories.IntelligentTraining.CfProblemNoTagRepository;
import org.algotithmcontestdatacollect.displaybackend.repositories.IntelligentTraining.NoRecommendContestRepository;
import org.algotithmcontestdatacollect.displaybackend.repositories.ProblemTagRepository;
import org.algotithmcontestdatacollect.displaybackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("hardTagBasedFunc")
public class HardTagBasedGenerateFunc implements IGenerateFunc {
    @Autowired
    CodeforcesOkSubmitWithTagsRepository codeforcesOkSubmitWithTagsRepository;
    @Autowired
    CodeforcesProblemsWithTagsRepository codeforcesProblemsWithTagsRepository;
    @Autowired
    NoRecommendContestRepository noRecommendContestRepository;
    @Autowired
    ProblemTagRepository problemTagRepository;
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
        var tagMap = new HashMap<String, Integer>();

        for (var problem : problems) {
            if (problem.getTags() == null) continue;
            for (var tag : problem.getTags().split(",")) {
                if (!tagMap.containsKey(tag)) {
                    tagMap.put(tag, 0);
                }
                tagMap.put(tag, Math.max(tagMap.get(tag), problem.getDifficulty()));
            }
        }
        //假设只做过3种专题题目，则无法生成5道题，补充2个专题！
        if (tagMap.size() < n) {
            List<ProblemTag> all = problemTagRepository.findAll();
            int length = all.size();
            for (int j = 0; j < length && tagMap.size() < n; j++) {
                int randomElementIndex
                        = new Random().nextInt(all.size()) % all.size();
                ProblemTag remove = all.remove(randomElementIndex);
                if (tagMap.containsKey(remove.getTagName())) {
                    continue;
                }
                tagMap.put(remove.getTagName(), 0);
            }
        }
        var tagList = new ArrayList<String>(tagMap.keySet());
        HashMap<String, String> ret = new HashMap<>();

        for (int i = 0; i < Math.min(tagList.size(),n); i++) {
            int randomElementIndex
                    = new Random().nextInt(tagList.size()) % tagList.size();
            var nowTag = tagList.get(randomElementIndex);
            var selectProblems = codeforcesProblemsWithTagsRepository.getCodeforcesProblemsWithTagsEntitiesByTagsContainingAndDifficultyGreaterThanEqual(nowTag, tagMap.get(nowTag));
            ArrayList<CodeforcesProblemsWithTags> filtered = new ArrayList<>();
            for (int j = 0; j < selectProblems.size(); j++) {
                String problemDetail = selectProblems.get(j).getCid() + selectProblems.get(j).getQindex();
                if (collect.contains(selectProblems.get(j).getCid())||NoTagHashMap.containsValue(selectProblems.get(j).getId())||doneProbles.contains(problemDetail)||selectedProblems.contains(problemDetail)) {
                    continue;
                }
                filtered.add(selectProblems.get(j));
            }
            randomElementIndex
                    = new Random().nextInt(filtered.size()) % filtered.size();
            ret.put(filtered.get(randomElementIndex).getCid() + filtered.get(randomElementIndex).getQindex(),getClass().getSimpleName());
            selectedProblems.add(filtered.get(randomElementIndex).getCid() + filtered.get(randomElementIndex).getQindex());
        }
        return ret;
    }
}

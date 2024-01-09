package org.algotithmcontestdatacollect.displaybackend.controllers;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSON;
import org.algotithmcontestdatacollect.displaybackend.entities.FutureContest;
import org.algotithmcontestdatacollect.displaybackend.repositories.FutureContestRepository;
import org.algotithmcontestdatacollect.displaybackend.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FutureContestController {

    @Autowired
    FutureContestRepository futureContestRepository;
    @GetMapping("/api/futureContest")
    public String getFutureContest() {
        List<FutureContest> futureContestEntities = futureContestRepository.findAll();
        return ResponseUtil.JSONReturn(200,(JSONArray) JSON.toJSON(futureContestEntities));
    }
}

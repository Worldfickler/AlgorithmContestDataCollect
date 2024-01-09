package org.algotithmcontestdatacollect.managebackend.GenerateFuncs;

import org.algotithmcontestdatacollect.managebackend.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service("cfContestBaseGenerateFunc")
public class CFContestBaseGenerateFunc implements IGenerateFunc{

    // 假模式
    @Override
    public List<String> generate(int n, Long uid) {
        return null;
    }
}

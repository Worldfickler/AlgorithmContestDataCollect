package org.algotithmcontestdatacollect.managebackend.GenerateFuncs;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public class TestGenerateFunc implements IGenerateFunc{
    @Override
    public List<String> generate(int n, Long uid) {
        var repList = new String[]{"1809A", "1809B", "1809C", "1809D"};
        var ret = new ArrayList<String>();
        for(int i = 0; i < n; i++) {
            ret.add(repList[i % repList.length]);
        }
        return ret;
    }
}

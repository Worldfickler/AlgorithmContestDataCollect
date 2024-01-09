package org.algotithmcontestdatacollect.displaybackend.generateFuncs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IGenerateFunc {
    HashMap<String,String> generate(int n, Long uid, List<String>doneProblems,List<String>selectedProblems);
}
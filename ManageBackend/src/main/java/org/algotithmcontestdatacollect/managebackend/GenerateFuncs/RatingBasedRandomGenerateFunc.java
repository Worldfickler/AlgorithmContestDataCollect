package org.algotithmcontestdatacollect.managebackend.GenerateFuncs;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service("ratingBasedRandomGenerateFunc")
public class RatingBasedRandomGenerateFunc implements IGenerateFunc{
    @Override
    public List<String> generate(int n, Long uid) {
        return null;
    }
}

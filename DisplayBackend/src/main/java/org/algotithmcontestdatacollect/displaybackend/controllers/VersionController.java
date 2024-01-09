package org.algotithmcontestdatacollect.displaybackend.controllers;


import com.alibaba.fastjson.JSONArray;
import org.algotithmcontestdatacollect.displaybackend.entities.PublishVersion;
import org.algotithmcontestdatacollect.displaybackend.entities.Version;
import org.algotithmcontestdatacollect.displaybackend.repositories.VersionRepository;
import org.algotithmcontestdatacollect.displaybackend.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;


@RestController
public class VersionController {
    @Autowired
    private VersionRepository repository;

    @GetMapping("/api/version/getAllVersion")
    public String getAllVersion() {
        List<Version> versionEntities = repository.findAll(Sort.by(Sort.Direction.DESC,"vid"));
        PublishVersion publish = null;
        List<PublishVersion> result = new ArrayList<>();
        String temp = "";
        for (int i = 0; i < versionEntities.size(); i++) {
            String vid = versionEntities.get(i).getVid();
            if (!temp.equals(vid)) {
                temp = vid;
                if (publish != null)
                    result.add(publish);
                publish = new PublishVersion(vid, versionEntities.get(i).getpTime(), versionEntities.get(i).getpAdmin());
            }
            publish.getContent().add(versionEntities.get(i));
        }
        result.add(publish);
        return ResponseUtil.JSONReturn(200, (JSONArray) JSONArray.toJSON(result));
    }
}

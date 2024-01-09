package org.algotithmcontestdatacollect.managebackend.Services;

import org.algotithmcontestdatacollect.managebackend.Entities.TagUserMapPK;
import org.algotithmcontestdatacollect.managebackend.Repositories.TagUserMapRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(propagation = Propagation.REQUIRED)
@Service
public class TagService {

    private static final Logger logger = LoggerFactory.getLogger(TagService.class);
    @Autowired
    TagUserMapRepository tagUserMapRepository;

    public void setUpNewTagUser(Long tid,List<Long> uids){
        tagUserMapRepository.deleteAllByTid(tid);
        for(Long uid:uids){
            tagUserMapRepository.insertByUidAndTid(uid,tid);
        }
        tagUserMapRepository.flush();
    }

    public boolean deleteTagUser(Long tid,Long uid) {
        TagUserMapPK pk = new TagUserMapPK();
        pk.setTid(tid);
        pk.setUid(uid);
        if(tagUserMapRepository.existsById(pk)){
            tagUserMapRepository.deleteById(pk);
            return true;
        }
        return false;

    }
}

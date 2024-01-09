package org.algotithmcontestdatacollect.managebackend.Controllers;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.algotithmcontestdatacollect.managebackend.Entities.Tag;
import org.algotithmcontestdatacollect.managebackend.Repositories.*;
import org.algotithmcontestdatacollect.managebackend.Repositories.AcRepositories.AcAccountTotalInfoRepository;
import org.algotithmcontestdatacollect.managebackend.Repositories.CfRepository.CfAccountTotalInfoRepository;
import org.algotithmcontestdatacollect.managebackend.Services.TagService;
import org.algotithmcontestdatacollect.managebackend.Utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TagController {
    private static final Logger logger = LoggerFactory.getLogger(TagController.class);
    @Autowired
    TagUserDetailRepository tagUserDetailRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    TagService tagService;

    @Autowired
    AcAccountTotalInfoRepository acAccountTotalInfoRepository;

    @Autowired
    CfAccountTotalInfoRepository cfAccountTotalInfoRepository;


    @GetMapping("/api/tagList")
    public String getTagList() {
        var tagList = tagRepository.findAll();
        return ResponseUtil.JSONReturn(200,(JSONArray) JSONArray.toJSON(tagList));
    }

    @PostMapping("/api/newtag")
    public String createTag(@RequestBody JSONObject data) {
        var name = data.getString("name");
        Tag newTag = new Tag();
        newTag.setName(name);
        try {
            newTag = tagRepository.saveAndFlush(newTag);
        } catch (Exception e) {
            return ResponseUtil.JSONReturn(401, "创建失败");
        }
        return ResponseUtil.JSONReturn(200,(JSONObject) JSONObject.toJSON(newTag));
    }

    @PostMapping("/api/changeTagName")
    public String changeTagName(@RequestBody JSONObject data) {
        var id = data.getLong("id");
        var newName = data.getString("newName");
        var tag = tagRepository.findById(id);
        if (tag.isEmpty()) {
            return ResponseUtil.JSONReturn(401, "标签不存在");
        }
        var tagEntity = tag.get();
        tagEntity.setName(newName);
        try {
            tagRepository.saveAndFlush(tagEntity);
        } catch (Exception e) {
            return ResponseUtil.JSONReturn(401, "修改失败");
        }
        return ResponseUtil.JSONReturn(200,"修改成功");
    }

    @DeleteMapping("/api/deletetag")
    public String deleteTag(@RequestBody JSONObject data) {
        var id = data.getLong("id");
        try {
            tagRepository.deleteById(id);
        } catch (Exception e) {
            return ResponseUtil.JSONReturn(401, "删除失败");
        }
        return ResponseUtil.JSONReturn(200,"删除成功");
    }


    @GetMapping("/api/tag/{id}/userList")
    public String getTagUserList(@PathVariable Long id) {
        var tagUserList = tagUserDetailRepository.findAllByTid(id);
        return ResponseUtil.JSONReturn(200,(JSONArray) JSONArray.toJSON(tagUserList));
    }

    @PostMapping("/api/tag/{id}/setUserList")
    public String setupTagUserList(@RequestBody JSONObject data,@PathVariable Long id) {
        var jsonArr = data.getJSONArray("userList");
        List<Long> uids = new ArrayList<>();
        for(int i = 0;i<jsonArr.size();i++){
            uids.add(jsonArr.getLong(i));
        }
        try{
            tagService.setUpNewTagUser(id,uids);
        }catch (Exception e){
            logger.error(e.toString());
            return ResponseUtil.JSONReturn(404,"修改失败");
        }
        return ResponseUtil.JSONReturn(200,"修改成功");
    }
    @GetMapping("/api/tag/{id}/totalInfo/ac")
    public String getTagAcTotalInfo(@PathVariable Long id) {
        var result = acAccountTotalInfoRepository.getAcAccountTotalInfoEntitiesByTid(id);
        return ResponseUtil.JSONReturn(200,(JSONArray) JSONArray.toJSON(result));
    }
    @GetMapping("/api/tag/{id}/totalInfo/cf")
    public String getTagCfTotalInfo(@PathVariable Long id) {
        var result = cfAccountTotalInfoRepository.getCfAccountTotalInfoEntitiesByTid(id);
        return ResponseUtil.JSONReturn(200,(JSONArray) JSONArray.toJSON(result));
    }

    @DeleteMapping("/api/tag/deleteByTidAndId")
    public String deleteByTidAndid(@RequestBody JSONObject data) {
        var tid = data.getLong("tid");
        var uid = data.getLong("uid");
        if(tagService.deleteTagUser(tid,uid)) {
            return ResponseUtil.JSONReturn(200,"删除成功");
        }
        return ResponseUtil.JSONReturn(404,"无此用户在该标签中");
    }

}

package org.algotithmcontestdatacollect.managebackend.WxBot;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//@Service
public class WXBotService {
    @Autowired
    RestTemplate restTemplate;
    @Value("${WxBotUrl}")
    String url;

    public void sendInfo(JSONObject info) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(info.toJSONString(), headers);
        restTemplate.postForEntity(url, request, String.class);
    }

    public void sendMarkdown(String detail) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msgtype", "markdown");
        var markdown = new JSONObject();
        markdown.put("content", detail);
        jsonObject.put("markdown", markdown);
        sendInfo(jsonObject);
    }
}

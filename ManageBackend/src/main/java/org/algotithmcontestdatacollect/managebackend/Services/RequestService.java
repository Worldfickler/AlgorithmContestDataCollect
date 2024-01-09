package org.algotithmcontestdatacollect.managebackend.Services;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@Service
public class RequestService {
    @Autowired
    RestTemplate restTemplate;

    public JSONObject Get(URI url) {
        ResponseEntity<JSONObject> responseEntity = restTemplate.getForEntity(url,JSONObject.class);
        return responseEntity.getBody();
    }

    public JSONObject Post(URI url) {
        return this.Post(url,null);
    }

    public JSONObject Post(URI url,Map<String,Object> data) {
        RequestEntity<JSONObject> requestEntity = RequestEntity.post(url).body((JSONObject) JSONObject.toJSON(data));
        return requestEntity.getBody();
    }

}

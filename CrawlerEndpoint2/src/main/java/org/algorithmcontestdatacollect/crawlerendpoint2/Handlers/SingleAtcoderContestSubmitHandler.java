package org.algorithmcontestdatacollect.crawlerendpoint2.Handlers;

import com.alibaba.fastjson.JSONArray;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Pipelines.failPipelines.LoggerFailPipeline;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.RequestParams;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Spider;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("SingleAtcoderContestSubmitHandler")
public class SingleAtcoderContestSubmitHandler implements IHandler{
    private static final Logger logger = LoggerFactory.getLogger(SingleAtcoderContestSubmitHandler.class);
    @Override
    public void handleRequest(Spider spider, String params) {
        JSONArray JSONParams = JSONArray.parseArray(params);
        String atid = JSONParams.getString(0); // 使用JSONArray获取参数
        String nickname = JSONParams.getString(1);
        HashMap<String, String> map = new HashMap<>();
//        map.put("Cookie",getEndCookie(getCsrfAndCookie()));
        spider.addTask(new Task(new RequestParams("https://atcoder.jp/contests/"+nickname+"/submissions?f.User="+atid+"&page=1",map,null)));
    }
//    private static String getEndCookie(HashMap<String,String> map){
//        try {
//            Unirest.setTimeouts(0, 0);
//            HttpResponse<String> response = Unirest.post("https://atcoder.jp/login")
//                    .header("Content-Type", "application/x-www-form-urlencoded")
//                    .header("Cookie", map.get("cookie"))
//                    .field("username", "Yangjize")
//                    .field("password", "1092yang")
//                    .field("csrf_token", map.get("csrf"))
//                    .asString();
//            List<String> strings = response.getHeaders().get("Set-Cookie");
//            return strings.get(1);
//        }catch (Exception e){
//            logger.error("SingleAtcoderContestSubmitHandler异常",e);
//        }
//        return null;
//    }
//    private static HashMap<String,String>getCsrfAndCookie(){
//        HashMap<String, String> map = new HashMap<>();
//        try{
//            Unirest.setTimeouts(0, 0);
//            HttpResponse<String> response = Unirest.get("https://atcoder.jp/")
//                    .asString();
//            map.put("csrf",extractCsrfToken(response.getBody()));
//            map.put("cookie",response.getHeaders().get("Set-Cookie").get(1));
//            return map;
//        }catch (Exception e){
//            logger.error("SingleAtcoderContestSubmitHandler异常",e);
//        }
//        return map;
//    }
//    private static String extractCsrfToken(String javascriptCode) {
//        int startIndex = javascriptCode.indexOf("var csrfToken = \"");
//        int endIndex = javascriptCode.indexOf("\"", startIndex + "var csrfToken = \"".length());
//
//        if (startIndex != -1 && endIndex != -1) {
//            return javascriptCode.substring(startIndex + "var csrfToken = \"".length(), endIndex);
//        }
//
//        return null;
//    }

}

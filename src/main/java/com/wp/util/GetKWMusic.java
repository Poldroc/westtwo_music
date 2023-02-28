package com.wp.util;

import com.alibaba.fastjson.JSONObject;
import com.wp.dao.MusicDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class GetKWMusic {



    public static Object getKWByKey(String key,Integer pn,Integer rn){

    // 要调用的接口方法
    String url =
            "http://www.kuwo.cn/api/www/search/searchMusicBykeyWord?key="+key+"&pn="+pn+"&rn="+rn;

    RestTemplate restTemplate=new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
        headers.add("csrf","m");
        headers.add("Referer","http://www.kuwo.cn");
        headers.add("Cookie","kw_token=m");

    Map<String, Object> requestParam = new HashMap<>();

    HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(requestParam, headers);

    ResponseEntity<JSONObject> entity = restTemplate.exchange(url, HttpMethod.GET, request, JSONObject.class);
    JSONObject body = entity.getBody();

    Object data=(Object)body.get("data");



    //System.out.println(data);

    return data;

   }

}

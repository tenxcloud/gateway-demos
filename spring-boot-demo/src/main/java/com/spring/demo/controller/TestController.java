/*
 * Licensed Materials - Property of tenxcloud.com
 * (C) Copyright 2019 TenxCloud. All Rights Reserved.
 */

package com.spring.demo.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TestController {
	
	@GetMapping("/casetest/{caseParam}")
	public  Map<String, Object> casetest(@PathVariable(value = "caseParam") String caseParam,
			HttpServletRequest request) {
		log.info(request.getHeader("auth"));
		Map<String, Object> map = new HashMap<>();
		if ("order".equals(caseParam)) {
			map.put("result", "success");
			map.put("info", "deal case1 is:"+caseParam);
			return map;
		} else if ("address".equals(caseParam)) {
			map.put("result", "success");
			map.put("info", "deal case2 is:"+caseParam);
			return map;
		}
		log.info("deal case is:"+caseParam);
		map.put("result", "success");
		map.put("info", "deal case other is:"+caseParam);
		return map;
	}
	
	@GetMapping("/dowhiletest/{dowhiletest}")
	public JSONObject dowhiletest(@PathVariable(value = "dowhiletest") Integer dowhiletest,
			HttpServletRequest request) {
		log.info(request.getHeader("auth"));
		JSONObject returnJson = new JSONObject();
		JSONArray dealJson = new JSONArray();
		for (int i = 0; i < dowhiletest; i++) {
			Map<String,Object> map = new HashMap<>();
			map.put("code", i);
			map.put("msg", "deal dowhiletest times is:"+(i+1));
			dealJson.add(map);
		}
		log.info("deal dowhiletest:"+JSONArray.toJSONString(dealJson));
		returnJson.put("result", "success");
		returnJson.put("info",dealJson.get(dowhiletest-1));
		return returnJson;
	}
	
	@GetMapping("/test/{testParam}")
	public  Map<String, Object> forkJoinTest(@PathVariable(value = "testParam") String testParam,
			HttpServletRequest request) {
		log.info(request.getHeader("auth"));
		log.info("testParam:"+testParam);
		Map<String, Object> map = new HashMap<>();
		map.put("result", "success");
		map.put("info", "deal test is:"+testParam);
		return map;
	}
	
	@GetMapping("/test2/{testParam}")
	public  Map<String, Object> forkJoinTest2(@PathVariable(value = "testParam") String testParam,
			HttpServletRequest request,@RequestParam(name = "a1", defaultValue = "a1",required=false) String a1) {
		log.info(request.getHeader("auth"));
		log.info("testParam:"+testParam);
		Map<String, Object> map = new HashMap<>();
		map.put("result", "success");
		map.put("info", "deal test is:"+testParam);
		return map;
	}

}

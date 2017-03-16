package com.sharebo.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
	/**
	 * test/test.do
	 * @param req
	 * @return
	 */
	@RequestMapping("/test")
	public  String test(HttpServletRequest req){
		Map<String, String[]> map=req.getParameterMap();
		for (String m : map.keySet()) {
			System.out.println(map.get(m)[0]);
		}
		return "SUCCESS";
	}
}

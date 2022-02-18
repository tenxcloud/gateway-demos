/*
 * Licensed Materials - Property of tenxcloud.com
 * (C) Copyright 2019 TenxCloud. All Rights Reserved.
 */

package com.spring.demo.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.spring.demo.model.MessageAccept;
import com.spring.demo.model.User;
import com.spring.demo.model.User2;
import com.spring.demo.utils.HashUtils;
import com.spring.demo.utils.HmacSha1Util;
import com.spring.demo.utils.IpUtils;


@RestController
@RequestMapping("user")
public class UserController {

	@GetMapping("hello")
	public @ResponseBody String hello( HttpServletRequest request) {
		System.out.println(request.getHeader("auth"));
		return "hello";
	}

	@GetMapping("hello_hmac_get")
	public @ResponseBody Map<String,String> helloHmacTest( HttpServletRequest request) throws Exception{
		System.out.println(request.getHeader("digest"));
		String authorization = request.getHeader("Authorization");
		System.out.println(authorization);
		Map<String,String> result = new HashMap<>();
		result.put("message", "success");
		result.put("authorization", authorization);
		return result;
	}
	
	@PostMapping("hello_hmac_post")
	public @ResponseBody Map<String,String> helloHmacPostTest(@RequestBody User user, HttpServletRequest request) throws Exception{
		String digest = request.getHeader("digest");
		String authorization = request.getHeader("Authorization");
		System.out.println(digest);
		System.out.println(authorization);
		Map<String,String> result = new HashMap<>();
		result.put("message", "success");
		result.put("authorization", authorization);
		result.put("digest", digest);
		result.put("name", user.getName());
		result.put("age", String.valueOf(user.getAge()));
		return result;
	}
	
	@GetMapping("hello_timeout")
	public @ResponseBody String helloTimeOut( HttpServletRequest request)throws Exception {
		System.out.println(request.getHeader("auth"));
		throw new TimeoutException();
	}
	
	@GetMapping("sleep/{num}")
	public @ResponseBody String hellosleep(@PathVariable(value = "num") Long num,HttpServletRequest request)throws Exception {
		System.out.println(request.getHeader("auth"));
		Thread.sleep(num*1000);
		return "hello";
	}
	
	@GetMapping("hello500")
	public @ResponseBody String hello500( HttpServletRequest request) {
		System.out.println(request.getHeader("auth"));
		int num = 10/0;
		return "hello";
	}
	
	@GetMapping("hello_socket")
	public @ResponseBody String helloScoket( HttpServletRequest request) throws Exception{
		System.out.println(request.getHeader("auth"));
		throw new SocketException();
	}
	
	@GetMapping("testLoad")
	public @ResponseBody Map<String, Object> testLoad( HttpServletRequest request) {
		System.out.println(request.getHeader("auth"));
		Map<String, Object> map = new HashMap<>();
		map.put("result", "success");
		//map.put("ipAddress", ipAddress);
 		map.put("version", "v2"); 
 		System.out.println(JSONObject.toJSONString(map));
		return map;
	}
	
	@GetMapping("/tranform/{testId}")
	public @ResponseBody Map<String, Object> tranformTest1(@PathVariable(value = "testId") String testId,
			@RequestParam(name = "testNum",  required = false) String testNum,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		String auth = request.getHeader("auth");
		String paramTest2 = request.getHeader("paramTest2");
		System.out.println(request.getHeader("auth"));
		String ipAddress = IpUtils.getRealIP(request);
		int port = request.getRemotePort();// 返回发出请求的客户机的端口号。
		String url = request.getRequestURL().toString();
		map.put("result", "success");
		//map.put("ipAddress", ipAddress);
		map.put("testId", testId);  
 		map.put("testNum", testNum);                             
		map.put("auth", auth);
		map.put("paramTest2", paramTest2);
		return map;
	}
		
	@PostMapping("/tranformPost/{testId}")
	public @ResponseBody Map<String, Object> tranformTest2(User user,@PathVariable(value = "testId") String testId,
			@RequestParam(name = "testNum", defaultValue = "testNum", required = false) String testNum,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		String auth = request.getHeader("auth");
		String paramTest2 = request.getHeader("paramTest2");
		System.out.println(request.getHeader("auth"));
		String ipAddress = IpUtils.getRealIP(request);
		int port = request.getRemotePort();// 返回发出请求的客户机的端口号。
		String url = request.getRequestURL().toString();
		map.put("result", "success");
		//map.put("ipAddress", ipAddress);
		map.put("testId", testId);
		map.put("testNum", testNum);
		map.put("auth", auth);
		map.put("paramTest2", paramTest2);
		map.put("user", JSONObject.toJSONString(user));
		return map;
	}
	
	@PutMapping("/tranformPut/{testId}")
	public @ResponseBody Map<String, Object> tranformTest3(User user,@PathVariable(value = "testId") String testId,
			@RequestParam(name = "testNum", defaultValue = "testNum", required = false) String testNum,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		String auth = request.getHeader("auth");
		String paramTest2 = request.getHeader("paramTest2");
		System.out.println(request.getHeader("auth"));
		String ipAddress = IpUtils.getRealIP(request);
		int port = request.getRemotePort();// 返回发出请求的客户机的端口号。
		String url = request.getRequestURL().toString();
		map.put("result", "success");
		//map.put("ipAddress", ipAddress);
		map.put("testId", testId);
		map.put("testNum", testNum);
		map.put("auth", auth);
		map.put("paramTest2", paramTest2);
		map.put("user", JSONObject.toJSONString(user));
		return map;
	}
	
	@PostMapping("/tranformPost2/{testId}")
	public @ResponseBody Map<String, Object> tranformTest4(@RequestBody User user,@PathVariable(value = "testId") String testId,
			@RequestParam(name = "testNum", defaultValue = "testNum", required = false) String testNum,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		String auth = request.getHeader("auth");
		String paramTest2 = request.getHeader("paramTest2");
		System.out.println(request.getHeader("auth"));
		String ipAddress = IpUtils.getRealIP(request);
		int port = request.getRemotePort();// 返回发出请求的客户机的端口号。
		String url = request.getRequestURL().toString();
		map.put("result", "success");
		//map.put("ipAddress", ipAddress);
		map.put("testId", testId);
		map.put("testNum", testNum);
		map.put("auth", auth);
		map.put("paramTest2", paramTest2);
		map.put("user", JSONObject.toJSONString(user));
		return map;
	}
	
	
	@DeleteMapping("/tranformDelete/{testId}")
	public @ResponseBody Map<String, Object> tranformTest5(@PathVariable(value = "testId") String testId,
			@RequestParam(name = "testNum", defaultValue = "testNum", required = false) String testNum,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		String auth = request.getHeader("auth");
		String paramTest2 = request.getHeader("paramTest2");
		System.out.println(request.getHeader("paramTest2"));
		String ipAddress = IpUtils.getRealIP(request);
		int port = request.getRemotePort();// 返回发出请求的客户机的端口号。
		String url = request.getRequestURL().toString();
		map.put("result", "success");
		//map.put("ipAddress", ipAddress);
		map.put("testId", testId);
		map.put("testNum", testNum);
		map.put("auth", auth);
		map.put("paramTest2", paramTest2);
		return map;
	}
	
	@PatchMapping("/tranformPatch/{testId}")
	public @ResponseBody Map<String, Object> tranformTest6(User user,@PathVariable(value = "testId") String testId,
			@RequestParam(name = "testNum", defaultValue = "testNum", required = false) String testNum,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		String auth = request.getHeader("auth");
		String paramTest2 = request.getHeader("paramTest2");
		System.out.println(request.getHeader("auth"));
		String ipAddress = IpUtils.getRealIP(request);
		int port = request.getRemotePort();// 返回发出请求的客户机的端口号。
		String url = request.getRequestURL().toString();
		map.put("result", "success");
		//map.put("ipAddress", ipAddress);
		map.put("testId", testId);
		map.put("testNum", testNum);
		map.put("auth", auth);
		map.put("paramTest2", paramTest2);
		map.put("user", JSONObject.toJSONString(user));
		return map;
	}
	
	@PatchMapping("/tranformPatch2/{testId}")
	public @ResponseBody Map<String, Object> tranformTest7(@RequestBody User user,@PathVariable(value = "testId") String testId,
			@RequestParam(name = "testNum", defaultValue = "testNum", required = false) String testNum,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		String auth = request.getHeader("auth");
		String paramTest2 = request.getHeader("paramTest2");
		System.out.println(request.getHeader("auth"));
		String ipAddress = IpUtils.getRealIP(request);
		int port = request.getRemotePort();// 返回发出请求的客户机的端口号。
		String url = request.getRequestURL().toString();
		map.put("result", "success");
		//map.put("ipAddress", ipAddress);
		map.put("testId", testId);
		map.put("testNum", testNum);
		map.put("auth", auth);
		map.put("paramTest2", paramTest2);
		map.put("user", JSONObject.toJSONString(user));
		return map;
	}
	
	@PutMapping("/tranformPut2/{testId}")
	public @ResponseBody Map<String, Object> tranformTest8(@RequestBody User user,@PathVariable(value = "testId") String testId,
			@RequestParam(name = "testNum", defaultValue = "testNum", required = false) String testNum,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		String auth = request.getHeader("auth");
		String paramTest2 = request.getHeader("paramTest2");
		System.out.println(request.getHeader("auth"));
		String ipAddress = IpUtils.getRealIP(request);
		int port = request.getRemotePort();// 返回发出请求的客户机的端口号。
		String url = request.getRequestURL().toString();
		map.put("result", "success");
		//map.put("ipAddress", ipAddress);
		map.put("testId", testId);
		map.put("testNum", testNum);
		map.put("auth", auth);
		map.put("paramTest2", paramTest2);
		map.put("user", JSONObject.toJSONString(user));
		return map;
	}
	
	@RequestMapping(value = "/tranformTest3/upload", method = RequestMethod.POST)
	@ResponseBody
	public String handleFileUpload(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
		String name = params.getParameter("name");
		System.out.println("name:" + name);
		String id = params.getParameter("id");
		System.out.println("id:" + id);
		String auth = request.getHeader("auth");
		String paramTest2 = request.getHeader("paramTest2");
		MultipartFile file = null;
		BufferedOutputStream stream = null;
		for (int i = 0; i < files.size(); ++i) {
			file = files.get(i);
			if (!file.isEmpty()) {
				try {
					byte[] bytes = file.getBytes();
					//stream = new BufferedOutputStream(new FileOutputStream(new File(file.getOriginalFilename())));
					stream.write(bytes);
					stream.close();
				} catch (Exception e) {
					stream = null;
					return "You failed to upload " + i + " => " + e.getMessage();
				}
			} else {
				return "You failed to upload " + i + " because the file was empty.";
			}
		}
		map.put("result", "success");
		//map.put("ipAddress", ipAddress);
		map.put("auth", auth);
		map.put("paramTest2", paramTest2);
		map.put("name", name);
		map.put("id", id);
		return JSONObject.toJSONString(map);
	}   

	@RequestMapping(value = "/tranformTest3/upload2", method = RequestMethod.PUT)
	@ResponseBody
	public String handleFileUpload2(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
		String name = params.getParameter("name");
		System.out.println("name:" + name);
		String id = params.getParameter("id");
		System.out.println("id:" + id);
		String auth = request.getHeader("auth");
		String paramTest2 = request.getHeader("paramTest2");
		MultipartFile file = null;
		BufferedOutputStream stream = null;
		for (int i = 0; i < files.size(); ++i) {
			file = files.get(i);
			if (!file.isEmpty()) {
				try {
					byte[] bytes = file.getBytes();
					//stream = new BufferedOutputStream(new FileOutputStream(new File(file.getOriginalFilename())));
					stream.write(bytes);
					stream.close();
				} catch (Exception e) {
					stream = null;
					return "You failed to upload " + i + " => " + e.getMessage();
				}
			} else {
				return "You failed to upload " + i + " because the file was empty.";
			}
		}
		map.put("result", "success");
		//map.put("ipAddress", ipAddress);
		map.put("auth", auth);
		map.put("paramTest2", paramTest2);
		map.put("name", name);
		map.put("id", id);
		return JSONObject.toJSONString(map);
	}   
	
	@RequestMapping(value = "/tranformTest3/upload3", method = RequestMethod.PATCH)
	@ResponseBody
	public String handleFileUpload3(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
		String name = params.getParameter("name");
		System.out.println("name:" + name);
		String id = params.getParameter("id");
		System.out.println("id:" + id);
		String auth = request.getHeader("auth");
		String paramTest2 = request.getHeader("paramTest2");
		MultipartFile file = null;
		BufferedOutputStream stream = null;
		for (int i = 0; i < files.size(); ++i) {
			file = files.get(i);
			if (!file.isEmpty()) {
				try {
					byte[] bytes = file.getBytes();
					//stream = new BufferedOutputStream(new FileOutputStream(new File(file.getOriginalFilename())));
					stream.write(bytes);
					stream.close();
				} catch (Exception e) {
					stream = null;
					return "You failed to upload " + i + " => " + e.getMessage();
				}
			} else {
				return "You failed to upload " + i + " because the file was empty.";
			}
		}
		map.put("result", "success");
		//map.put("ipAddress", ipAddress);
		map.put("auth", auth);
		map.put("paramTest2", paramTest2);
		map.put("name", name);
		map.put("id", id);
		return JSONObject.toJSONString(map);
	}   
	
	@RequestMapping(value = "/testOptions", method = RequestMethod.OPTIONS)
	public ResponseEntity options(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("OPTIONS /testOptions called");
		Map<String, Object> map = new HashMap<>();
		String name = request.getParameter("name");
		System.out.println("name:" + name);
		String id = request.getParameter("id");
		System.out.println("id:" + id);
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder("");
		try {
			br = request.getReader();
			String str;
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != br) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println(sb.toString());
		String auth = request.getHeader("auth");
		System.out.println("auth:"+auth);
		String paramTest2 = request.getHeader("paramTest2");
		response.setHeader("Allow", "HEAD,GET,PUT,OPTIONS");
		map.put("result", "success");
		// map.put("ipAddress", ipAddress);
		map.put("auth", auth);
		map.put("paramTest2", paramTest2);
		map.put("name", name);
		map.put("id", id);
		return new ResponseEntity(map, null, HttpStatus.OK);
	}
	

	@RequestMapping(value = "/testHead", method = RequestMethod.HEAD)
	public ResponseEntity testHead(HttpServletRequest request,HttpServletResponse response) {
	    System.out.println("HEAD /testHead called");
	    Map<String, Object> map = new HashMap<>();
	    String name = request.getParameter("name");
		System.out.println("name:" + name);
		String id = request.getParameter("id");
		System.out.println("id:" + id);
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder("");
        try
        {
            br = request.getReader();
            String str;
            while ((str = br.readLine()) != null)
            {
                sb.append(str);
            }
            br.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (null != br)
            {
                try
                {
                    br.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(sb.toString());
		String auth = request.getHeader("auth");
		System.out.println("auth:"+auth);
		String paramTest2 = request.getHeader("paramTest2");
	    response.setHeader("Allow", "HEAD,GET,PUT,OPTIONS");
	    map.put("result", "success");
		//map.put("ipAddress", ipAddress);
		map.put("auth", auth);
		map.put("paramTest2", paramTest2);
		map.put("name", name);
		map.put("id", id);
	    return new ResponseEntity(map,null,HttpStatus.OK);
	}
	
	@GetMapping("check")
	public @ResponseBody String check( HttpServletRequest request) {
		System.out.println(request.getHeader("auth"));
		String ipAddress= IpUtils.getRealIP(request);
		int port =request.getRemotePort();//返回发出请求的客户机的端口号。
		String url = request.getRequestURL().toString();
		return "success:"+ipAddress+",url:"+url;
	}

	@GetMapping("/{id}")
	public @ResponseBody User getUserById(@PathVariable(value = "id") Long id,HttpServletRequest request) {
		System.out.println(request.getHeader("auth"));
		User user = new User();
		user.setId(id);
		user.setName("mrbird");
		user.setAge(25);
		return user;
	}

	@GetMapping("/list")
	public @ResponseBody List<User> getUserList() {
		List<User> list = new ArrayList<>();
		User user1 = new User();
		user1.setId(1l);
		user1.setName("mrbird");
		user1.setAge(25);
		list.add(user1);
		User user2 = new User();
		user2.setId(2l);
		user2.setName("scott");
		user2.setAge(29);
		list.add(user2);
		return list;
	}

	@PostMapping("/add")
	public @ResponseBody Map<String, Object> addUser(User user,HttpServletRequest request) {
		System.out.println(request.getHeader("auth"));
		Map<String, Object> map = new HashMap<>();
		map.put("result", "success");
		return map;
	}

	@DeleteMapping("/{id}")
	public @ResponseBody Map<String, Object> deleteUser(@PathVariable(value = "id") Long id,HttpServletRequest request) {
		System.out.println(request.getHeader("auth"));
		Map<String, Object> map = new HashMap<>();
		map.put("result", "success");
		return map;
	}

	@PutMapping("/{id}")
	public @ResponseBody Map<String, Object> updateUser(@PathVariable(value = "id") Long id, User user,HttpServletRequest request) {
		System.out.println(request.getHeader("auth"));
		Map<String, Object> map = new HashMap<>();
		map.put("result", "success");
		return map;
	}

	@PatchMapping("update/{id}")
	public @ResponseBody Map<String, Object> patchUser(@PathVariable(value = "id") Long id, User user,HttpServletRequest request) {
		System.out.println(request.getHeader("auth"));
		Map<String, Object> map = new HashMap<>();
		map.put("result", "success");
		return map;
	}
	
	@PatchMapping("update2/{id}")
	public @ResponseBody Map<String, Object> patch2User(@PathVariable(value = "id") Long id, @RequestBody User user,HttpServletRequest request) {
		System.out.println(request.getHeader("auth"));
		Map<String, Object> map = new HashMap<>();
		map.put("result", "success");
		return map;
	}
	

	@PutMapping("/demoputxml")
	@ResponseBody
	public MessageAccept demo(@RequestBody User2 user2,HttpServletRequest request) {
		System.out.println(request.getHeader("auth"));
		System.out.println(user2.getName());
		System.out.println(user2.getPassword());
		return new MessageAccept("ada", "sq", "eqd");
	}
	
	@PostMapping("/demopostxml")
	@ResponseBody
	public MessageAccept demo2(@RequestBody User2 user2,HttpServletRequest request) {
		System.out.println(request.getHeader("auth"));
		System.out.println(user2.getName());
		System.out.println(user2.getPassword());
		return new MessageAccept("ada", "sq", "eqd");
	}
	
	@PatchMapping("/demopatchxml")
	@ResponseBody
	public MessageAccept demo3(@RequestBody User2 user2,HttpServletRequest request) {
		System.out.println(request.getHeader("auth"));
		System.out.println(user2.getName());
		System.out.println(user2.getPassword());
		return new MessageAccept("ada", "sq", "eqd");
	}
	
	@PostMapping("/authPost/{name}/{secret}/{authType}/{algorithm}")
	@ResponseBody
	public Map<String,String> authPost(@PathVariable(value = "name") String name,
			@PathVariable(value = "secret") String secret,
			@PathVariable(value = "authType") String authType,
			@PathVariable(value = "algorithm") String algorithm,
			@RequestBody User user,HttpServletRequest request) {
		System.out.println(user.getName());
		Map<String,String> result = new HashMap<>();
		//String secret = "BdPyfBaLQCgshVM5HONSB9JaLHbdHHJj"; // 用户的密钥
		// x-date
		Date d = new Date();
		DateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		String hdate = format.format(d);
		System.out.println("date: " + hdate);
		result.put("date", hdate);
		// body
//		User user = new User();
//		user.setName("bob");
//		user.setAge(10);
//		user.setId(1L);
		String body = JSONObject.toJSONString(user);
		System.out.println("body:" + body);
		result.put("body", body);
		String digest = "";
		try {
			digest = new String(Base64.getEncoder().encode(HashUtils.getInstance().SHA256ReturnByte(body)), "US-ASCII");
			System.out.println("显示digest: " + digest);
			result.put("digest", digest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		StringBuilder stb = new StringBuilder();
		String content2 = stb.append("date: ").append(hdate).append("\n").append("digest: ").append("SHA-256=")
				.append(digest).toString();
		System.out.println("签名前内容: " + content2);
		result.put("content2", content2);
		try {
			String signature2 = new String(
					Base64.getEncoder().encode(HmacSha1Util.signatureReturnBytesByType(content2, secret, authType)), "US-ASCII");
			System.out.println("指定编码2: " + signature2);
			String authorization = "hmac username="+"\""+name+"\""+", algorithm="+"\""+algorithm+"\""+", headers="+"\""+"date digest"+"\""+", signature="+"\""+signature2+"\"";
			System.out.println("authorization: " + authorization);
			result.put("signature2", signature2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	@PostMapping("/authPost2/{name}/{secret}/{authType}/{algorithm}")
	@ResponseBody
	public Map<String,String> authPost2(@PathVariable(value = "name") String name,
			@PathVariable(value = "secret") String secret,
			@PathVariable(value = "authType") String authType,
			@PathVariable(value = "algorithm") String algorithm,
			@RequestBody Map<String,String> map,HttpServletRequest request) {
		System.out.println(JSONObject.toJSONString(map));
		Map<String, String> result = new HashMap<>();
		String queryParam = "hello";
		System.out.println("hello: " + queryParam);

		Date d = new Date();
		DateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		String hdate = format.format(d);
		System.out.println("date: "+ hdate);

		// String secret = "BdPyfBaLQCgshVM5HONSB9JaLHbdHHJj"; //用户的密钥
		//String secret = "zAMLTpp7FcqApeUTHiugLMOjJueayC5H"; // 用户的密钥
		StringBuilder stb = new StringBuilder();
		String content1 = stb.append("date: ").append(hdate).toString();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
			content1 = content1 + "\n" + entry.getKey() + ": " + entry.getValue();
		}
		// String content1 = stb.append("x-date: ").append(hdate).toString();
		// String content1 = stb.append("date:
		// ").append(hdate).append("\n").append("hello:
		// ").append(queryParam).toString();
		System.out.println("签名前内容: " + content1);
		//Authorization    hmac username="test17", algorithm="hmac-sha1", headers="date", signature="1NHgdmI8svY+WRZ7HSVNthHD4tA="
		try {
			String signature2 = new String(
					Base64.getEncoder().encode(HmacSha1Util.signatureReturnBytesByType(content1, secret,authType)), "US-ASCII");
			System.out.println("指定编码2:"+signature2);
			result.put("date", hdate);
			result.put("signature2", signature2);
			String authorization = "hmac username="+"\""+name+"\""+", algorithm="+"\""+algorithm+"\""+", headers="+"\""+"date"+"\""+", signature="+"\""+signature2+"\"";
			System.out.println("authorization:"+authorization);
			result.put("Authorization", authorization);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@GetMapping("/authNoBody/{name}/{secret}/{authType}/{algorithm}")
	@ResponseBody
	public Map<String, String> authNoBody(@PathVariable(value = "name") String name,
			@PathVariable(value = "secret") String secret,
			@PathVariable(value = "authType") String authType,
			@PathVariable(value = "algorithm") String algorithm, HttpServletRequest request) {
		Map<String, String> result = new HashMap<>();
		String queryParam = "hello";
		System.out.println("hello: " + queryParam);

		Date d = new Date();
		DateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		String hdate = format.format(d);
		System.out.println("date: "+ hdate);

		// String secret = "BdPyfBaLQCgshVM5HONSB9JaLHbdHHJj"; //用户的密钥
		//String secret = "zAMLTpp7FcqApeUTHiugLMOjJueayC5H"; // 用户的密钥
		StringBuilder stb = new StringBuilder();
		String content1 = stb.append("date: ").append(hdate).toString();
		// String content1 = stb.append("x-date: ").append(hdate).toString();
		// String content1 = stb.append("date:
		// ").append(hdate).append("\n").append("hello:
		// ").append(queryParam).toString();
		System.out.println("签名前内容: " + content1);
		//Authorization    hmac username="test17", algorithm="hmac-sha1", headers="date", signature="1NHgdmI8svY+WRZ7HSVNthHD4tA="
		try {
			String signature2 = new String(
					Base64.getEncoder().encode(HmacSha1Util.signatureReturnBytesByType(content1, secret,authType)), "US-ASCII");
			System.out.println("指定编码2:"+signature2);
			result.put("date", hdate);
			result.put("signature2", signature2);
			String authorization = "hmac username="+"\""+name+"\""+", algorithm="+"\""+algorithm+"\""+", headers="+"\""+"date"+"\""+", signature="+"\""+signature2+"\"";
			System.out.println("authorization:"+authorization);
			result.put("Authorization", authorization);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}

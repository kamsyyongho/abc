package com.skt.doss.portal.crowd.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skt.doss.portal.crowd.core.object.command.UserTokenRequestVo;
import com.skt.doss.portal.crowd.core.service.UserCrowdService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/v1/crowd")
public class UserCrowdController {
	
	@Autowired
	private UserCrowdService userCrowdService;
	
	@ApiOperation(value = "사용자 여부 조회", httpMethod = "GET")
    @GetMapping(value = "/user-yn")
    public ResponseEntity<Object> getUserYn(
    		@RequestParam(name = "userId", required = true) String userId,
    		@RequestParam(name = "key", required = false, value = "") String key) throws UnknownHostException {
		InetAddress local = InetAddress.getLocalHost();
		System.out.println("my ip"+local.getHostAddress());

    	return ResponseEntity.ok(userCrowdService.getUserYn(userId, key));
    }
	
	@ApiOperation(value = "그룹 추가", httpMethod = "POST", notes = "그룹 추가")
    @PostMapping(value = "/add-group")
    public ResponseEntity<Object> addGroup(@RequestBody Map param)
    {	
		System.out.println(param.get("name"));
		
    	return ResponseEntity.ok(userCrowdService.addGroup(param));
    }
	
	@ApiOperation(value = "그룹 수정", httpMethod = "PUT", notes = "그룹 수정")
    @PutMapping(value = "/update-group")
    public ResponseEntity<Object> updateGroup(@RequestParam String groupname, @RequestBody Map param)
    {	
		System.out.println(param);
		
    	return ResponseEntity.ok(userCrowdService.updateGroup(groupname,param));
    }	
	
	@ApiOperation(value = "그룹 조회", httpMethod = "GET", notes = "그룹 조회")
    @GetMapping(value = "/get-group")
    public ResponseEntity<Object> getGroup(@RequestParam String groupname)
    {	
		System.out.println(groupname);
		
    	return ResponseEntity.ok(userCrowdService.getGroup(groupname));
    }	
	
	@ApiOperation(value = "유저 그룹 리스트 조회", httpMethod = "GET", notes = "유저 그룹 리스트 조회")
    @GetMapping(value = "/get-user-group-list")
    public ResponseEntity<Object> getUserGroupList(
    		@RequestParam(value="username",required = true) String userName,
    		@RequestParam(value="groupname",required=false) String groupname,
    		@RequestParam(value="max-results",required=false) String maxResult,
    		@RequestParam(value="start-index",required=false) String startIndex
    		)
    {	
		System.out.println(groupname);
		
    	return ResponseEntity.ok(userCrowdService.getUserGroupList(userName,groupname,maxResult,startIndex));
    }	
	
	@ApiOperation(value = "유저 그룹 추가", httpMethod = "POST", notes = "유저 그룹 추가 api")
    @PostMapping(value = "/add-user-togroup")
    public ResponseEntity<Object> addUserToGroup(
    		@RequestParam(value="username",required = true) String userName,
    		@RequestBody Map param)
    {	
		
    	return ResponseEntity.ok(userCrowdService.addUserToGroup(userName,param));
    }	

	@ApiOperation(value = "사용자 토큰 조회", httpMethod = "POST")
    @PostMapping(value = "/user-token")
    public ResponseEntity<Object> getUserToken(
    		HttpServletRequest request,
    		@RequestBody(required = true) UserTokenRequestVo reqVo)
    {
    	return ResponseEntity.ok(userCrowdService.getUserToken(request.getRemoteAddr(), reqVo));
    }
	
	@ApiOperation(value = "사용자 토큰 삭제", httpMethod = "DELETE")
    @DeleteMapping(value = "/delete-user-token/{token}")
    public ResponseEntity<Object> deleteUserToken(
    		@PathVariable(required = true) String token)
    {
    	return ResponseEntity.ok(userCrowdService.deleteUserToken(token));
    }
	
	@ApiOperation(value = "crowd 유저 추가", httpMethod = "POST", notes = "crowd 유저 추가 api")
    @PostMapping(value = "/add-crowd-user")
    public ResponseEntity<Object> addCrowdUser(
    		@RequestBody Map param)
    {	
		
    	return ResponseEntity.ok(userCrowdService.addCrowdUser(param));
    }		
	

}

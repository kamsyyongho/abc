package com.skt.doss.portal.crowd.core.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skt.doss.portal.crowd.core.object.command.UserTokenRequestVo;
import com.skt.doss.portal.crowd.port_infra.clientAPI.UserCrowdClient;

import feign.FeignException;

@Service
public class UserCrowdService {

	@Autowired
	private UserCrowdClient userCrowdClient;
	
	public Map<String, Object> getUserYn(String userId, String key) {
		
		String code = "";
		String msg = "";
		String userYn = "N";
		
		Map<String, Object> resMap = userCrowdClient.getUserYn(userId, key);
		
		if(resMap.get("name") != null && !"".equals(resMap.get("name"))) {
			code = "OK";
			userYn = "Y";
		} else {
			code = (String) resMap.get("reason");
			msg = (String) resMap.get("message");
		}
		
		Map<String, Object> retMap = new HashMap<>();
		
		retMap.put("code", code);
		retMap.put("msg", msg);
		retMap.put("userYn", userYn);
		
		return retMap;
	}
	

	public Map<String, Object> addGroup(Map param) {
		
		String code = "";
		String msg = "";
		//String userYn = "N";
		Map<String, Object> retMap = new HashMap();
		
		try {
			Map corwdMap = userCrowdClient.addGroup(param);
			code = "OK";
			msg = "그룹 정보가 정상적으로 등록 되었습니다.";
		}catch(FeignException e) {
			code = "err";
			msg = e.getMessage();
		}
		
		retMap.put("code", code);
		retMap.put("err", msg);
		
		return retMap;
	}	
	
	public Map<String, Object> updateGroup(String oldgroupname,Map params) {
		
		String code = "";
		String msg = "";
		String msgDetail = "";
		//String userYn = "N";
		
		Map<String, Object> retMap = new HashMap();
		
		try {
			Map corwdMap = userCrowdClient.updateGroup(oldgroupname,params);
			System.out.println(corwdMap);
			
			if(corwdMap.get("name") != null) {
				code = "OK";
				msg = "그룹 정보가 정상적으로 수정 되었습니다.";
			}else {
				code = "err";
				msg = (String)corwdMap.get("reason");
				msgDetail = (String)corwdMap.get("message");
			}
			

		}catch(FeignException e) {
			code = "err";
			msg = e.getMessage();
		}
		
		retMap.put("code", code);
		retMap.put("err", msg);
		retMap.put("msgDetail", msgDetail);
		
		return retMap;
	}
	
	public Map<String, Object> getGroup(String groupname) {
		
		String code = "";
		String msg = "";
		
		
		Map<String, Object> resMap = userCrowdClient.getGroup(groupname);
		
		if(resMap.get("name") != null && !"".equals(resMap.get("name"))) {
			code = "OK";
			msg = "";
			
		} else {
			code = (String) resMap.get("reason");
			msg = (String) resMap.get("message");
		}
		
		Map<String, Object> retMap = new HashMap<>();
		
		retMap.put("code", code);
		retMap.put("msg", msg);
		retMap.put("result", resMap);
		
		return retMap;
	}

	public Object getUserGroupList(String userName, String groupname, String maxResult, String startIndex) {
		String code = "";
		String msg = "";
		
		Map<String, Object> resMap = userCrowdClient.getUserGroupList(userName,groupname,maxResult,startIndex);
		
		if(resMap.get("name") != null && !"".equals(resMap.get("name"))) {
			code = "OK";
			msg = "";
			
		} else {
			code = (String) resMap.get("reason");
			msg = (String) resMap.get("message");
		}
		
		Map<String, Object> retMap = new HashMap<>();
		
		retMap.put("code", code);
		retMap.put("msg", msg);
		retMap.put("result", resMap);
		
		return retMap;
	}

	public Object addUserToGroup(String userName, Map param) {
		
		String code = "";
		String msg = "";
		String msgDetail = "";
		//String userYn = "N";
		
		Map<String, Object> retMap = new HashMap();
		System.out.println(param);
		try {
			Map corwdMap = userCrowdClient.addUserToGroup(userName,param);
			System.out.println(corwdMap + " .. ");
			
			code = "OK";
			msg = "그룹 정보가 정상적으로 등록 되었습니다.";
			
		}catch(FeignException e) {
			code = "err";
			msg = e.getMessage();
		}
		
		retMap.put("code", code);
		retMap.put("err", msg);
		retMap.put("msgDetail", msgDetail);		
		
		return retMap;
	}

	public Map<String, Object> getUserToken(String remoteAddr, UserTokenRequestVo reqVo) {
		
		String code = "";
		String msg = "";
		
		Map<String,Object> remoteAddrMap = new HashMap<>();
		remoteAddrMap.put("name", "remote_address");
		remoteAddrMap.put("value", remoteAddr);
		
		List<Map<String,Object>> validationList = new ArrayList<>();
		validationList.add(remoteAddrMap);
		
		Map<String,Object> validationMap = new HashMap<>();
		validationMap.put("validationFactors", validationList);
		
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("username", reqVo.getUserId());
		paramMap.put("password", reqVo.getUserPw());
		paramMap.put("validation-factors", validationMap);
		
		Map<String, Object> retMap = new HashMap<>();
		
		try {
			Map<String, Object> resMap = userCrowdClient.getUserToken(paramMap);
			code = "OK";
			retMap.put("token", resMap.get("token"));
		} catch(FeignException fe) {
			code = "err";
			msg = "auth_error";
			retMap.put("token", "");
		}
		
		retMap.put("code", code);
		retMap.put("msg", msg);
	  
	  	return retMap;
	}
	
	public Map<String, Object> deleteUserToken(String token) {
		
		String code = "OK";
		String msg = "";
		
		Map<String, Object> resMap = userCrowdClient.deleteUserToken(token);
		
		Map<String, Object> retMap = new HashMap<>();
		
		retMap.put("code", code);
		retMap.put("msg", msg);
		retMap.put("result", resMap);
		
		return retMap;
	}

	public Object addCrowdUser(Map param) {
		// TODO Auto-generated method stub
		String code = "";
		String msg = "";
		
		Map<String, Object> resMap = userCrowdClient.addCrowdUser(param);
		
		Map<String, Object> retMap = new HashMap<>();
		
		retMap.put("code", "OK");
		retMap.put("msg", "회원정보가 정상적으로 등록되었습니다.");
		retMap.put("result", resMap);
		
		return retMap;		
	}
	
	public Object updateCrowdUser(String username , Map param) {
		// TODO Auto-generated method stub
		String code = "";
		String msg = "";
		
	    Map<String, Object> resMap = userCrowdClient.updateCrowdUser(username,param);
		
		Map<String, Object> retMap = new HashMap<>();
		
		retMap.put("code", "OK");
		retMap.put("msg", "회원정보가 정상적으로 등록되었습니다.");
		//retMap.put("result", resMap);
		
		return retMap;		
	}

	
}

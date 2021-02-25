package com.skt.doss.common.utils;

import com.skt.doss.portal.user.core.object.commad.UserRequestVo;
import com.skt.doss.portal.user.core.service.UserService;

import java.util.HashMap;
import java.util.Map;

public class CommUser {
	
    private UserService userService = (UserService) BeanUtils.getBean("userService");
    
    CommUtil commUtil = new CommUtil();
   
	public Map<String , Object> checkExistingUser(Map<String,Object> userInfo){
		
		Map resultMap = userService.checkExistingUser(userInfo);
		
		return resultMap;
	}

	public Map<String,Object> regUserInfo(Map<String, Object> param) {
		// TODO Auto-generated method stub
		
		Map<String,Object> result = userService.regUserInfo(param);
		
		return result;
	}
	
	//skt정직원 여부 
	/*
	public Boolean reqSktMngUser(String dossId) {
		// TODO Auto-generated method stub
		
		String sktMemberYn = userService.selectIlmUserInfo(dossId);
		
		
	}*/
	
	//Doss회원여부
	public Boolean reqDossMemberYn(Map<String, Object> param) {
		
		// TODO Auto-generated method stub
		System.out.println(param.get("DOSS_ID"));
		Map<String,Object> selectDossUserInfo = userService.selectDossUserInfo(param);
		
		if(selectDossUserInfo != null && !selectDossUserInfo.isEmpty()) {
			System.out.println(" == selectDossUserInfo not null == ");
			return true;
		}else {
			return false;
		}
	}
	
	//회원가입 전 유효성 체크
	public Map<String,Object> signUpValidChk(Map<String, Object> param) {
		// TODO Auto-generated method stub
		Map resultMap = null;
		
		return resultMap;
		
	}	
	
	//회원가입
	public Map<String,Object> regMemberInfo(UserRequestVo user) throws Exception {
		// TODO Auto-generated method stub
		Map resultMap = null;
		userService.regMemberInfo(user);
		return resultMap;
	}	
	
	public Map<String , Object> checkUserSignupInfo(UserRequestVo user){
		
		Map<String , Object> resultMap = new HashMap<String , Object>();
		
		if(commUtil.sktMemberYn(user.getDossId()).equals("Y")) {
			
			//필수값 체크
			if(user.getDossId() == null || "".equals(user.getDossId()) ) {
				resultMap.put("code", "err");
				resultMap.put("msg", "doss_id는 필수 입니다.");
				return resultMap;
			}				
			
		}else {
			
			//필수값 체크
			if(user.getDossId() == null || "".equals(user.getDossId()) ) {
				resultMap.put("code", "err");
				resultMap.put("msg", "doss_id는 필수 입니다.");
				return resultMap;
			}	
			
			if(user.getPwd() == null || "".equals(user.getPwd()) ) {
				resultMap.put("code", "err");
				resultMap.put("msg", "패스워드는 필수 입니다.");
				return resultMap;
			}	
			
			/*
			if(user.getUserType() == null || "".equals(user.getUserType()) ) {
				resultMap.put("code", "err");
				resultMap.put("msg", "userType은 필수 입니다.");
				return resultMap;
			}*/
			
			if(user.getPhoneNo() == null || "".equals(user.getPhoneNo()) ) {
				resultMap.put("code", "err");
				resultMap.put("msg", "핸드폰 번호는 필수 입니다.");
				return resultMap;
			}
			
			if(user.getEmail() == null || "".equals(user.getEmail()) ) {
				resultMap.put("code", "err");
				resultMap.put("msg", "email은 필수 입니다.");
				return resultMap;
			}	
			/*
			if(commUtil.isNull((String)userMap.get("company_nm")) ) {
				resultMap.put("code", "err");
				resultMap.put("msg", "회사명은 필수 입니다.");
				return resultMap;
			}	*/
			
			if(user.getDeptNm() == null || "".equals(user.getDeptNm()) ) {
				resultMap.put("code", "err");
				resultMap.put("msg", "부서명은 필수 입니다.");
				return resultMap;
			}	
			
			if(user.getUserNm() == null || "".equals(user.getUserNm()) ) {
				resultMap.put("code", "err");
				resultMap.put("msg", "사용자명은 필수 입니다.");
				return resultMap;
			}
			
		}
		

		
		resultMap.put("code", "OK");
		
		return resultMap;
		
		
	}
}

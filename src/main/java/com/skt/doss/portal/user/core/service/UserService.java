package com.skt.doss.portal.user.core.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skt.doss.common.utils.CommUtil;
import com.skt.doss.portal.crowd.core.service.UserCrowdService;
import com.skt.doss.portal.crowd.port_infra.clientAPI.UserCrowdClient;
import com.skt.doss.portal.user.core.mapper.UserMapper;
import com.skt.doss.portal.user.core.object.commad.UserRequestVo;
import com.skt.doss.portal.user.core.object.query.UserAuthResponseVo;
import com.skt.doss.portal.user.core.object.query.UserResponseVo;

import lombok.extern.slf4j.Slf4j;

//import com.skt.doss.iam.port_infra.persistent.UserMapper;

@Service

@Slf4j
public class UserService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserCrowdClient userCrowdClient;
    
    CommUtil commUtil = new CommUtil();
    
    //PasswordEncoding passwordEncoding = new PasswordEncoding();
    
	public UserResponseVo searchUserInfo(String dossId) throws Exception{
		System.out.println(" == searchUserInfo == ");
		
		UserResponseVo repsDto = userMapper.searchUserInfo(dossId);
		System.out.println(repsDto + " : repsDto");
        return repsDto;
     }

	public Map checkExistingUser(Map<String, Object> userInfo) {
		// TODO Auto-generated method stub
		return userMapper.checkExistingUser();
	}

	public Map<String,Object> regUserInfo(Map<String, Object> param) {
		Map<String, Object> resultMap = new HashMap();
		
		String dossId;
		// TODO Auto-generated method stub
		if(!param.get("user_type").equals("S")) {//정직원이 아닐경우
			//도스아이디 체번
			//dossId = userMapper.makeDossId();
		}else {//정직원일 경우
			dossId = (String)param.get("user_id");
		}
		
		//유저정보 등록
	    userMapper.regUserInfo(param);
	    
	    //비밀번호 등록
	    userMapper.regUserPwdInfo(param);
	    
	    return resultMap;
	}

	public String selectIlmUserInfo(String empno) {
		// TODO Auto-generated method stub
		return userMapper.selectIlmUserInfo(empno);
	}

	public Map<String, Object> selectDossUserInfo(Map<String, Object> param) {
		// TODO Auto-generated method stub
		System.out.println(" == selectDossUserInfo service == ");
		return userMapper.selectDossUserInfo(param);
	}

	
	@Transactional(rollbackFor = Exception.class)
	public Map<String , Object> regMemberInfo(UserRequestVo user) throws Exception {
		
		Map resultMap = new HashMap();
		
		if(commUtil.sktMemberYn(user.getDossId()).equals("Y")) {
			
			UserResponseVo userResp = userMapper.getSktMemberInfo(user.getDossId());
			
			if(userResp ==null) {
				resultMap.put("code", "err");
				resultMap.put("msg", "skt 유저 정보가 존재하지 않습니다.");
				System.out.println(resultMap.get("msg") + " !!!!!!!! ");
				return resultMap;
			}
			
			System.out.println(" == ?????????? == ");
			
			user.setUserNm(userResp.getHname());
			user.setBirthDt(userResp.getRegno());
			user.setDeptNm(userResp.getDeptNm());
			user.setUserType("S");
			user.setSktEmpNo(user.getDossId());
			user.setPhoneNo(userResp.getMovetelno());
			user.setEmail(userResp.getEmail());
			user.setPwd("1234");
			user.setCi("x");
			
		}else {
			user.setUserType("B");
		}
		
		
		userMapper.insertUserInfo(user);
		userMapper.insertUserCred(user);
		
	    Map crowdUserMap = new HashMap();
	    Map pwdMap = new HashMap();
	    String displNm = "";
	    displNm = (String)user.getUserNm() + " " +(String)user.getDeptNm();
	    pwdMap.put("value", user.getPwd());
	    crowdUserMap.put("name"			, user.getDossId());
	    crowdUserMap.put("password"		, pwdMap);
	    crowdUserMap.put("active"		, true);
	    crowdUserMap.put("first-name"	, user.getUserNm());
	    crowdUserMap.put("last-name"	, user.getCompanyNm());
	    crowdUserMap.put("display-name"	, displNm);
	    crowdUserMap.put("email"		, user.getEmail());
	    
	    userCrowdClient.addCrowdUser(crowdUserMap);
	    
	    System.out.println(crowdUserMap + " ??????????? ");
		
		resultMap.put("code", "OK");
		resultMap.put("msg", "회원등록이 완료되었습니다.");
		
		return resultMap;
	}

	public List<UserAuthResponseVo> searchUserAuth(String dossId) {
		// TODO Auto-generated method stub
		return userMapper.searchUserAuth(dossId);
	}

	@Transactional(rollbackFor = Exception.class)
	public void updUserInfo(UserRequestVo userRequestVo) {
		// TODO Auto-generated method stub
		Map<String , Object> resultMap = new HashMap<String , Object>();
		
		userMapper.updUserInfo(userRequestVo);
	}

	public UserResponseVo checkDossUser(UserRequestVo user) {
		// TODO Auto-generated method stub
		return userMapper.checkDossUser(user);
	}

	public void userCertiStatusChange(UserRequestVo user) throws Exception{
		// TODO Auto-generated method stub
		
		userMapper.userCertiStatusChange(user);
		
	}

	public UserResponseVo checkUserCertiStatus(String dossId) {
		// TODO Auto-generated method stub
		return userMapper.checkUserCertiStatus(dossId);
	}

	public String makeDossId() {
		// TODO Auto-generated method stub
		return userMapper.makeDossId();
	}

	public String selectNextDossSeq() {
		// TODO Auto-generated method stub
		return userMapper.selectNextDossSeq();
	}

	public Map<String, Object> updateDossSeq(Map<String , Object> param) {
		// TODO Auto-generated method stub
		
		Map<String , Object> resultMap = new HashMap<String , Object>();
		
		
		try {
			userMapper.updateDossSeq(param);
			resultMap.put("code", "OK");
			return resultMap;
		}catch(Exception e) {
			resultMap.put("code", "err");
			return resultMap;
		}
	}

	public Map<String, Object> insertUserLoginHst(UserRequestVo user) {
		// TODO Auto-generated method stub
		Map<String , Object> resultMap = new HashMap<String , Object>();
		try {
			userMapper.insertUserLoginHst(user);
			resultMap.put("code", "OK");
			return resultMap;
		}catch(Exception e) {
			resultMap.put("code", "err");
			return resultMap;	
		}
	}
	/*
	public String sktMemberYn(String empno) {
		// TODO Auto-generated method stub
		return userMapper.sktMemberYn;
	}*/

	public UserResponseVo certiAsisUser(UserRequestVo userRequestVo) {
	  UserResponseVo result = userMapper.certiAsisUser(userRequestVo);
	  if("Y".equals(result.getAsisUserYn())) {
	    userMapper.expireAsisUser(userRequestVo);
	  }
	  
	  return result;
	}

	public UserResponseVo getSktMemberInfo(String empno) {
		// TODO Auto-generated method stub
		return userMapper.getSktMemberInfo(empno);
	}
}

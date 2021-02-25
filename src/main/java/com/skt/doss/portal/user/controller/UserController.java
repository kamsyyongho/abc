package com.skt.doss.portal.user.controller;

import com.skt.doss.common.utils.CommUser;
import com.skt.doss.common.utils.CommUtil;
import com.skt.doss.common.utils.IdentityVer;
import com.skt.doss.common.utils.TNetCerti;
import com.skt.doss.portal.crowd.core.service.UserCrowdService;
import com.skt.doss.portal.user.core.object.commad.UserRequestVo;
import com.skt.doss.portal.user.core.object.query.UserAuthResponseVo;
import com.skt.doss.portal.user.core.object.query.UserResponseVo;
import com.skt.doss.portal.user.core.service.UserService;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.skcc.twd.customer.core.object.query.ResponseDto;

@RestController
public class UserController {

	@Autowired
    UserService userService;
	
    CommUser commUser = new CommUser();

    TNetCerti tNetCerti = new TNetCerti();

    IdentityVer identityVer = new IdentityVer();

    CommUtil commUtil = new CommUtil();
    
    @Autowired
    UserCrowdService userCrowdService;


	private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @ApiOperation(value = "유저정보 조회", httpMethod = "GET", notes = "유저 정보 조회 API.")
    @GetMapping(value="/api/v1/user/searchUserInfo/{dossId}")
    public ResponseEntity<Object> searchUserInfo(@PathVariable String dossId) throws Exception{
    	
    	UserResponseVo userResponseVo = new UserResponseVo();
    	
    	log.debug(dossId);
    	
    	if(dossId==null || "".equals(dossId)) {
    		userResponseVo.setCode("err");
    		userResponseVo.setMsg("dossId는 필수입니다.");
    		return  new ResponseEntity<>(userResponseVo, HttpStatus.OK);   		
    	}
    	
    	userResponseVo = userService.searchUserInfo(dossId);
    	
    	System.out.println(userResponseVo);
    	
    	if(userResponseVo==null) {
    		Map resultMap = new HashMap();
    		resultMap.put("code", "err");
    		resultMap.put("msg", "유저 정보가 존재하지 않습니다.");
    		
    		return new ResponseEntity<>(resultMap, HttpStatus.OK);
    	}else {
    		System.out.println(userResponseVo);
    		userResponseVo.setCode("OK");
    		return  new ResponseEntity<>(userResponseVo, HttpStatus.OK);
    	}
    	
    	//return  new ResponseEntity<>(userResponseVo, HttpStatus.OK);
    	
    }

    @ApiOperation(value = "회원정보 입력", httpMethod = "POST", notes = "BP / Family / 외부 직원 회원 정보 입력")
    @GetMapping(value="/api/v1/user/userInfoInput")
    public ResponseEntity<Object> regUserInfo(@RequestBody Map<String,Object> param){
    	//01. 등록 사용자 체크
    	Map<String,Object> resultMap = commUser.regUserInfo(param);
    	return new ResponseEntity<>(resultMap , HttpStatus.OK);

    }

    @ApiOperation(value = "회원가입", httpMethod = "POST", notes = "회원 가입 API.")
    @PostMapping(value="/api/v1/user/signUp")
    public ResponseEntity<Object> signUp(@RequestBody UserRequestVo user){
    	Map<String , Object> resultMap = new HashMap<>();
    	log.debug(user + " == signUp == ");

    	Map<String, Object> checkUserMap = commUser.checkUserSignupInfo(user);

    	if(!checkUserMap.get("code").equals("OK")) {
    		resultMap.put("code", checkUserMap.get("msg"));
    		resultMap.put("msg", "err");
    	    return new ResponseEntity<>(resultMap, HttpStatus.OK);
    	}

    	Map<String , Object> regUserMap = new HashMap<String, Object>();
    	
    	try {
    		regUserMap = userService.regMemberInfo(user);
    		resultMap.put("code", regUserMap.get("code"));
        	resultMap.put("msg", regUserMap.get("msg"));
        	
    	}catch(Exception e) {
    		log.info(e.getMessage() + " : get message ");
    		log.info("   == ?? == ");
    		resultMap.put("code", "err");
    		resultMap.put("msg", "회원가입 중 오류 발생. 관리자에게 문의하세요.");
    	}

    	return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @ApiOperation(value = "정직원여부조회", httpMethod = "GET", notes = "회원 가입 API.")
    @GetMapping(value="/api/v1/user/sktMemberYn/{empno}")
    public ResponseEntity<Object> sktMemberYn(@PathVariable String empno){
    	log.debug("sktMemberYn .. " + empno);
    	Map<String , Object> resultMap = new HashMap<>();
    	
    	if(empno == null || "".equals(empno)) {
    		resultMap.put("msg", "empno는 필수 항목입니다.");
    		resultMap.put("code", "err");
    	}
    	
    	resultMap.put("sktMemberYn", commUtil.sktMemberYn(empno));
    	
    	/*
    	if("10".equals(empno.substring(0, 2)) || "11".equals(empno.substring(0, 2))){
    		resultMap.put("sktMemberYn", "Y");
    	}else {
    		resultMap.put("sktMemberYn", "N");
    	}*/
    	
    	resultMap.put("msg", "");
    	resultMap.put("code", "OK");
    	
    	//String sktMemberYn = commUser.reqSktMngUser(dossId);
    	
    	//String sktMemberYn = userService.sktMemberYn(empno);

    	//resultMap.put("empno", param.get("EMPNO"));
    	//resultMap.put("result", commUser.reqSktMngUser(dossId) == true ? 'Y' : 'N');

    	return new ResponseEntity<>(resultMap , HttpStatus.OK);
    }

    @ApiOperation(value = "DOSS 회원 여부", httpMethod = "POST", notes = "회원 가입 API.")
    @PostMapping(value="/api/v1/user/dossMemberYn")
    public ResponseEntity<Object> dossMemberYn(@RequestBody Map<String,Object> param){
    	log.debug(param + " == dossMemberYn == ");
    	Map<String , Object> resultMap = new HashMap<>();

    	
    	if(param.isEmpty() || param.get("doss_id") ==null) {
    		resultMap.put("doss_id", "");
    		resultMap.put("resultMsg","doss_id를 확인해주세요.");

    		resultMap.put("resultCd","110");
    		return new ResponseEntity<>(resultMap , HttpStatus.OK);
    	}

    	
    	resultMap.put("doss_id", param.get("doss_id"));


    	resultMap.put("DOSS_ID", param.get("DOSS_ID"));

    	resultMap.put("resultMsg","OK");
    	resultMap.put("resultCd", commUser.reqDossMemberYn(param) == true ? 'Y' : 'N');

    	return new ResponseEntity<>(resultMap , HttpStatus.OK);

    }

    @ApiOperation(value = "사용자 권한조회 api", httpMethod = "GET", notes = "회원 가입 API.")
    @GetMapping(value="/api/v1/user/searchUserAuth/{dossId}")
    public ResponseEntity<Object> searchUserAuth(@PathVariable String dossId){
    	log.debug(dossId + " == searchUserAuth == ");
    	Map<String , Object> resultMap = new HashMap<>();

    	
    	if("".equals(dossId) || dossId ==null) {
    		resultMap.put("doss_id", "");
    		resultMap.put("msg","doss_id를 확인해주세요.");
    		resultMap.put("code","err");
    		return new ResponseEntity<>(resultMap , HttpStatus.OK);
    	}

    	List<UserAuthResponseVo> userAuthList = userService.searchUserAuth(dossId);
		resultMap.put("msg","");
		resultMap.put("code","OK");
    	resultMap.put("userAuthList", userAuthList);

    	return new ResponseEntity<>(resultMap , HttpStatus.OK);

    }

    @ApiOperation(value = "회원정보 변경", httpMethod = "POST", notes = "Portal 회원 정보를 변경하는 API(정직원 제외)")
    @PostMapping(value="/api/v1/user/updUserInfo")
    public ResponseEntity<Object> updUserInfo(@RequestBody UserRequestVo userRequestVo){

    	log.debug(userRequestVo +  " == updUserInfo == ");

    	Map<String , Object> resultMap = new HashMap<>();

    	if(userRequestVo.getDossId()==null || "".equals(userRequestVo.getDossId())) {

    		resultMap.put("msg","dossId는 필수입니다.");
    		resultMap.put("code","err");
    		return new ResponseEntity<>(resultMap , HttpStatus.OK);
    	}

    	try {
    		userService.updUserInfo(userRequestVo);
        	resultMap.put("msg", "");
        	resultMap.put("code", "OK");
    	}catch(Exception e) {
    		System.out.println(e.getMessage());
    		resultMap.put("code", "err");
    		resultMap.put("msg", "사용자 정보 변경중 오류 발생. 관리자에게 문의하세요.");
    	}

    	return new ResponseEntity<>(resultMap , HttpStatus.OK);
    } 
 


    @ApiOperation(value = "회원정보 확인", httpMethod = "POST", notes = "Portal 회원 정보 id pwd 확인 API")
    @PostMapping(value="/api/v1/user/checkDossUser")
    public ResponseEntity<Object> checkDossUser(@RequestBody UserRequestVo userRequestVo){

    	log.debug( " == checkDossUser == ");

    	Map<String , Object> resultMap = new HashMap<>();

    	if(userRequestVo.getDossId()==null || "".equals(userRequestVo.getDossId())) {

    		resultMap.put("msg","doss id는 필수입니다.");
    		resultMap.put("code","err");
    		return new ResponseEntity<>(resultMap , HttpStatus.OK);
    		
    	}

    	if(userRequestVo.getPwd()==null || "".equals(userRequestVo.getPwd())) {

    		resultMap.put("msg","패스워드는 필수입니다.");
    		resultMap.put("code","err");
    		return new ResponseEntity<>(resultMap , HttpStatus.OK);
    	}

    	UserResponseVo checkDossUser = userService.checkDossUser(userRequestVo);

    	if("Y".equals(checkDossUser.getDossUserYn())) {
        	resultMap.put("msg", "");
        	resultMap.put("code", "OK");
        	resultMap.put("result", "Y");
    	}else {
        	resultMap.put("msg", "");
        	resultMap.put("code", "OK");
        	resultMap.put("result", "N");
    	}

    	return new ResponseEntity<>(resultMap , HttpStatus.OK);
    }
    

    @ApiOperation(value = "본인인증 상태 변경", httpMethod = "POST", notes = "본인 인증 상태 변경 API")
    @PostMapping(value="/api/v1/user/userCertiStatusChange")
    public ResponseEntity<Object> userCertiStatusChange(@RequestBody UserRequestVo userRequestVo){
    	Map<String , Object> resultMap = new HashMap<>();

    	if(userRequestVo.getDossId()==null || "".equals(userRequestVo.getDossId())) {
    		resultMap.put("code", "err");
    		resultMap.put("msg", "doss id를 확인해주세요.");
    	}

    	if(userRequestVo.getIdentCheckYn()==null || "".equals(userRequestVo.getIdentCheckYn()) ) {
    		userRequestVo.setIdentCheckYn("Y");;
    	}

    	try {
    		userService.userCertiStatusChange(userRequestVo);
    		resultMap.put("code", "OK");
    		resultMap.put("msg", "");
    	}catch(Exception e) {
    		log.debug(e.getMessage());
    		resultMap.put("code", "err");
    		resultMap.put("msg", "본인인증 상태 변경중 오류발생 . 관리자에게 문의하세요.");
    	}

    	return new ResponseEntity<>(resultMap , HttpStatus.OK);
    }

    @ApiOperation(value = "본인인증 대상자 확인", httpMethod = "GET", notes = "본인 인증 대상자 확인 API")
    @GetMapping(value="/api/v1/user/checkUserCertiStatus/{dossId}")
    public ResponseEntity<Object> checkUserCertiStatus(@PathVariable String dossId){
    	Map<String , Object> resultMap = new HashMap<>();

    	if(dossId==null || "".equals(dossId)) {

    		resultMap.put("msg","doss_id는 필수입니다.");
    		resultMap.put("code","err");
    		return new ResponseEntity<>(resultMap , HttpStatus.OK);
    	}

    	Map<String , Object> certigMap = new HashMap();

    	UserResponseVo respVo = userService.checkUserCertiStatus(dossId);
    	
    	System.out.println(respVo + " : respVo ");
    	
    	if("N".equals(respVo.getDossId())) {
        	resultMap.put("code", "err");
        	resultMap.put("msg", "doss_id가 존재하지 않습니다.");
        	resultMap.put("certiStatus", "");
        	return new ResponseEntity<>(resultMap , HttpStatus.OK);
    	}

    	resultMap.put("code", "OK");
    	resultMap.put("msg", "");
    	resultMap.put("certiStatus", respVo.getIdentCheckYn());

    	return new ResponseEntity<>(resultMap , HttpStatus.OK);
    }

    
    @ApiOperation(value = "DOSS ID 채번", httpMethod = "GET", notes = "신규 DOSS ID 채번")
    @GetMapping(value="/api/v1/user/makeDossId")
    public ResponseEntity<Object> makeDossId(){
    	Map<String , Object> resultMap = new HashMap<>();
    	
    	String dossSeq = userService.selectNextDossSeq();
    	
    	//String newSeq = "D" + Integer.toString(dossSeq) ;
    	
    	String newSeq = "bp" + dossSeq;
    	
    	log.debug(newSeq + "new seq");
    	
    	Map<String , Object> seqMap = new HashMap<String , Object>();
    	
    	seqMap.put("doss_id_seq", dossSeq);
    	
    	Map<String , Object> updSeqRst = userService.updateDossSeq(seqMap);
    	
    	if(!"OK".equals(updSeqRst.get("code"))) {
    		
    		resultMap.put("code", "err");
    		resultMap.put("msg", "doss id 채번 중 오류 발생. 관리자에게 문의하세요");
    		return new ResponseEntity<>(resultMap , HttpStatus.OK);
    	}
    	
    	resultMap.put("code", "OK");
    	resultMap.put("msg", "");
    	resultMap.put("nextDossId", newSeq);	
    	
    	return new ResponseEntity<>(resultMap , HttpStatus.OK);
    }  
    
    @ApiOperation(value = "login 이력 등록", httpMethod = "POST", notes = "login 이력 등록")
    @PostMapping(value="/api/v1/user/insertUserLoginHst")
    public ResponseEntity<Object> insertUserLoginHst(@RequestBody UserRequestVo userRequestVo){
    	
    	System.out.println(userRequestVo);
    	//log.debug(param);
    	
    	Map<String , Object> resultMap = new HashMap<>();
    	
    	if(userRequestVo.getDossId()==null || "".equals(userRequestVo.getDossId())) {
    		resultMap.put("code", "err");
    		resultMap.put("msg", "doss_id는 필수입니다.");
    		return new ResponseEntity<>(resultMap , HttpStatus.OK);
    		
    	}
    	
    	Map<String, Object> instLoingMap = userService.insertUserLoginHst(userRequestVo);
    	
    	if(!"OK".equals(instLoingMap.get("code"))) {
    		
    		resultMap.put("code", "err");
    		resultMap.put("msg", "user login hst 이력 추가 중 오류발생. 관리자에게 문의하세요");
    		return new ResponseEntity<>(resultMap , HttpStatus.OK);
    	}
    	
    	resultMap.put("code", "OK");
    	resultMap.put("msg", "");
    	
    	return new ResponseEntity<>(resultMap , HttpStatus.OK);
    }
    
    @ApiOperation(value = "asis login 인증", httpMethod = "POST", notes = "asis login 인증 api")
    @PostMapping(value="/api/v1/user/certiAsisUser")
    public ResponseEntity<Object> certiAsisUser(@RequestBody UserRequestVo userRequestVo){
    	
    	System.out.println(userRequestVo);
    	//log.debug(param);
    	
    	Map<String , Object> resultMap = new HashMap<>();
    	
    	if(userRequestVo.getAsisId()==null || "".equals(userRequestVo.getAsisId())) {
    		resultMap.put("code", "err");
    		resultMap.put("msg", "asisId는 필수입니다.");
    		return new ResponseEntity<>(resultMap , HttpStatus.OK);
    		
    	}
    	
    	if(userRequestVo.getPwd()==null || "".equals(userRequestVo.getPwd())) {
    		resultMap.put("code", "err");
    		resultMap.put("msg", "pwd는 필수입니다.");
    		return new ResponseEntity<>(resultMap , HttpStatus.OK);
    		
    	}
    	
    	UserResponseVo userResponse = userService.certiAsisUser(userRequestVo);
    	
    	if("N".equals(userResponse.getAsisUserYn())) {
    		resultMap.put("msg", "asisId pwd가 일치하지 않습니다.");
    		resultMap.put("code", "err");
    		return new ResponseEntity<>(resultMap , HttpStatus.OK);
    	}
    	
    	resultMap.put("code", "OK");
    	resultMap.put("msg", "");
    	resultMap.put("dossId", userResponse.getDossId());
    	
    	return new ResponseEntity<>(resultMap , HttpStatus.OK);
    } 
    
    @ApiOperation(value = "정직원 정보 조회", httpMethod = "GET", notes = "정직원 정보 조회 api")
    @GetMapping(value="/api/v1/user/getSktMemberInfo/{empno}")
    public ResponseEntity<Object> getSktMemberInfo(@PathVariable String empno){
    	
    	System.out.println(empno + " == getSktMemberInfo == ");
    	//log.debug(param);
    	
    	Map<String , Object> resultMap = new HashMap<>();
    	
    	if(empno==null || "".equals(empno)) {
    		resultMap.put("code", "err");
    		resultMap.put("msg", "dossId는 필수입니다.");
    		return new ResponseEntity<>(resultMap , HttpStatus.OK);
    		
    	}
    	
    	UserResponseVo userResponse = userService.getSktMemberInfo(empno);
    	
    	resultMap.put("code", "OK");
    	resultMap.put("msg", "");
    	resultMap.put("result", userResponse);
    	
    	return new ResponseEntity<>(resultMap , HttpStatus.OK);
    }  
 
}

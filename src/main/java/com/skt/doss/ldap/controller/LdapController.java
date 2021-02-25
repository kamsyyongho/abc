package com.skt.doss.ldap.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import com.skt.doss.ldap.core.service.LdapGroupService;
import com.skt.doss.ldap.core.service.DossCompanyInfoService;
import com.skt.doss.ldap.core.service.DossGroupInfoService;
import com.skt.doss.ldap.core.service.DossProjectInfoService;
import com.skt.doss.ldap.core.service.IamToolAuthRuleService;
import com.skt.doss.ldap.core.service.IamUserInfoService;
import com.skt.doss.ldap.core.service.IamUserCredentialRecordService;
import com.skt.doss.ldap.core.service.DossGroupMemberAuthService;
import com.skt.doss.ldap.core.service.DossProjectMemberAuthService;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;


import com.skt.doss.ldap.core.object.command.IamUserInfoVo;
import com.skt.doss.ldap.core.object.command.IamUserCredentialRecordVo;
import com.skt.doss.portal.user.core.object.commad.UserRequestVo;
import com.skt.doss.portal.user.core.object.query.UserResponseVo;

@RestController
@RequestMapping("/api/v1/ldap")
public class LdapController {
	
	private static String baseDn = "dc=example,dc=org";
	private static String ldapServer = "localhost";
	
	private static final Logger log = LoggerFactory.getLogger(LdapController.class);
	
	@Value("${ldap.initail-context-factory}")
	private String initailContextFactory;
	
	@Value("${ldap.provider-url}")
	private String providerUrl;
	
	@Value("${ldap.security-authentication}")
	private String securityAuthentication;
		
	@Value("${ldap.security-principal}")
	private String securityPrincipal;
	
	@Value("${ldap.security-credentials}")
	private String securityCredentials;
	
	@Autowired
	LdapGroupService ldapGroupService;
	
	@Autowired
	DossCompanyInfoService dossCompanyInfoService;
	
	@Autowired
	DossGroupInfoService dossGroupInfoService;
	
	@Autowired
	DossProjectInfoService dossProjectInfoService;
	
	@Autowired
	IamToolAuthRuleService iamToolAuthRuleService;
	
	@Autowired
	IamUserInfoService iamUserInfoService;
	
	@Autowired
	IamUserCredentialRecordService iamUserCredentialRecordService;
	
	@Autowired
	DossGroupMemberAuthService dossGroupMemberAuthService;
	
	@Autowired
	DossProjectMemberAuthService dossProjectMemberAuthService;
	
	
	@ApiOperation(value="회사정보 조회", httpMethod="GET", notes="회사정보 조회")
	@GetMapping(value="/selectDossCompanyInfo")
	public ResponseEntity<Object> selectDossCompanyInfo(
				@RequestParam(value = "dossId") String dossId
	) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if(dossId==null || "".equals(dossId)) {
			resultMap.put("code", "err");
			resultMap.put("msg", "dossid가 없습니다.");
			resultMap.put("result", "");
			return new ResponseEntity<>(resultMap, HttpStatus.OK);
		}
		
		resultMap.put("code", "OK");
		resultMap.put("msg", "OK");
		//resultMap.put("result", dossCompanyInfoService.selectdfasfdadsf(dossId));
		
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
	
	
	@ApiOperation(value="회사정보 조회", httpMethod="POST", notes="회사정보 조회")
	@PostMapping(value="/insertLdapGroup")
	public ResponseEntity<Object> insertLdapGroup(@RequestBody Map<String, String> param) {
		
		//String dossId = (String) param.get("dossId");
		
		DirContext ctx = null;
		
		Properties props = new Properties();
		
		props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		props.setProperty(Context.PROVIDER_URL, "ldap://"+ ldapServer+":389");
		props.setProperty(Context.SECURITY_AUTHENTICATION, "simple");
		props.setProperty(Context.SECURITY_PRINCIPAL, "cn=admin,dc=example,dc=org");
		props.setProperty(Context.SECURITY_CREDENTIALS, "admin");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			ctx = new InitialDirContext(props);
			ldapGroupService.insertLdapGroup(baseDn, ctx, param); 
		} catch (Exception e) {
		}
		
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
		
	}
	
	
//	@GetMapping(value="/selectIamUserInfo")
//	public ResponseEntity<Object> selectIamUserInfo(
//			@RequestParam(value = "dossId") String dossId ){
	@ApiOperation(value="사용자 정보 조회", httpMethod="GET", notes="사용자 정보 조회")
    @GetMapping(value="/selectIamUserInfo/{dossId}")
    public ResponseEntity<Object> selectIamUserInfo(@PathVariable String dossId) throws Exception{
		
    	Map<String , Object> resultMap = new HashMap<String , Object>();
    	
    	if(dossId==null || "".equals(dossId)) {
    		resultMap.put("code", "err");
    		resultMap.put("msg", "doss id 정보가 없습니다.");
    		resultMap.put("result", "");
    		return  new ResponseEntity<>(resultMap, HttpStatus.OK);   		
    	}
		
		DirContext ctx = null;
		
		Properties props = new Properties();
		props.setProperty(Context.INITIAL_CONTEXT_FACTORY, initailContextFactory);
		props.setProperty(Context.PROVIDER_URL, providerUrl);
		props.setProperty(Context.SECURITY_AUTHENTICATION, securityAuthentication);
		props.setProperty(Context.SECURITY_PRINCIPAL, securityPrincipal);
		props.setProperty(Context.SECURITY_CREDENTIALS, securityCredentials);
		
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		try {
//			ctx = new InitialDirContext(props);
//			resultMap = iamUserInfoService.iamUserInfo(baseDn, ctx, dossId);
//		} catch (Exception e) {
//		}
//		
//		return new ResponseEntity<>(resultMap, HttpStatus.OK);
		
		UserResponseVo result = new UserResponseVo();
		try {
			ctx = new InitialDirContext(props);
			result = iamUserInfoService.iamUserInfo(baseDn, ctx, dossId);
		} catch (Exception e) {
		} finally {
			ctx.close();
		}
		
		resultMap.put("code", "OK");
		resultMap.put("msg", "");
		resultMap.put("result", result);
		
		return new ResponseEntity<>(resultMap, HttpStatus.OK);

		
	}
	
	@ApiOperation(value="사용자정보 조회2", httpMethod="POST", notes="사용자정보 조회2")
	@GetMapping(value="/selectIamUserInfo2")
	public ResponseEntity<Object> selectIamUserInfo2(@RequestBody Map<String, Object> param) {
		
		String dossId = (String) param.get("dossId");
		
		DirContext ctx = null;
		
		Properties props = new Properties();
		props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		props.setProperty(Context.PROVIDER_URL, "ldap://"+ ldapServer+":389");
		props.setProperty(Context.SECURITY_AUTHENTICATION, "simple");
		props.setProperty(Context.SECURITY_PRINCIPAL, "cn=admin,dc=example,dc=org");
		props.setProperty(Context.SECURITY_CREDENTIALS, "admin");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			ctx = new InitialDirContext(props);
			//resultMap = iamUserInfoService.iamUserInfo(baseDn, ctx, dossId);
		} catch (Exception e) {
		}
		
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
		
	}

	
	@ApiOperation(value="사용자 추가", httpMethod="POST", notes="사용자 추가")
	@PostMapping(value="/insertIamUserInfo")
	public ResponseEntity<Object> insertIamUserInfo(@RequestBody Map<String, String> param) {
		
		//String dossId = (String) param.get("dossId");
		
		DirContext ctx = null;
		
		Properties props = new Properties();
		props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		props.setProperty(Context.PROVIDER_URL, "ldap://"+ ldapServer+":389");
		props.setProperty(Context.SECURITY_AUTHENTICATION, "simple");
		props.setProperty(Context.SECURITY_PRINCIPAL, "cn=admin,dc=example,dc=org");
		props.setProperty(Context.SECURITY_CREDENTIALS, "admin");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			ctx = new InitialDirContext(props);
			iamUserInfoService.insertIamUserInfo(baseDn, ctx, param); 
		} catch (Exception e) {
		}
		
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
		
	}
	
	
	
    @ApiOperation(value = "회원정보 확인", httpMethod = "POST", notes = "Portal 회원 정보 id pwd 확인 API")
    @PostMapping(value="/checkDossUser")
    public ResponseEntity<Object> checkDossUser(@RequestBody UserRequestVo userRequestVo){


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


		DirContext ctx = null;
		
		Properties props = new Properties();
		props.setProperty(Context.INITIAL_CONTEXT_FACTORY, initailContextFactory);
		props.setProperty(Context.PROVIDER_URL, providerUrl);
		props.setProperty(Context.SECURITY_AUTHENTICATION, securityAuthentication);
		props.setProperty(Context.SECURITY_PRINCIPAL, securityPrincipal);
		props.setProperty(Context.SECURITY_CREDENTIALS, securityCredentials);
		
		
		UserResponseVo result = new UserResponseVo();
		IamUserCredentialRecordVo iamUserCredentialRecordVo = new IamUserCredentialRecordVo();
		String dossUserYn = "N";
		
		try {
			ctx = new InitialDirContext(props);
			result = iamUserInfoService.iamUserInfo(baseDn, ctx, userRequestVo.getDossId());
			
			if (result.getDossId() != null) {
				iamUserCredentialRecordVo = iamUserCredentialRecordService.iamUserCredentialRecord(baseDn, ctx, userRequestVo.getDossId(), userRequestVo.getPwd());

				if (iamUserCredentialRecordVo.getDossId() != null) {
					dossUserYn = "Y";
				}
			} 
		} catch (Exception e) {
		} finally {
			try {
				ctx.close();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	
    	if("Y".equals(dossUserYn)) {
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
	
}
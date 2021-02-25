package com.skt.doss.common.utils;

import com.redbc.rp.internal.message.InternalJsonMessage;
import com.redbc.serveragent.client.UAF;
import com.skt.doss.portal.fido.core.object.commad.FidoRequestVo;

import java.util.HashMap;
import java.util.Map;

public class FidoCerti {
	/*
	@Value("${fido.appId}")
	String appid;
	@Value("${fido.targetUrl}")
	String targetUrl;
	@Value("${fido.accesskey}")
	*/
	
	String appid = "https://fido-dev.sktelecom.com:9031/tdeappid";
//	String appid = "https://fido.sktelecom.com:9031/tdeappid"; //#prod
	String baseTargetUrl = "https://fido-dev.sktelecom.com:9031";
//	String baseTargetUrl = "https://fido.sktelecom.com:9031";	//#prod
//	String accesskey = "5c42ffc4da69c1fe32774657014a783be073d41acd1c6679fe9617c6195ebea7";
	String accesskey = "89115bf539e3953b5fa368243d0add95845db6a49ab590936a4c28d3d374a2e5"; //#prod
	String websessionid = "121011";
	
	String userId;
	//String revSession;
	
	public Map<String , Object> fidoUserReg(UAF uaf , Map<String, Object> inParam){
		
		String revErrorCode;
		String revErrorMsg;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		/*
		InternalJsonMessage regPushRev =  isRegPushInfoIMObj(uaf , inParam);
		
		System.out.println(" == aft isRegPushInfoIMObj == ");
		revErrorCode = regPushRev.getErrorcode();
		
		if(ErrorCodeDefine.E_TYPE_SUCCESS_STR.equals(revErrorCode)){
			System.out.println("이미 사용등록되었습니다.");
			resultMap.put("resultMsg", "이미 사용등록되었습니다.");
			return resultMap;
		}
	
		InternalJsonMessage activeRev =   getActiveCodeIMObj(uaf , inParam);
		
		System.out.println(" == aft getActiveCodeIMObj == ");
		if(activeRev == null){
			System.out.println("revMsgObj is null");
			resultMap.put("resultMsg", "revMsgObj is null");
			return resultMap;
		}
		
		revErrorCode = activeRev.getErrorcode();
		revErrorMsg = activeRev.getErrormessage();
		
		if(!ErrorCodeDefine.E_TYPE_SUCCESS_STR.equals(revErrorCode)){
			System.out.println("revErrorMsg : " + revErrorMsg);
			resultMap.put("resultMsg", revErrorMsg);
			return resultMap;
		}
		
		revSession = (String)activeRev.getSessionid();
		System.out.println(activeRev.getActivecode() + " : activecode ");
		System.out.println(revSession + " : revSession ");
		
		InternalJsonMessage waitMobileRev = waitMobileUseIMObj(uaf , inParam);
		
		System.out.println(" == aft waitMobileUseIMObj == ");
		
		if(waitMobileRev == null){
			System.out.println("revUseMsgObj is null ");
			resultMap.put("resultMsg", "revUseMsgObj is null" );
			return resultMap;
		}

		revErrorCode = waitMobileRev.getErrorcode();
		revErrorMsg = waitMobileRev.getErrormessage();

		if(!ErrorCodeDefine.E_TYPE_SUCCESS_STR.equals(revErrorCode)){
		System.out.println("revErrorMsg : " + revErrorMsg);
		resultMap.put("resultMsg", revErrorMsg );
		return resultMap;
		}

		*/
		resultMap.put("resultMsg", "사용등록 완료");

		return resultMap;
	}
	
	/*
	public Map<String , Object> fidoAuthentication(UAF uaf , Map<String, Object> inParam){
		String revErrorCode;
		String revErrorMsg;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		InternalJsonMessage authentiRev =  authenticationRequestFromRPMObj(uaf , inParam);
		
		revErrorCode = authentiRev.getErrorcode();
		revErrorMsg = authentiRev.getErrormessage();

		if(!ErrorCodeDefine.E_TYPE_SUCCESS_STR.equals(revErrorCode)){
			System.out.println("revErrorMsg : " + revErrorMsg);
			resultMap.put("resultMsg", revErrorMsg);
			return resultMap;
		}
		System.out.println("FIDO UAF 인증 성공");
		resultMap.put("resultMap", "FIDO UAF 인증 성공");
	
		return resultMap;
	}	*/
	
	public InternalJsonMessage isRegPushInfoIMObj(UAF uaf , String dossId) {
		
		//String userid = (String)inParam.get("userid");
		String userid = dossId;
		String targetUrl = baseTargetUrl + "/isRegPushInfoFromRP";
		
		System.out.println(targetUrl + "/" + userid + "/" + appid + "/" + accesskey);
		return uaf.isRegPushInfoIMObj(targetUrl, userid, appid, accesskey);
	}
	
	public InternalJsonMessage getActiveCodeIMObj(UAF uaf , FidoRequestVo fidoRequestVo) {
		String targetUrl = baseTargetUrl + "/getActiveCodeFromRP";
		
		//String userid = (String)inParam.get("userid");
		String userid = fidoRequestVo.getDossId();
		
		String phoneno = fidoRequestVo.getPhoneNo();
		
		//String accesskey="";
		
		return uaf.getActiveCodeIMObj(targetUrl, userid, appid, websessionid, phoneno, accesskey);
	}
	
	public InternalJsonMessage waitMobileUseIMObj(UAF uaf , FidoRequestVo fidoRequestVo) {
		String targetUrl = baseTargetUrl + "/waitMobileUseFromRP";
		
		String userid = fidoRequestVo.getDossId();
		
		String phoneno = fidoRequestVo.getPhoneNo();
		
		String revSession = fidoRequestVo.getRevSession();

		return uaf.waitMobileUseIMObj(targetUrl, userid, appid, websessionid,revSession,phoneno,accesskey);
	}
	
	public InternalJsonMessage registrationRequestFromRPMObj(UAF uaf , String dossId) {
		String targetUrl = baseTargetUrl + "/REGISTRATIONREQUESTFROMRP";
		String userid = dossId;
		
		return uaf.registrationRequestFromRPMObj(targetUrl, userid, appid, websessionid,accesskey);
	}
	
	public InternalJsonMessage authenticationRequestFromRPMObj(UAF uaf , String dossId) {
		String targetUrl = baseTargetUrl + "/AUTHENTICATIONREQUESTFROMRP";
		String userid = dossId;
		
		return uaf.authenticationRequestFromRPMObj(targetUrl, userid, appid, websessionid,accesskey);
	}

	public InternalJsonMessage deregistrationRequestFromRPMObj(UAF uaf, String dossId){
		String targetUrl = baseTargetUrl + "/DEREGISTRATIONREQUESTFROMRP";
		String userid = dossId;
		return uaf.deregistrationRequestFromRPMObj(targetUrl, userid, appid, websessionid,accesskey);
	}
}

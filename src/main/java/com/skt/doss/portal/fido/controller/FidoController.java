package com.skt.doss.portal.fido.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.redbc.common.error.ErrorCodeDefine;
import com.redbc.rp.internal.message.InternalJsonMessage;
import com.redbc.serveragent.client.UAF;
import com.skt.doss.common.utils.CommUtil;
import com.skt.doss.common.utils.FidoCerti;
import com.skt.doss.portal.fido.core.object.commad.FidoRequestVo;

import io.swagger.annotations.ApiOperation;

@RestController
public class FidoController {
	
	FidoCerti fidoCerti = new FidoCerti();
	CommUtil commUtil = new CommUtil();
	
    @ApiOperation(value = "uaf초기화", httpMethod = "GET", notes = "uafSdk 초기화 api")
    @GetMapping(value="/api/v1/fido/regUAFSdk")
    public ResponseEntity<Object> regUAFSdk(@RequestParam Map<String, Object> param){
    	Map<String,Object> resultMap = new HashMap<String, Object>();
		System.out.println(" == regUAFSdk == ");
		UAF uaf = new UAF();
		uaf.Init("uafsdk.properties");
    	
		resultMap = fidoCerti.fidoUserReg(uaf , param);
		
    	return  new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
    
    
    @ApiOperation(value = "fido 등록여부 확인", httpMethod = "GET", notes = "fido 등록여부 확인 api")
    @GetMapping(value="/api/v1/fido/isRegPushInfo/{dossId}")
    public ResponseEntity<Object> isRegPushInfo(@PathVariable String dossId) throws Exception{
    	//UAF uaf= fidoCerti.fidoInit();
    	
    	Map<String,Object> resultMap = new HashMap<String, Object>();
		String revErrorCode;
		String revErrorMsg;
		
		UAF uaf = new UAF();
		//String propFile = getClass().getResource("/fido/uafsdk.properties").toString();
		
		//System.out.println(propFile + " ??? ");
		
		uaf.Init("uafsdk.properties");
		
		System.out.println(commUtil.stringToUpper(dossId) + " : dossId ");
		
		InternalJsonMessage regPushRev =  fidoCerti.isRegPushInfoIMObj(uaf , commUtil.stringToUpper(dossId));
		
		System.out.println(" == aft isRegPushInfoIMObj == ");
		revErrorCode = regPushRev.getErrorcode();
		
		if(ErrorCodeDefine.E_TYPE_SUCCESS_STR.equals(revErrorCode)){
			System.out.println("이미 사용등록되었습니다.");
			resultMap.put("msg", "이미 사용등록되었습니다.");
			resultMap.put("code", "err");
			return  new ResponseEntity<>(resultMap, HttpStatus.OK);
		}
		
		resultMap.put("msg", "사용가능한 ID입니다.");
		resultMap.put("code", "OK");
    	
    	return  new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
    
    @ApiOperation(value = "fido 활성화 코드  요청", httpMethod = "POST", notes = "fido 활성화 코드  요청 api")
    @PostMapping(value="/api/v1/fido/getActiveCode")
    public ResponseEntity<Object> getActiveCode(@RequestBody FidoRequestVo fidoRequestVo) throws Exception{
    	
    	System.out.println(fidoRequestVo);
    	
    	Map<String,Object> resultMap = new HashMap<String, Object>();
		String revErrorCode;
		String revErrorMsg;
		
    	if(fidoRequestVo.getDossId() == null || "".equals(fidoRequestVo.getDossId())) {
    		resultMap.put("msg", "doss id는 필수입니다.");
    		resultMap.put("code", "err");
    		return new ResponseEntity<>(resultMap, HttpStatus.OK);
    	}
    	
    	if(fidoRequestVo.getPhoneNo() == null || "".equals(fidoRequestVo.getPhoneNo())) {
    		resultMap.put("msg", "전화번호는 필수입니다.");
    		resultMap.put("code", "err");
    		return new ResponseEntity<>(resultMap, HttpStatus.OK);   		
    	}
    	
    	fidoRequestVo.setDossId(commUtil.stringToUpper(fidoRequestVo.getDossId()));
    	
    	System.out.println(fidoRequestVo.getDossId() + " : dossId ");
		
		UAF uaf = new UAF();
		
		uaf.Init("uafsdk.properties");
		
		InternalJsonMessage revObj =  fidoCerti.getActiveCodeIMObj(uaf , fidoRequestVo);
		
		
		if(revObj == null){
			System.out.println("revMsgObj is null");
			resultMap.put("msg", "revMsgObj is null");
			resultMap.put("code", "err");
			return new ResponseEntity<>(resultMap, HttpStatus.OK);
		}
		
		revErrorCode = revObj.getErrorcode();
		revErrorMsg = revObj.getErrormessage();
		
		if(!ErrorCodeDefine.E_TYPE_SUCCESS_STR.equals(revErrorCode)){
			System.out.println("revErrorMsg : " + revErrorMsg);
			resultMap.put("msg", revErrorMsg);
			resultMap.put("code", "err");
			return new ResponseEntity<>(resultMap, HttpStatus.OK);
		}
		
		System.out.println(revObj.getSessionid() + " / " + revObj.getActivecode());
		
		resultMap.put("msg", "");
		resultMap.put("code", "OK");
		resultMap.put("revSession", revObj.getSessionid());
		resultMap.put("activecode", revObj.getActivecode());
		
    	return  new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
    
    @ApiOperation(value = "fido 활성화 코드  요청 대기", httpMethod = "POST", notes = "fido 활성화 코드 요청 대기 api")
    @PostMapping(value="/api/v1/fido/waitMobileUse")
    public ResponseEntity<Object> waitMobileUse(@RequestBody FidoRequestVo fidoRequestVo) throws Exception{
    	
    	System.out.println(fidoRequestVo);
    	
    	Map<String,Object> resultMap = new HashMap<String, Object>();
		String revErrorCode;
		String revErrorMsg;
		
		UAF uaf = new UAF();
		
		uaf.Init("uafsdk.properties");
		
    	if(fidoRequestVo.getDossId() == null || "".equals(fidoRequestVo.getDossId())) {
    		resultMap.put("msg", "doss id는 필수입니다.");
    		resultMap.put("code", "err");
    		return new ResponseEntity<>(resultMap, HttpStatus.OK);
    	}
    	
    	if(fidoRequestVo.getPhoneNo() == null || "".equals(fidoRequestVo.getPhoneNo())) {
    		resultMap.put("msg", "전화번호는 필수입니다.");
    		resultMap.put("code", "err");
    		return new ResponseEntity<>(resultMap, HttpStatus.OK);   		
    	}
    	
    	if(fidoRequestVo.getRevSession() == null || "".equals(fidoRequestVo.getRevSession())) {
    		resultMap.put("msg", "활성화 session id는 필수입니다.");
    		resultMap.put("code", "err");
    		return new ResponseEntity<>(resultMap, HttpStatus.OK);   		
    	}
    	
    	//doss id 대문자로 변환
    	fidoRequestVo.setDossId(commUtil.stringToUpper(fidoRequestVo.getDossId()));
    	System.out.println(fidoRequestVo.getDossId() + " : dossId ");
    	
    	InternalJsonMessage revObj =  fidoCerti.waitMobileUseIMObj(uaf , fidoRequestVo);
		
		if(revObj == null){
			System.out.println("revUseMsgObj is null ");
			resultMap.put("code", "err" );
			resultMap.put("msg", "revUseMsgObj is null" );
			return  new ResponseEntity<>(resultMap, HttpStatus.OK);
		}

		revErrorCode = revObj.getErrorcode();
		revErrorMsg = revObj.getErrormessage();

		if(!ErrorCodeDefine.E_TYPE_SUCCESS_STR.equals(revErrorCode)){
			System.out.println("revErrorMsg : " + revErrorMsg);
			resultMap.put("msg", revErrorMsg );
			resultMap.put("code", "err" );
			return  new ResponseEntity<>(resultMap, HttpStatus.OK);
		} 
		
		resultMap.put("msg", "" );
		resultMap.put("code", "OK" );
    	
		return  new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
    
    
    @ApiOperation(value = "사용등록", httpMethod = "GET", notes = "fido 사용 등록 api")
    @GetMapping(value="/api/v1/fido/regFidoUser")
    public ResponseEntity<Object> isRegPushInfoIMObj(@RequestParam Map<String, Object> param) throws Exception{
    	
    	Map<String,Object> resultMap = new HashMap<String, Object>();
    	
		System.out.println(" == isRegPushInfoIMObj == ");
		
		UAF uaf = new UAF();
		
		uaf.Init("uafsdk.properties");
		
		fidoCerti.fidoUserReg(uaf, param);
		
		/*
		UAF uaf = new UAF();
		
		uaf.Init("uafsdk.properties");
		
		System.out.println(uaf.getSdkEnv());
		
		String targetUrl = "https://fido-dev.sktelecom.com:9031/AUTHENTICATIONREQUESTFROMRP";
		String userid = "P169842";
		String appid = "https://fido.sktelecom.com:9031/tdeappid";
		String websessionid = "12123455";
		String accesskey = "5c42ffc4da69c1fe32774657014a783be073d41acd1c6679fe9617c6195ebea7"; 
		InternalJsonMessage authRev = uaf.authenticationRequestFromRPMObj(targetUrl, userid, appid, websessionid,accesskey);
		
		System.out.println(authRev.getErrormessage());
		*/
		
		//callFido();
    	return  new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

	@ApiOperation(value = "사용삭", httpMethod = "GET", notes = "fido 사용 삭 api")
	@GetMapping(value="/api/v1/fido/deleteFidoUser/{dossId}")
	public ResponseEntity<Object> deregistrationRequestFromRPMObj(@PathVariable String dossId) throws Exception{

//		Map<String,Object> resultMap = new HashMap<String, Object>();

		System.out.println(" == isRegPushInfoIMObj == ");

		UAF uaf = new UAF();

		uaf.Init("uafsdk.properties");

		fidoCerti.deregistrationRequestFromRPMObj(uaf,dossId );

		/*
		UAF uaf = new UAF();

		uaf.Init("uafsdk.properties");

		System.out.println(uaf.getSdkEnv());

		String targetUrl = "https://fido-dev.sktelecom.com:9031/AUTHENTICATIONREQUESTFROMRP";
		String userid = "P169842";
		String appid = "https://fido.sktelecom.com:9031/tdeappid";
		String websessionid = "12123455";
		String accesskey = "5c42ffc4da69c1fe32774657014a783be073d41acd1c6679fe9617c6195ebea7";
		InternalJsonMessage authRev = uaf.authenticationRequestFromRPMObj(targetUrl, userid, appid, websessionid,accesskey);

		System.out.println(authRev.getErrormessage());
		*/

		//callFido();
		Map<String,Object> resultMap = new HashMap<String, Object>();
		String revErrorCode;
		String revErrorMsg;
		InternalJsonMessage revObj =  fidoCerti.deregistrationRequestFromRPMObj(uaf , commUtil.stringToUpper(dossId));

		revErrorCode = revObj.getErrorcode();
		revErrorMsg = revObj.getErrormessage();
		if(!ErrorCodeDefine.E_TYPE_SUCCESS_STR.equals(revErrorCode)){
			System.out.println("revErrorMsg : " + revErrorMsg);
			resultMap.put("msg", revErrorMsg );
			resultMap.put("code", "err" );
			return  new ResponseEntity<>(resultMap, HttpStatus.OK);
		}

		resultMap.put("msg", "Registration 성공.");
		resultMap.put("code", "OK");

		return  new ResponseEntity<>(resultMap, HttpStatus.OK);
	}


	@ApiOperation(value = "fido Registration ", httpMethod = "GET", notes = "fido Registration  api")
    @GetMapping(value="/api/v1/fido/registrationRequestFrom/{dossId}")
    public ResponseEntity<Object> registrationRequestFrom(@PathVariable String dossId) throws Exception{
    
    	Map<String,Object> resultMap = new HashMap<String, Object>();
		String revErrorCode;
		String revErrorMsg;
		
		UAF uaf = new UAF();
		
		uaf.Init("uafsdk.properties");
    	
		System.out.println(commUtil.stringToUpper(dossId));
		
		InternalJsonMessage revObj =  fidoCerti.registrationRequestFromRPMObj(uaf , commUtil.stringToUpper(dossId));
		
		revErrorCode = revObj.getErrorcode();
		revErrorMsg = revObj.getErrormessage();

		if(!ErrorCodeDefine.E_TYPE_SUCCESS_STR.equals(revErrorCode)){
			System.out.println("revErrorMsg : " + revErrorMsg);
			resultMap.put("msg", revErrorMsg );
			resultMap.put("code", "err" );
			return  new ResponseEntity<>(resultMap, HttpStatus.OK);
		}
		
		resultMap.put("msg", "Registration 성공.");
		resultMap.put("code", "OK");
		
    	return  new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
    
    @ApiOperation(value = "fido 인증", httpMethod = "GET", notes = "fido 인증 api")
    @GetMapping(value="/api/v1/fido/fidoAuthentication/{dossId}")
    public ResponseEntity<Object> fidoAuthentication(@PathVariable String dossId) throws Exception{
    	String revErrorCode;
    	String revErrorMsg;
    	Map<String,Object> resultMap = new HashMap<String, Object>();
    	
		System.out.println(" == fidoAuthentication == ");
		UAF uaf = new UAF();
		
		uaf.Init("uafsdk.properties");
		
		System.out.println(commUtil.stringToUpper(dossId));
		
		InternalJsonMessage revObj =  fidoCerti.authenticationRequestFromRPMObj(uaf , commUtil.stringToUpper(dossId));
		
		revErrorCode = revObj.getErrorcode();
		revErrorMsg = revObj.getErrormessage();

		if(!ErrorCodeDefine.E_TYPE_SUCCESS_STR.equals(revErrorCode)){
			System.out.println("revErrorMsg : " + revErrorMsg);
			resultMap.put("msg", revErrorMsg);
			resultMap.put("code", "err");
			return  new ResponseEntity<>(resultMap, HttpStatus.OK);
		}
		
		System.out.println("FIDO UAF 인증 성공");
		resultMap.put("msg", "FIDO UAF 인증 성공");
		resultMap.put("code", "OK");
		
    	return  new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
    
    /*
    private void callFido() {
		UAF uaf = new UAF();
		
		uaf.Init("uafsdk.properties");
    	
        //SdkEnv sdkenv = SdkEnv.getInstance();
        //sdkenv.Init("uafsdk.properties");
		
    	String userid = "P169842";
    	String appid = "https://fido-dev.sktelecom.com:9031/tdeappid";
    	String websessionid = "12123455";
    	String accesskey = "5c42ffc4da69c1fe32774657014a783be073d41acd1c6679fe9617c6195ebea7"; 
    	String targetUrl = "https://fido-dev.sktelecom.com:9031/AUTHENTICATIONREQUESTFROMRP";
    	
    	InternalJsonMessage objRet = null;
        SdkEnv sdkenv = SdkEnv.getInstance();
        if(!sdkenv.checkEnv())
            return ;
        String strRetJson = null;
        ObjectMapper mapper = new ObjectMapper();
        InternalJsonMessage msg = new InternalJsonMessage();
        msg.setVersion("1.0");
        msg.setSource(InternalJsonMessageDefine.DIRECTION_FIDOSDK);
        msg.setTarget(InternalJsonMessageDefine.DIRECTION_FIDOSERVERAGENT);
        msg.setOperation("auth");
        msg.setAuthenticationmode((new StringBuilder(String.valueOf(UAFV1TLVDdataDefine.AUTHENTICATIONMODE_AUTH))).toString());
        msg.setUserid(userid);
        msg.setAppid(appid);
        msg.setRpwebsession(websessionid);
        msg.setAccesskey(accesskey);
        try
        {
            mapper.setSerializationInclusion(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL);
            String strSendJson = mapper.writeValueAsString(msg);
            //UafSdkHttpUtill http = new UafSdkHttpUtill();
            //strRetJson = http.httpSend(targetUrl, strSendJson, SdkEnv.getSSL_KEYSTORE_PATH_UAFSDK(), SdkEnv.getSSL_KEYSTORE_PASSWORD_UAFSDK());
            System.out.println(SdkEnv.getSSL_KEYSTORE_PATH_UAFSDK() + " ; SdkEnv.getSSL_KEYSTORE_PATH_UAFSDK()");
            System.out.println(SdkEnv.getSSL_KEYSTORE_PASSWORD_UAFSDK() + " : SdkEnv.getSSL_KEYSTORE_PASSWORD_UAFSDK()");
            strRetJson = httpSend(targetUrl, strSendJson, SdkEnv.getSSL_KEYSTORE_PATH_UAFSDK(), SdkEnv.getSSL_KEYSTORE_PASSWORD_UAFSDK());
            if(strRetJson != null)
            {
                ObjectMapper mapperRev = new ObjectMapper();
                mapperRev.setSerializationInclusion(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL);
                //objRet = (InternalJsonMessage)mapper.readValue(strRetJson, com/redbc/rp/internal/message/InternalJsonMessage);
            } else
            {
                int errorcode = 0x6000001;
                //objRet = InternalError.getMessageIEObj(errorcode, "(from FIDO SDK - authenticationRequestFromRPMObj)", InternalJsonMessageDefine.DIRECTION_FIDOSERVERAGENT.intValue(), InternalJsonMessageDefine.DIRECTION_FIDOSDK.intValue());
            }
        }
        catch(JsonProcessingException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        	
    	
    }
    
    
    private String httpSend(String strTarget, String strBody, String strKeystorePath, String strKeystorePassword)
    {
        String strMsg = null;
        String strTargetLow = strTarget.toLowerCase();
        boolean bSslUsage;
        if(strTargetLow.startsWith("https://"))
            bSslUsage = true;
        else
            bSslUsage = false;
        //this.strKeystorePath = strKeystorePath;
        //this.strKeystorePassword = strKeystorePassword;
        //if(bSslUsage)
            strMsg = httpsPostSend(strTargetLow, strBody , strKeystorePath , strKeystorePassword);
        //else
            //strMsg = httpPostSend(strTargetLow, strBody);
        return "";
    }
    
    public String httpsPostSend(String request, String postData, String strKeystorePath , String strKeystorePassword)
    {
        String result;
        CloseableHttpResponse response;
        IOException e;
        result = null;
        javax.net.ssl.SSLContext sslcontext = null;
        try
        {
            KeyStore keyStore = KeyStore.getInstance("jks");
            keyStore.load(new FileInputStream(strKeystorePath), strKeystorePassword.toCharArray());
            sslcontext = SSLContexts.custom().loadTrustMaterial(keyStore, null).build();
        }
        catch(KeyManagementException e1)
        {
            System.out.println(e1.getMessage());
            e1.printStackTrace();
        }
        catch(NoSuchAlgorithmException e1)
        {
        	System.out.println(e1.getMessage());
            e1.printStackTrace();
        }
        catch(KeyStoreException e1)
        {
        	System.out.println(e1.getMessage());
            e1.printStackTrace();
        }
        catch(CertificateException e2)
        {
        	System.out.println(e2.getMessage());
            e2.printStackTrace();
        }
        catch(FileNotFoundException e2)
        {
        	System.out.println(e2.getMessage());
            e2.printStackTrace();
        }
        catch(IOException e2)
        {
        	System.out.println(e2.getMessage());
            e2.printStackTrace();
        }
        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslcontext, new String[] {
            "TLSv1", "SSLv3"
        }, null, new NoopHostnameVerifier());
        org.apache.http.config.Registry socketFactoryRegistry = RegistryBuilder.create().register("http", PlainConnectionSocketFactory.INSTANCE).register("https", sslConnectionSocketFactory).build();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory).setConnectionManager(cm).build();
        HttpPost httpPost = new HttpPost(request);
        ByteArrayEntity postDataEntity = new ByteArrayEntity(postData.getBytes());
        httpPost.setEntity(postDataEntity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json;charset=utf8");
        response = null;
        try
        {
            response = httpclient.execute(httpPost);
        }
        // Misplaced declaration of an exception variable
        catch(IOException e2)
        {
        	System.out.println(e2.getMessage());
            e2.printStackTrace();
        }
        
        System.out.println(response + " : response");
        
        try
        {
            response.close();
        }
        catch(IOException e2)
        {
        	System.out.println(e2.getMessage());
            e2.printStackTrace();
        }
      
        return result;
    }
    */
}

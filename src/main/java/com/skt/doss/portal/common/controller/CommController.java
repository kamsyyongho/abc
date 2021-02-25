package com.skt.doss.portal.common.controller;

import com.skt.doss.common.utils.CommUtil;
import com.skt.doss.portal.common.core.object.query.CommCodeResponseVo;
import com.skt.doss.portal.common.core.object.query.CompanyResponseVo;
import com.skt.doss.portal.common.core.service.CommService;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CommController {
	
	@Autowired
    CommService commService;
	
	CommUtil commUtil = new CommUtil();
	
    @ApiOperation(value = "공통코드 조회", httpMethod = "GET", notes = "특정 공통 코드 조회")
    @GetMapping(value="/api/v1/comm/searchCommCodeList/{codeGroupId}")
    public ResponseEntity<Object> searchCommCodeList(@PathVariable String codeGroupId){
    	
    	Map<String  , Object> resultMap = new HashMap<String, Object>();
    	
    	if(codeGroupId==null || "".equals(codeGroupId)) {
    		resultMap.put("code", "err");
    		resultMap.put("msg", "group_id는 필수입니다.");
    		resultMap.put("codeList", null);
    		return  new ResponseEntity<>(resultMap, HttpStatus.OK);
    	}
    	
    	List<CommCodeResponseVo> codeList = commService.searchCommCodeList(codeGroupId);
    	
    	if(codeList.size() <= 0 ) {
    		resultMap.put("code", "");
    		resultMap.put("msg", "선택한 공통 코드가 존재하지 않습니다.");
    		resultMap.put("codeList", null);
    		return  new ResponseEntity<>(resultMap, HttpStatus.OK);
    	}
    	
		resultMap.put("code", "OK");
		resultMap.put("msg", "");
		resultMap.put("codeList", codeList);
		return  new ResponseEntity<>(resultMap, HttpStatus.OK);
    	
    }
    
    @ApiOperation(value = "공통코드 조회", httpMethod = "GET", notes = "모든 공통코드 조회")
    @GetMapping(value="/api/v1/comm/searchCommCodeAllList")
    public ResponseEntity<Object> searchCommCodeAllList(){
    	
    	Map<String  , Object> resultMap = new HashMap<String, Object>();
    	
    	List<CommCodeResponseVo> codeList = commService.searchCommCodeAllList();

 
		resultMap.put("code", "OK");
		resultMap.put("msg", "");
		resultMap.put("codeList", codeList);
		return  new ResponseEntity<>(resultMap, HttpStatus.OK);
    	
    }
    
    @ApiOperation(value = "공통그룹 코드 조회", httpMethod = "GET", notes = "doss 공통코드 조회")
    @GetMapping(value="/api/v1/comm/searchCommGroupCode")
    public ResponseEntity<Object> searchCommGroupCodeList(){
    	
    	Map<String , Object> resultMap = new HashMap<String, Object>();
    	
    	List<Map<String , Object>> groupCodeList = commService.searchCommGroupCodeList();
    	
		resultMap.put("code", "OK");
		resultMap.put("msg", "");
		resultMap.put("groupCodeList", groupCodeList);
    	
    	return  new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
    
    @ApiOperation(value = "회사정보 조회", httpMethod = "GET", notes = "회사 리스트 조회")
    @GetMapping(value="/api/v1/comm/searchCompanyList")
    public ResponseEntity<Object> searchCompanyList(){
    	
    	Map<String , Object> resultMap = new HashMap<String, Object>();
    	
    	List<CompanyResponseVo> companyList = commService.searchCompanyList();
    	
		resultMap.put("code", "OK");
		resultMap.put("msg", "");
		resultMap.put("companyList", companyList);
    	
    	return  new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @ApiOperation(value = "회사정보 검색", httpMethod = "GET", notes = "회사 정보 검색")
    @GetMapping(value="/api/v1/comm/searchCompanyInfo/{companyName}")
    public ResponseEntity<Object> searchCompanyInfo(@PathVariable String companyName){
    	
    	Map<String , Object> resultMap = new HashMap<String, Object>();
    	
    	List<CompanyResponseVo> companyList = commService.searchCompanyInfo(companyName);
    	
		resultMap.put("code", "OK");
		resultMap.put("msg", "");
		resultMap.put("companyList", companyList);
    	
    	return  new ResponseEntity<>(resultMap, HttpStatus.OK);
    }    
    
}

package com.skt.doss.portal.batch.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skt.doss.portal.crowd.core.service.UserCrowdService;

import io.swagger.annotations.ApiOperation;

//import com.skcc.twd.customer.core.object.query.ResponseDto;

@RestController
public class BatchController {

	@Autowired 
	UserCrowdService userCrowdService;
	
	private static final Logger log = LoggerFactory.getLogger(BatchController.class);
    
    @ApiOperation(value = "crowd 유저 정보 현행화", httpMethod = "GET", notes = "crowd 유저 정보 현행화 api")
    @GetMapping(value="/api/v1/batch/update-crowd-user")
    public ResponseEntity<Object> updateCrowdUser(){
    	
    	System.out.println(" == updateCrowdUser == ");
    	
    	Map<String , Object> resultMap = new HashMap<>();
    	
    	//ilm 변경 유저 정보 조회.
    	
    	/*
    	Map crowdUserMap = new HashMap();
    	
	    crowdUserMap.put("name"			, "bp000411");
	    crowdUserMap.put("first-name"	, "lee");
	    crowdUserMap.put("last-name"	, "com");
	    crowdUserMap.put("display-name"	, "lee dept");
	    crowdUserMap.put("email"		, "aa3@aa.com");  	
    	
    	userCrowdService.updateCrowdUser("bp000411", crowdUserMap);
    	*/
    	
    	return new ResponseEntity<>(resultMap , HttpStatus.OK);
    }  
 
}

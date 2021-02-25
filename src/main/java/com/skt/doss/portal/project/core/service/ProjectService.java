package com.skt.doss.portal.project.core.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skt.doss.portal.project.core.mapper.ProjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    public Map<String,Object> selectMyProjectList(String dossId) {
    	
    	String code = "";
    	String msg = "";
    	
    	Map<String,Object> retMap = new HashMap<>();
    	
    	try {
    		code = "OK";
    		retMap.put("result", projectMapper.selectMyProjectList(dossId));
    	} catch(Exception e) {
    		code = "err";
    		msg = "";
    	}
    	
    	retMap.put("code", code);
    	retMap.put("msg", msg);
    	
		return retMap;
	}
    
    public Map<String,Object> selectProjectUserList(String projectId) {
    	
    	String code = "";
    	String msg = "";
    	
    	Map<String,Object> retMap = new HashMap<>();
    	
    	try {
    		code = "OK";
    		retMap.put("result", projectMapper.selectProjectUserList(projectId));
    	} catch(Exception e) {
    		code = "err";
    		msg = "";
    	}
    	
    	retMap.put("code", code);
    	retMap.put("msg", msg);
    	
		return retMap;
	}
	
}

package com.skt.doss.portal.common.core.service;

import com.skt.doss.portal.common.core.mapper.CommMapper;
import com.skt.doss.portal.common.core.object.query.CommCodeResponseVo;
import com.skt.doss.portal.common.core.object.query.CompanyResponseVo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@Slf4j
public class CommService {

    @Autowired
    CommMapper commMapper;

	public List<CommCodeResponseVo> searchCommCodeList(String codeGroupId) {
		// TODO Auto-generated method stub
		return commMapper.searchCommCodeList(codeGroupId);
	}

	public List<CommCodeResponseVo> searchCommCodeAllList() {
		// TODO Auto-generated method stub
		return commMapper.searchCommCodeAllList();
	}

	public List<Map<String , Object>> searchCommGroupCodeList() {
		// TODO Auto-generated method stub
		return commMapper.searchCommGroupCodeList();
	}

	public List<CompanyResponseVo> searchCompanyList() {
		// TODO Auto-generated method stub
		return commMapper.searchCompanyList();
	}

	public List<CompanyResponseVo> searchCompanyInfo(String companyName) {
		// TODO Auto-generated method stub
		return commMapper.searchCompanyInfo(companyName);
	}
	
	
}

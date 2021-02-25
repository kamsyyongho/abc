package com.skt.doss.portal.common.core.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.skt.doss.portal.common.core.object.query.CommCodeResponseVo;
import com.skt.doss.portal.common.core.object.query.CompanyResponseVo;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommMapper {

	List<CommCodeResponseVo> searchCommCodeList(String codeGroupId);

	List<CommCodeResponseVo> searchCommCodeAllList();

	List<Map<String, Object>> searchCommGroupCodeList();
	
	List<CompanyResponseVo> searchCompanyList();

	List<CompanyResponseVo> searchCompanyInfo(String companyName);
}

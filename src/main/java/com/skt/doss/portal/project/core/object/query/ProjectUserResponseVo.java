package com.skt.doss.portal.project.core.object.query;

import lombok.Data;

@Data
public class ProjectUserResponseVo {

	private String userNm;  
	private String companyNm;
	private String deptNm;
	private String roleName;
	private String portal;
	private String crowd;
	private String jira;
	private String confluence;
	private String bitbucket;
	private String jenkins;
	private String sonarqube;
	private String nexus;
	
}

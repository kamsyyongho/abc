package com.skt.doss.portal.project.core.object.query;

import java.util.List;

import lombok.Data;

@Data
public class MyProjectResponseVo {
	
	private String projId;
	private String projectName;
	private String startDate;
	private String endDate;
	private String pmsType;
	private String projStatus;
	private String projStatusNm;
	private String prjMstCompNm;
	private String prjMstUserNm;
	
	private List<Role> roleList;
	
	@Data
	public static class Role {
		private String roleId;
		private String roleName;
		private String portal;
		private String crowd;
		private String jira;
		private String confluence;
		private String bitbucket;
		private String jenkins;
		private String sonarqube;
		private String nexus;
		private String spinnaker;
	}
	
}

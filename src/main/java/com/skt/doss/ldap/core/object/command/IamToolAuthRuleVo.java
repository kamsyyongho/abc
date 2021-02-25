package com.skt.doss.ldap.core.object.command;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
public class IamToolAuthRuleVo {
	
	private String roleId;
	private String roleName;
	private String roleExplain;
	private String prjRange;
	private String portal;
	private String crowd;
	private String jira;
	private String confluence;
	private String bitbucket;
	private String jenkins;
	private String sonarqube;
	private String nexus;
	private String spinnaker;
	private String useYn;
	private String updDate;
	private String regDate;
	
}
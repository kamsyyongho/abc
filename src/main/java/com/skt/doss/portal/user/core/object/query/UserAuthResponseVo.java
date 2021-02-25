package com.skt.doss.portal.user.core.object.query;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.extern.slf4j.Slf4j;


@ToString()
@Slf4j
@NoArgsConstructor
@Getter
@Setter
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAuthResponseVo {
	
	private String projectId;
	private String dossId;
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
	private String projectName;
	private String description;
	private String startDate;
	private String endDate;
	private String regId;
	private String updId;
	private String pmsType;
	private String projStatus;
}

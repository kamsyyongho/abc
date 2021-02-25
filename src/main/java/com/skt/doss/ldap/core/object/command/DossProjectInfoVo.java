package com.skt.doss.ldap.core.object.command;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
public class DossProjectInfoVo {
	
	private String projId;
	private String projectName;
	private String description;
	private String startDate;
	private String endDate;
	private String regId;
	private String updId;
	private String pmsType;
	private String projStatus;
	private String updDate;
	private String regDate;

}
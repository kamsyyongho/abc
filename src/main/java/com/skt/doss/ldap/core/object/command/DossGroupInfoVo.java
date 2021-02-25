package com.skt.doss.ldap.core.object.command;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
public class DossGroupInfoVo {
	
	private String groupId;
	private String groupName;
	private String startDate;
	private String endDate;
	private String updDate;
	private String regDate;

}

package com.skt.doss.ldap.core.object.command;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
public class DossGroupMemberAuthVo {
	
	private String groupId;
	private String dossId;
	private String roleId;
	private String updDate;
	private String regDate;
	
}

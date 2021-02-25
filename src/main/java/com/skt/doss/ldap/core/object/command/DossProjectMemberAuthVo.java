package com.skt.doss.ldap.core.object.command;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
public class DossProjectMemberAuthVo {
	
	private String projectId;
	private String dossId;
	private String roleId;
	private String updDate;
	private String regDate;
	
}

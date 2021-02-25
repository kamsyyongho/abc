package com.skt.doss.ldap.core.object.command;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
public class IamUserCredentialRecordVo {
	
	private String dossId;
	private String ci;
	private String pwdHash;
	private String updDate;
	private String regDate;

}

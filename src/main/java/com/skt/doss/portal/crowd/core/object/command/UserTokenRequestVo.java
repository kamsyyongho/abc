package com.skt.doss.portal.crowd.core.object.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserTokenRequestVo {
	
	private String userId;
	private String userPw;
	
}

package com.skt.doss.portal.user.core.object.query;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.extern.slf4j.Slf4j;


@ToString(exclude = "pwd")
@Slf4j
@NoArgsConstructor
@Getter
@Setter
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseVo {
	
	private String dossId;	
	private String userType;
	private String sktEmpNo;
	private String phoneNo;
	private String email;
	private String lastLoginDt;
	private String signupDt;
	private String companyCd;
	private String portalRoleId;
	private String lockYn;
	private String identCheckYn;
	private String identCheckDate;
	private String updDate;
	private String regDate;
	private String companyNm;
	private String deptNm;
	private String birthDt;
	private String userNm;
	private String pwd;
	private String newPwd;
	private String dossIdSeq;
	private String userTypeNm;
	private String dossUserYn;
	private String asisUserYn;
	private String empno;
	private String hname;
	private String ename;
	private String regno;
	private String indept;
	private String sosok;
	private String tsosok;
	private String movetelno;
	private String msg;
	private String code;
	
}

package com.skt.doss.portal.common.core.object.query;

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
public class CompanyResponseVo {
	
	private String  companyCd;
	private String  companyName;
	private String  updDate;
	private String  regDate;
	
}

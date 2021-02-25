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
public class CommCodeResponseVo {
	
	private String  codeGroupNm;
	private String  codeGroupId;
	private String  codeId;
	private String  codeNm;
	private String  useYn;
	private String  updDate;
	private String  regDate;
}

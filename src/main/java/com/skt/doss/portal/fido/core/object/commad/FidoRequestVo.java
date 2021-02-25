package com.skt.doss.portal.fido.core.object.commad;

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
public class FidoRequestVo {
	
	private String dossId;	
	private String phoneNo;
	private String revSession;
}
package com.skt.doss.common.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@AllArgsConstructor
public class ErrorVo {
	
    public final static String NONE = "0000";

    private String errorCode;
    private String errorMessage;
    
    
    public ErrorVo() {
        this(NONE, "");
    }

    
	public ErrorVo(String none2, String string) {
		// TODO Auto-generated constructor stub
	}
}

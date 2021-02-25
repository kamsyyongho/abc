package com.skt.doss.common.utils;

public class CommUtil {
	
	public boolean isNull(String val) {
		if(val == null || val.equals("")) {
			return true;
		}else {
			return false;
		}
	}
	
	public String stringToUpper(String val) {
		return val.toUpperCase();
	}

	public String sktMemberYn(String val) {
		String retStr = "";
		
    	if("10".equals(val.substring(0, 2)) || "11".equals(val.substring(0, 2))){
    		retStr = "Y";
    	}else {
    		retStr = "N";
    	}
		
		return retStr;
	}
}

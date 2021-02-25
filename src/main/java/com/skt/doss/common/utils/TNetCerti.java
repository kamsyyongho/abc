package com.skt.doss.common.utils;

import java.util.HashMap;
import java.util.Map;

public class TNetCerti {
	public Map<String , Object> checkTnetSsoSession(){
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("t-session", true);
		return resultMap;
	}
}

package com.skt.doss.ldap.core.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Name;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;

import org.springframework.stereotype.Service;

import com.skt.doss.ldap.core.object.command.DossProjectInfoVo;

@Service
public class DossProjectInfoService {
	
	public Attributes buildAttributes(DossProjectInfoVo p) {
		
		BasicAttribute ocAttr = new BasicAttribute("objectclass");
		ocAttr.add("top");
		ocAttr.add("dossProjectInfo");
		
		Attributes attrs = new BasicAttributes();
		attrs.put(ocAttr);
		attrs.put("cn", "dossProjectInfo");
		
		attrs.put("projId", p.getProjId());
		attrs.put("projectName", p.getProjectName());
		attrs.put("description", p.getDescription());
		attrs.put("startDate", p.getStartDate());
		attrs.put("endDate", p.getEndDate());
		attrs.put("regId", p.getRegId());
		attrs.put("updId", p.getUpdId());
		attrs.put("pmsType", p.getPmsType());
		attrs.put("projStatus", p.getProjStatus());
		attrs.put("updDate", p.getUpdDate());
		attrs.put("regDate", p.getRegDate());
		
		return attrs;
	}

}

package com.skt.doss.ldap.core.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;

import org.springframework.stereotype.Service;

import com.skt.doss.ldap.core.object.command.DossProjectMemberAuthVo;

@Service
public class DossProjectMemberAuthService {
	
	public Attributes buildAttributes(DossProjectMemberAuthVo p) {
		
		BasicAttribute ocAttr = new BasicAttribute("objectclass");
		ocAttr.add("top");
		ocAttr.add("(dossProjectMemberAuth");
		
		Attributes attrs = new BasicAttributes();
		attrs.put(ocAttr);
		attrs.put("cn", "dossProjectMemberAuth");
		
		attrs.put("projectId", p.getProjectId());
		attrs.put("dossId", p.getDossId());
		attrs.put("roleId", p.getRoleId());
		attrs.put("updDate", p.getUpdDate());
		attrs.put("regDate", p.getRegDate());
		
		return attrs;
	}

}

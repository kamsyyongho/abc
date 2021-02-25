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

import com.skt.doss.ldap.core.object.command.DossGroupInfoVo;

@Service
public class DossGroupInfoService {
	
	public Attributes buildAttributes(DossGroupInfoVo p) {
		
		BasicAttribute ocAttr = new BasicAttribute("objectclass");
		ocAttr.add("top");
		ocAttr.add("dossGroupInfo");
		
		Attributes attrs = new BasicAttributes();
		attrs.put(ocAttr);
		attrs.put("cn", "dossGroupInfo");
		
		attrs.put("groupId", p.getGroupId());
		attrs.put("groupName", p.getGroupName());
		attrs.put("startDate", p.getStartDate());
		attrs.put("endDate", p.getEndDate());
		attrs.put("updDate", p.getUpdDate());
		attrs.put("regDate", p.getRegDate());
		
		return attrs;
	}

}

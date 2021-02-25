package com.skt.doss.ldap.core.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;

import org.springframework.stereotype.Service;

import com.skt.doss.ldap.core.object.command.IamToolAuthRuleVo;

@Service
public class IamToolAuthRuleService {
	
	
	public Attributes buildAttributes(IamToolAuthRuleVo p) {
		
		BasicAttribute ocAttr = new BasicAttribute("objectclass");
		ocAttr.add("top");
		ocAttr.add("iamToolAuthRule");
		
		Attributes attrs = new BasicAttributes();
		attrs.put(ocAttr);
		attrs.put("cn", "iamToolAuthRule");
		
		attrs.put("roleId", p.getRoleId());
		attrs.put("roleName", p.getRoleName());
		attrs.put("roleExplain", p.getRoleExplain());
		attrs.put("prjRange", p.getPrjRange());
		attrs.put("portal", p.getPortal());
		attrs.put("crowd", p.getCrowd());
		attrs.put("jira", p.getJira());
		attrs.put("confluence", p.getConfluence());
		attrs.put("bitbucket", p.getBitbucket());
		attrs.put("jenkins", p.getJenkins());
		attrs.put("sonarqube", p.getSonarqube());
		attrs.put("nexus", p.getNexus());
		attrs.put("spinnaker", p.getSpinnaker());
		attrs.put("useYn", p.getUseYn());
		attrs.put("updDate", p.getUpdDate());
		attrs.put("regDate", p.getRegDate());
		
		return attrs;
	}

}

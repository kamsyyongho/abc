package com.skt.doss.ldap.core.service;

import java.util.Map;

import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;

import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Service;

import com.skt.doss.ldap.core.object.command.IamUserInfoVo;

@Service
public class LdapGroupService {
	
	public void insertLdapGroup(String baseDn, DirContext ctx, Map param) {
		
		String cn = param.get("cn").toString();
		String gidNumber = param.get("gidNumber").toString();
		
		Name dn = LdapNameBuilder.newInstance(baseDn).build();
		try {
			ctx.bind(dn, null, buildAttributes(cn, gidNumber));
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Attributes buildAttributes(String cn, String gidNumber) {
		
		BasicAttribute ocAttr = new BasicAttribute("objectclass");
		ocAttr.add("posixGroup");
		ocAttr.add("top");
		
		Attributes attrs = new BasicAttributes();
		attrs.put(ocAttr);
		attrs.put("cn", cn);
		attrs.put("gidNumber", gidNumber);
		
		return attrs;
	}


}

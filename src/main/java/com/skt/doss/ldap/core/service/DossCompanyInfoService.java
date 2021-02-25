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

import com.skt.doss.ldap.core.object.command.DossCompanyInfoVo;

@Service
public class DossCompanyInfoService {
	
	public Attributes buildAttributes(DossCompanyInfoVo p) {
		
		BasicAttribute ocAttr = new BasicAttribute("objectclass");
		ocAttr.add("top");
		ocAttr.add("dossCompanyInfo");
		
		Attributes attrs = new BasicAttributes();
		attrs.put(ocAttr);
		attrs.put("cn", "dossCompanyInfo");
		
		attrs.put("companyCd", p.getCompanyCd());
		attrs.put("companyName", p.getCompanyName());
		attrs.put("updDate", p.getUpdDate());
		attrs.put("regDate", p.getRegDate());

		return attrs;
	}

}

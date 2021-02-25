package com.skt.doss.ldap.core.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.springframework.stereotype.Service;

import com.skt.doss.ldap.core.object.command.IamUserCredentialRecordVo;
import com.skt.doss.portal.user.core.object.query.UserResponseVo;

@Service
public class IamUserCredentialRecordService {
	
	
	
	public IamUserCredentialRecordVo iamUserCredentialRecord(String baseDn, DirContext ctx, String dossId, String pwd) {
		
		IamUserCredentialRecordVo iamUserCredentialRecordVo = new IamUserCredentialRecordVo();
		SearchControls cons = new SearchControls();
		cons.setSearchScope(SearchControls.SUBTREE_SCOPE);
		
		String returnAttrs[] = {
				"dossId",
				"pwdHash",
				"updDate",
				"regDate",
		};
		cons.setReturningAttributes(returnAttrs);
		
		String searchFilter = "(&(objectClass=iamUserCredentialRecord)";
		searchFilter =  searchFilter + "(dossId=" + dossId + ")(pwdHash=" + pwd +"))";
		
		NamingEnumeration result1 = null;
		
		try {
			result1 = ctx.search(baseDn, searchFilter, cons);
			int searchCount = 0;
			
			while (result1.hasMore()) {
				SearchResult nextEntry = null;
				try {
					nextEntry = (SearchResult) result1.next();
					searchCount++;
					
					Attributes attrs = nextEntry.getAttributes();
					
					if(attrs != null) {
						try {
							
							iamUserCredentialRecordVo.setDossId(attrs.get("dossId").get().toString());
							iamUserCredentialRecordVo.setPwdHash(attrs.get("pwdHash").get().toString());
							iamUserCredentialRecordVo.setUpdDate(attrs.get("updDate").get().toString());
							iamUserCredentialRecordVo.setRegDate(attrs.get("regDate").get().toString());

						} catch (NullPointerException e) {
						}
					}
							
				} catch (Exception e) {
					continue;
				}
			}
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		return iamUserCredentialRecordVo;
	}
	
	
	
	
	public Attributes buildAttributes(IamUserCredentialRecordVo p) {
		
		BasicAttribute ocAttr = new BasicAttribute("objectclass");
		ocAttr.add("top");
		ocAttr.add("iamUserCredentialRecord");
		
		Attributes attrs = new BasicAttributes();
		attrs.put(ocAttr);
		attrs.put("cn", "iamUserCredentialRecord");
		
		attrs.put("dossId", p.getDossId());
		attrs.put("ci", p.getCi());
		attrs.put("pwdHash", p.getPwdHash());
		attrs.put("updDate", p.getUpdDate());
		attrs.put("regDate", p.getRegDate());
		
		return attrs;
	}

}

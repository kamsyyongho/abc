package com.skt.doss.ldap.core.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Name;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.Attribute;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Service;

import com.skt.doss.ldap.core.mapper.Person;
import com.skt.doss.ldap.core.object.command.IamUserInfoVo;
import com.skt.doss.portal.user.core.object.query.UserResponseVo;


import io.netty.util.internal.StringUtil;
import lombok.*; 

@Service
public class IamUserInfoService {
	
	
	public Map<String, Object> iamUserInfo_bak(String baseDn, DirContext ctx, String searchName) {
		
		Map map = new HashMap();
		List<Map> list = new ArrayList<Map>();
		
		SearchControls cons = new SearchControls();
		cons.setSearchScope(SearchControls.SUBTREE_SCOPE);
		
		String returnAttrs[] = {
				"dossId",
				"userType",
				"sktEmpNo",
				"phoneNo",
				"email",
				"lastLoginDt",
				"signupDt",
				"companyCd",
				"portalRoleId",
				"lockYn",
				"identCheckYn",
				"identCheckDate",
				"updDate",
				"regDate",
				"companyNm",
				"birthDt",
				"userNm",
				"deptNm"
		};
		cons.setReturningAttributes(returnAttrs);
		
		String searchFilter = "(&(cn=iamUserInfo)";
		searchFilter =  searchFilter + "(dossId=" + searchName + "))";
		
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
			
					NamingEnumeration<String> ids = attrs.getIDs();
					while(ids.hasMore()) {
						String id = ids.next();
						Attribute idattr = attrs.get(id);
						//System.out.print(id + "(" + idattr.size() + ")");
						for(int ix=0; ix < idattr.size(); ++ix) {
							//System.out.print(", " + ix + "/" + idattr.get(ix));
						}
					}
							
				} catch (Exception e) {
					continue;
				}
			}
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		map.put("userList", list);
		
		return map;
	}
	
	public UserResponseVo iamUserInfo(String baseDn, DirContext ctx, String searchName) {
		
		UserResponseVo userResponseVo = new UserResponseVo();
		SearchControls cons = new SearchControls();
		cons.setSearchScope(SearchControls.SUBTREE_SCOPE);
		
		String returnAttrs[] = {
				"dossId",
				"userType",
				"sktEmpNo",
				"phoneNo",
				"email",
				"lastLoginDt",
				"signupDt",
				"companyCd",
				"portalRoleId",
				"lockYn",
				"identCheckYn",
				"identCheckDate",
				"updDate",
				"regDate",
				"companyNm",
				"birthDt",
				"userNm",
				"deptNm"
		};
		cons.setReturningAttributes(returnAttrs);
		
		String searchFilter = "(&(objectClass=iamUserInfo)";
		searchFilter =  searchFilter + "(dossId=" + searchName + "))";
		
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
							
							userResponseVo.setDossId(attrs.get("dossId").get().toString());
							userResponseVo.setUserType(attrs.get("userType").get().toString());
							userResponseVo.setSktEmpNo(attrs.get("sktEmpNo").get().toString());
							userResponseVo.setPhoneNo(attrs.get("phoneNo").get().toString());
							userResponseVo.setEmail(attrs.get("email").get().toString());
							userResponseVo.setLastLoginDt(attrs.get("lastLoginDt").get().toString());
							userResponseVo.setSignupDt(attrs.get("signupDt").get().toString());
							userResponseVo.setCompanyCd(attrs.get("companyCd").get().toString());
							userResponseVo.setPortalRoleId(attrs.get("portalRoleId").get().toString());
							userResponseVo.setLockYn(attrs.get("lockYn").get().toString());
							userResponseVo.setIdentCheckYn(attrs.get("identCheckYn").get().toString());
							userResponseVo.setIdentCheckDate(attrs.get("identCheckDate").get().toString());
							userResponseVo.setUpdDate(attrs.get("updDate").get().toString());
							userResponseVo.setRegDate(attrs.get("regDate").get().toString());
							userResponseVo.setCompanyNm(attrs.get("companyNm").get().toString());
							userResponseVo.setBirthDt(attrs.get("birthDt").get().toString());
							userResponseVo.setUserNm(attrs.get("userNm").get().toString());
							userResponseVo.setDeptNm(attrs.get("deptNm").get().toString());

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
		
		
		return userResponseVo;
	}
	
	public Map<String, Object> iamUserInfo_ldap(String baseDn, DirContext ctx, String searchName) {
		
		Map map = new HashMap();
		List<Map> list = new ArrayList<Map>();
		
		SearchControls cons = new SearchControls();
		cons.setSearchScope(SearchControls.SUBTREE_SCOPE);
		
		String returnAttrs[] = {
				"dossId",
				"userType",
				"sktEmpNo",
				"phoneNo",
				"email",
				"lastLoginDt",
				"signupDt",
				"companyCd",
				"portalRoleId",
				"lockYn",
				"identCheckYn",
				"identCheckDate",
				"updDate",
				"regDate",
				"companyNm",
				"birthDt",
				"userNm",
				"deptNm"
		};
		cons.setReturningAttributes(returnAttrs);
		
		String searchFilter = "(&(objectClass=iamUserInfo)";
		searchFilter =  searchFilter + "(dossId=" + searchName + "))";
		
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
							
							HashMap<String, String> hm = new HashMap<String, String>();							
							hm.put("dossId", attrs.get("dossId").get().toString());
							hm.put("userType", attrs.get("userType").get().toString());
							hm.put("sktEmpNo", attrs.get("sktEmpNo").get().toString());
							hm.put("phoneNo", attrs.get("phoneNo").get().toString());
							hm.put("email", attrs.get("email").get().toString());
							hm.put("lastLoginDt", attrs.get("lastLoginDt").get().toString());
							hm.put("signupDt", attrs.get("signupDt").get().toString());
							hm.put("companyCd", attrs.get("companyCd").get().toString());
							hm.put("portalRoleId", attrs.get("portalRoleId").get().toString());
							hm.put("lockYn", attrs.get("lockYn").get().toString());
							hm.put("identCheckYn", attrs.get("identCheckYn").get().toString());
							hm.put("identCheckDate", attrs.get("identCheckDate").get().toString());
							hm.put("updDate", attrs.get("updDate").get().toString());
							hm.put("regDate", attrs.get("regDate").get().toString());
							hm.put("companyNm", attrs.get("companyNm").get().toString());
							hm.put("birthDt", attrs.get("birthDt").get().toString());
							hm.put("userNm", attrs.get("userNm").get().toString());
							hm.put("deptNm", attrs.get("deptNm").get().toString());

							list.add(hm);
						} catch (NullPointerException e) {
							//System.out.println("Errors listing attributes:"+e);
						}
					}
							
				} catch (Exception e) {
					continue;
				}
			}
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		map.put("userList", list);
		
		return map;
	}
	
	public void insertIamUserInfo(String baseDn, DirContext ctx, Map param) {
		
		IamUserInfoVo p = new IamUserInfoVo();
		
		p.setDossId(param.get("dossId").toString());
		p.setUserType(param.get("userType").toString());
		p.setSktEmpNo(param.get("sktEmpNo").toString());
		p.setPhoneNo(param.get("phoneNo").toString());
		p.setEmail(param.get("email").toString());
		p.setLastLoginDt(param.get("lastLoginDt").toString());
		p.setSignupDt(param.get("signupDt").toString());
		p.setCompanyCd(param.get("companyCd").toString());
		p.setPortalRoleId(param.get("portalRoleId").toString());
		p.setLockYn(param.get("lockYn").toString());
		p.setIdentCheckYn(param.get("identCheckYn").toString());
		p.setIdentCheckDate(param.get("identCheckDate").toString());
		p.setUpdDate(param.get("updDate").toString());
		p.setRegDate(param.get("regDate").toString());
		p.setCompanyNm(param.get("companyNm").toString());
		p.setBirthDt(param.get("birthDt").toString());
		p.setUserNm(param.get("userNm").toString());
		p.setDeptNm(param.get("deptNm").toString());
		
		Name dn = LdapNameBuilder.newInstance("cn=iamUserInfo,"+ baseDn).build();
		try {
			ctx.bind(dn, null, buildAttributes(p));
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Attributes buildAttributes(IamUserInfoVo p) {
		
		BasicAttribute ocAttr = new BasicAttribute("objectclass");
		ocAttr.add("iamUserInfo");
		ocAttr.add("top");
		
		Attributes attrs = new BasicAttributes();
		attrs.put(ocAttr);
		attrs.put("cn", "iamUserInfo");
		
		attrs.put("dossId", p.getDossId());
		attrs.put("userType", p.getUserType());
		attrs.put("sktEmpNo", p.getSktEmpNo());
		attrs.put("phoneNo", p.getPhoneNo());
		attrs.put("email", p.getEmail());
		attrs.put("lastLoginDt", p.getLastLoginDt());
		attrs.put("signupDt", p.getSignupDt());
		attrs.put("companyCd", p.getCompanyCd());
		attrs.put("portalRoleId", p.getPortalRoleId());
		attrs.put("lockYn", p.getLockYn());
		attrs.put("identCheckYn", p.getIdentCheckYn());
		attrs.put("identCheckDate", p.getIdentCheckDate());
		attrs.put("updDate", p.getUpdDate());
		attrs.put("regDate", p.getRegDate());
		attrs.put("companyNm", p.getCompanyNm());
		attrs.put("birthDt", p.getBirthDt());
		attrs.put("userNm", p.getUserNm());
		attrs.put("deptNm", p.getDeptNm());
		
		return attrs;
	}

}

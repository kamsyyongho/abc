package com.skt.doss.ldap.core.service;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import java.util.List;

import javax.naming.Name;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;

import org.springframework.ldap.support.LdapNameBuilder;



public class LdapService {
	
	
    public Name buildGroupDn(String baseLdapPath, String groupName) {
        return LdapNameBuilder.newInstance(baseLdapPath)
                .add("ou", "groups")
                .add("cn", groupName)
                .build();
    }
    
    
//    public List<Group> findAll(){
//        return ldapTemplate.search(
//                query().where("objectclass").is("groupOfUniqueNames"),
//                new GroupContextMapper());
//    }
    
    
//    public Name buildPersonDn(String baseLdapPath, Person person) {
//        return LdapNameBuilder.newInstance(baseLdapPath)
//                .add("ou", "people")
//                .add("uid", person.getUid())
//                .build();
//    }
//    
//    
//    public Attributes buildPersonAttributes(Person p) {
//        Attributes attrs = new BasicAttributes();
//        BasicAttribute ocAttr = new BasicAttribute("objectclass");
//        ocAttr.add("top");
//        ocAttr.add("person");
//        attrs.put(ocAttr);
//        attrs.put("ou", "people");
//        attrs.put("uid", p.getUid());
//        attrs.put("cn", p.getFullName());
//        attrs.put("sn", p.getLastName());
//        return attrs;
//    }
	
    
	public static void selectLdap() {
		
	}
	
	
	public static void insertLdap() {
		
	}
	
	
	public static void updateLdap() {
		
	}
	
	
	public static void deleteLdap() {
		
	}
	
	
}
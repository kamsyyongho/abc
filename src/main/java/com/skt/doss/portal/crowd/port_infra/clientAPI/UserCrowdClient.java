package com.skt.doss.portal.crowd.port_infra.clientAPI;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.skt.doss.common.config.ApiHeaderConfiguration;

@FeignClient(name = "crowd-svc-api", url = "${feign.doss-crowd-api.url}", decode404 = true, configuration = {ApiHeaderConfiguration.class})
public interface UserCrowdClient {
	
    @GetMapping(value = "rest/usermanagement/1/user", produces = "application/json")
    public Map<String, Object> getUserYn(@RequestParam("username") String username, @RequestParam("key")  String key);
   
    @PostMapping(value = "rest/usermanagement/1/group", produces = "application/json")
    public Map<String, Object> addGroup(@RequestBody Map param);   
   
    @PutMapping(value = "rest/usermanagement/1/group", produces = "application/json")
    public Map<String, Object> updateGroup(@RequestParam("groupname") String groupname,@RequestBody Map param); 

    @GetMapping(value = "rest/usermanagement/1/group", produces = "application/json")
    public Map<String, Object> getGroup(@RequestParam("groupname") String groupname);
    
    @GetMapping(value = "rest/usermanagement/1/user/group/direct", produces = "application/json")
	public Map<String, Object> getUserGroupList(@RequestParam("username") String username
												,@RequestParam("groupname") String groupname
												,@RequestParam("maxResult") String maxResult
												,@RequestParam("startIndex") String startIndex);
    
    @PostMapping(value = "rest/usermanagement/1/user/group/direct", produces = "application/json")
	public Map<String, Object> addUserToGroup(@RequestParam("username") String username,@RequestBody Map param); 
 
    @PostMapping(value = "rest/usermanagement/1/session", produces = "application/json")
    public Map<String, Object> getUserToken(@RequestBody Map<String,Object> paramMap);
    
    @DeleteMapping(value = "rest/usermanagement/1/session/{token}", produces = "application/json")
    public Map<String, Object> deleteUserToken(@PathVariable String token);

    @PostMapping(value = "rest/usermanagement/1/user", produces = "application/json")
	public Map<String, Object> addCrowdUser(@RequestBody Map<String,Object> param);
    
    @PutMapping(value = "rest/usermanagement/1/user", produces = "application/json")
	public Map<String, Object> updateCrowdUser(@RequestParam("username") String username,@RequestBody Map param);
    
}

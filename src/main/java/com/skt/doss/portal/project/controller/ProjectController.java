package com.skt.doss.portal.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skt.doss.portal.project.core.service.ProjectService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/v1/project")
public class ProjectController {

	@Autowired
	private ProjectService projectService;
	
	@ApiOperation(value = "프로젝트 조회", httpMethod = "GET", notes = "프로젝트 조회")
	@GetMapping(value="/my-project/{dossId}")
	public ResponseEntity<Object> selectMyProjectList(
			@PathVariable(name = "dossId", required = true) String dossId)
	{
		return  new ResponseEntity<>(projectService.selectMyProjectList(dossId), HttpStatus.OK);
	}
	
	@ApiOperation(value = "프로젝트 유저 조회", httpMethod = "GET", notes = "프로젝트 유저 조회")
	@GetMapping(value="/project-user/{projectId}")
	public ResponseEntity<Object> selectProjectUserList(
			@PathVariable(name = "projectId", required = true) String projectId)
	{
		return  new ResponseEntity<>(projectService.selectProjectUserList(projectId), HttpStatus.OK);
	}
	
}

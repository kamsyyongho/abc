package com.skt.doss.portal.project.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.skt.doss.portal.project.core.object.query.MyProjectResponseVo;
import com.skt.doss.portal.project.core.object.query.ProjectUserResponseVo;

@Mapper
public interface ProjectMapper {

	List<MyProjectResponseVo> selectMyProjectList(String dossId);
	
	List<ProjectUserResponseVo> selectProjectUserList(String projectId);
	
}

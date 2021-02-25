package com.skt.doss.portal.user.core.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.skt.doss.portal.user.core.object.commad.UserRequestVo;
import com.skt.doss.portal.user.core.object.query.UserAuthResponseVo;
import com.skt.doss.portal.user.core.object.query.UserResponseVo;

@Mapper
public interface UserMapper {

	String sktMemberYn();

	String selectNextDossSeq();

	UserResponseVo searchUserInfo(String dossId) throws Exception;

	Map checkExistingUser();

	String makeDossId();

	void regUserInfo(Map<String, Object> param);

	void regUserPwdInfo(Map<String, Object> param);

	String selectIlmUserInfo(String empno);

	Map<String, Object> selectDossUserInfo(Map<String, Object> param);

	void insertUserInfo(UserRequestVo user) throws Exception;

	void insertUserCred(UserRequestVo user) throws Exception;

	List<UserAuthResponseVo> searchUserAuth(String dossId);

	void updUserInfo(UserRequestVo userRequestVo);

	UserResponseVo checkDossUser(UserRequestVo userRequestVo);

	void userCertiStatusChange(UserRequestVo userRequestVo) throws Exception;

	UserResponseVo checkUserCertiStatus(String dossId);

	void updateDossSeq(Map<String, Object> param);

	void insertUserLoginHst(UserRequestVo userRequestVo);

	UserResponseVo certiAsisUser(UserRequestVo userRequestVo);

	UserResponseVo getSktMemberInfo(String empno);
	
	int expireAsisUser(UserRequestVo userRequestVo);

}

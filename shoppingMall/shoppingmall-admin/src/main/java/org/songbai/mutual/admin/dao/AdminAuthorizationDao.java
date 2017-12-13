package org.songbai.mutual.admin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.songbai.mutual.admin.model.AdminAuthorizationModel;

public interface AdminAuthorizationDao {

	public void deleteAuthorizationByActorIdAuthorityIds(@Param(value = "actorId") Integer actorId,
		    @Param(value = "authorityIds") List<Integer> authorityIds);
	/**
	 * 创建角色与用户关联表
	 * @param authorizationMoudel
	 */
	public void createAuthorization(AdminAuthorizationModel authorizationMoudel);
	public void deleteAuthorizationByActorIdAuthorityId(@Param(value="actorId")Integer actorId,@Param(value="authorityId") Integer authorityId);
	

}



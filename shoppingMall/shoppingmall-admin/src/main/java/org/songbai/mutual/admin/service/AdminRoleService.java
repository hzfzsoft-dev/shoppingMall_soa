package org.songbai.mutual.admin.service;

import java.util.List;

import org.songbai.mutual.admin.model.AdminRoleModel;
import org.songbai.mutual.utils.mvc.Page;


public interface AdminRoleService {

	public Page<AdminRoleModel> pagingQueryNotGrantRoles(Integer page, Integer pageSize, Integer actorId, String name);

	public Page<AdminRoleModel> pagingQueryGrantRoles(Integer page, Integer pageSize, Integer userId, String name);

	public void terminateAuthorizationByUserIdAuthorithIds(Integer actorId, List<Integer> authorityIdList);

	public void createRole(AdminRoleModel roleModel);

	public List<AdminRoleModel> deleteRoles(List<Integer> roleList);

	public Page<AdminRoleModel> pagingQueryChannelRoles(Integer page, Integer pageSize);

	public void updateAdminRole(AdminRoleModel adminRoleModel);
	
	

}

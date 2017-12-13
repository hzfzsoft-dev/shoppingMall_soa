
package org.songbai.mutual.admin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.songbai.mutual.admin.model.AdminRoleModel;

public interface AdminAuthorityDao {

	/**
	 * 获得所有授权给参与者的角色
	 * 
	 * @param actorId
	 * @param category
	 * @return
	 */
	public List<AdminRoleModel> queryGrantRolesByUserId(@Param(value = "actorId") Integer actorId,
			@Param(value = "category") String category);
	
	
	/**
	 * 获得未授权给所选用户的角色信息
	 * @param limit
	 * @param pageSize
	 * @param actorId
	 * @param category
	 * @param name
	 * @return
	 */
	public List<AdminRoleModel> pagingQueryNotGrantRolesByUserId(@Param(value = "limit")Integer limit,@Param(value = "pageSize") Integer pageSize,@Param(value = "actorId") Integer actorId,
			@Param(value = "category")String category, @Param(value = "name")String name);
	/**
	 * 获得未授权给所选用户的角色个数
	 * @param actorId
	 * @param category
	 * @param name
	 * @return
	 */
	public Integer pagingQueryNotGrantRolesByUserIdCount(@Param(value = "actorId")Integer actorId, @Param(value = "category")String category, @Param(value = "name")String name);


	public List<AdminRoleModel> pagingQueryGrantRolesByUserId(@Param(value = "limit")Integer limit,@Param(value = "pageSize") Integer pageSize,@Param(value = "actorId") Integer actorId,
			@Param(value = "category")String category, @Param(value = "name")String name);
	
	public Integer pagingQueryGrantRolesByUserId_count(@Param(value="actorId")Integer actorId,@Param(value="category") String category,@Param(value="name") String name);

	public void createAdminRole(AdminRoleModel roleModel);

	public AdminRoleModel getRole(Integer roleId);


	public void deleteAdminRole(Integer roleId);


	public List<AdminRoleModel> pagingQueryByagencyId(@Param(value="limit") Integer limit,@Param(value="pageSize") Integer pageSize);


	public Integer pagingQueryByagencyIdCount();


	public void updateAdminRole(AdminRoleModel adminRoleModel);

}

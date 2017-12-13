package org.songbai.mutual.admin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.songbai.mutual.admin.model.AdminUserModel;

public interface AdminActorDao {
	
	/**
	 * 根据登录名和密码获得用户 登录验证
	 * 
	 * @param userAccount
	 * @param password
	 * @return
	 */
	public AdminUserModel getUserByUserAccountPassword(@Param(value = "userAccount") String userAccount,
			@Param(value = "password") String password);
	
	/**
	 * 修改除密码之外的用户信息
	 * 
	 * @param userModel
	 */
	public void updateAdminUserExceptPassword(AdminUserModel userModel);
	
	public List<AdminUserModel> getActorsBy(@Param(value = "limit") Integer limit, @Param(value = "pageSize") Integer pageSize,
			@Param(value = "userAccount") String userAccount, @Param(value = "name") String name,
			@Param(value = "email") String email, @Param(value = "disable") Boolean disable,
			@Param(value = "phone") String phone, @Param(value = "category") String category);
	
	public Integer getActorsByCount(@Param(value = "userAccount") String userAccount, @Param(value = "name") String name,
			@Param(value = "email") String email, @Param(value = "disable") Boolean disable,
			@Param(value = "phone") String phone, @Param(value = "category") String category);
	/**
	 * 根据Id查询
	 * @param actorId
	 * @return
	 */
	public AdminUserModel getAdminUser(Integer actorId);
	/**
	 * 保存部门和用户关系
	 * @param departmentId
	 * @param actorId
	 */

	public void addDepartmentActor(@Param(value = "departmentId")Integer departmentId,@Param(value = "actorId") Integer actorId);

	public void createAdminUser(AdminUserModel userModel);
	
	/**
	 * 删除用户表
	 * @param id
	 */
	public void deletAdminUser(@Param(value = "id")Integer id);

	/**
	 * 修改密码
	 * @param actorId
	 * @param defaultPassword
	 */
	public void updateAdminUserPassword(@Param(value = "id")Integer actorId,@Param(value = "defaultPassword") String defaultPassword);
	
	/***
	 * 查询角色的成员信息
	 * @param roleId
	 * @param limit
	 * @param pageSize
	 * @return
	 */
	public List<AdminUserModel> pageQueryActorByRoleId(@Param(value="roleId")Integer roleId,@Param(value="limit") Integer limit,@Param(value="pageSize") Integer pageSize);

	public Integer pageQueryActorByRoleId_count(@Param(value="roleId")Integer roleId);

	public void deleteUserInRole(@Param(value="roleId")Integer roleId,@Param(value="actorId") Integer actorId);


    AdminUserModel getUserByUserAccount(String userAccount);

}
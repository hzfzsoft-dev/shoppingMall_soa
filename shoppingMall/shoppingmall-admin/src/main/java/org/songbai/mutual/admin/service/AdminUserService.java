package org.songbai.mutual.admin.service;

import java.util.List;

import org.songbai.mutual.admin.model.AdminUserModel;
import org.songbai.mutual.utils.mvc.Page;


public interface AdminUserService {
	
	/**
	 * 根据用户名和密码获得一个用户 登录验证 关于登录密码： 用户的登录密码在页面时会通过MD5加密一次，在后台会拼接加密盐，再次加密，
	 * 保存用户时也是采用此加密流程
	 * 
	 * @param userAccount
	 * @param password
	 * @return
	 */
	public AdminUserModel getUserByUserAccountPassword(String userAccount, String password );

	
	/**
	 * 修改User除密码之外的信息 登录名不能修改
	 * 
	 * @param adminUserModel
	 */
	public void updateAdminUserExceptPassword(AdminUserModel adminUserModel);
	
	
	
	
	/**
	 * 
	 * @param page
	 * 当前页面 
	 * @param pageSize
	 * 页面大小
	 * @param userAccount
	 * @param name
	 * @param email
	 * @param disabled
	 * @param phone
	 * @return
	 */
	public Page<AdminUserModel> getUserList(Integer page,Integer pageSize,String userAccount,String name,String email, Boolean disabled, String phone);


	public AdminUserModel getUser(Integer actorId);


	public boolean hasUserAccount(String userAccount);


	public void createAdminUser(AdminUserModel userModel, Integer departmentId);

	
	public void deleteAdminUsers(List<Integer> idList);
	/**
	 * 初始化密码
	 * @param actorId
	 * @param defaultPassword
	 */
	public void changePassword(Integer actorId, String defaultPassword);


	public void grantAuthoritysToActor(Integer actorId, List<Integer> authorityIdList);


	public Page<AdminUserModel> pageQueryActorByRoleId(Integer roleId, Integer page, Integer pageSize);


	public void deleteUserInRole(Integer roleId, Integer actorId);

	AdminUserModel getUserByUserAccount(String userAccount);

}


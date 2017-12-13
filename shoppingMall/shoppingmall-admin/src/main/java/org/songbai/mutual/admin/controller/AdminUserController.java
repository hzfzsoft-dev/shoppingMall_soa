package org.songbai.mutual.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.songbai.mutual.admin.model.AdminRoleModel;
import org.songbai.mutual.admin.model.AdminUserModel;
import org.songbai.mutual.admin.service.AdminRoleService;
import org.songbai.mutual.admin.service.AdminUserService;
import org.songbai.mutual.utils.BaseController;
import org.songbai.mutual.utils.mvc.Page;
import org.songbai.mutual.utils.mvc.Response;
import org.songbai.mutual.utils.mvc.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 只是针对后台操作用户
 * @author 
 *
 */
@Controller
@RequestMapping("/adminUser")
public class AdminUserController extends BaseController {
	@Autowired
	AdminUserService adminUserService;
	
	@Autowired
	AdminRoleService adminRoleService;
	
	Logger logger = LoggerFactory.getLogger(AdminUserController.class);
	
	@RequestMapping(value="/pagingQuery")
	@ResponseBody
	public Response pagingQuery(Integer page,Integer pageSize,String name,String userAccount,String description,Boolean disable,String phone,String email,HttpServletRequest request){
		
		//定义pageSize和page
		pageSize=(pageSize==null?Page.DEFAULE_PAGESIZE:pageSize);
		page=(page==null?0:page);
		//返回查询页面
		Page<AdminUserModel> resultPage=adminUserService.getUserList(page, pageSize, userAccount, name, email, disable, phone);
		return Response.success(resultPage);	
	}
	/**
	 * 激活后台用户
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/activate")
	@ResponseBody
	public Response activate(Integer userId){
		Assert.notNull(userId, "用户Id不能为空");
		AdminUserModel userModel=adminUserService.getUser(userId);
		if ("admin".equals(userModel.getUserAccount())) {
			return Response.response(500, "admin管理员不允许被操作");
		}
		userModel.setDisable(Boolean.FALSE);
		adminUserService.updateAdminUserExceptPassword(userModel);
		return Response.success();
		
	}
	
	/**
	 * 禁用用户
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/suspend")
	@ResponseBody
	public Response suspend(Integer userId){
		Assert.notNull(userId, "用户Id不能为空");
		AdminUserModel userModel=adminUserService.getUser(userId);
		if ("admin".equals(userModel.getUserAccount())) {
			return Response.response(500, "admin管理员不允许被操作");
		}
		userModel.setDisable(Boolean.TRUE );
		adminUserService.updateAdminUserExceptPassword(userModel);
		return Response.success();
		
	}
	
	
	/**
	 * 用户管理之更新用户
	 * @param id
	 * @param name
	 * @param userAccount
	 * @param description
	 * @return
	 */
	@RequestMapping(value="/update")
	@ResponseBody
	public Response update(Integer id,String name,String userAccount,String description){
		Assert.notNull(id, "用户Id不能为空");
		AdminUserModel userModel=adminUserService.getUser(id);
		userModel.setName(name);
		userModel.setUserAccount(userAccount);
		userModel.setDescription(description);
		adminUserService.updateAdminUserExceptPassword(userModel);
		return Response.success();
	}
	/**
	 * 添加用户
	 * @param name
	 * @param userAccount
	 * @param description
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/add")
	@ResponseBody
	public Response add(String name, String userAccount, String description, HttpServletRequest request){
		//为空判断
		Assert.notNull(name,"用户名称不能为空");
		Assert.notNull(userAccount,"账户不能为空");
		//获得当前用户
		AdminUserModel currentUser=(AdminUserModel)request.getSession().getAttribute("user");
		//验证用户账户是否存在
		AdminUserModel userModel = new AdminUserModel();
		
		//判断用户是否存在
		if(adminUserService.hasUserAccount(userAccount)){
			return Response.response(ResponseCode.INNER_ERROR,"添加用户失败，已存在的用户账户");
		}
		
		userModel.setName(name);
		userModel.setUserAccount(userAccount);
		userModel.setDescription(description);
		//默认
		userModel.setRoleType(0);
		userModel.setResourceType(currentUser.getResourceType());
		//设置创建者
		if(currentUser!=null){
			userModel.setCreateOwnerId(currentUser.getId()!=null?currentUser.getId() : 0);
			userModel.setCreateOwner(currentUser.getName()!=null?currentUser.getName() : "admin");
		}
		adminUserService.createAdminUser(userModel, null);
		return Response.success();
		
	}
	/**
	 * 删除用户
	 * @param ids
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/deleteUsers")
	@ResponseBody
	public Response delete(String ids,HttpServletRequest request){
		Assert.notNull(ids, "要删除的用户的id不能为空");
		String[] id = ids.split(",");
		List<Integer> idList = new ArrayList<Integer>();
		for (int i = 0; i < id.length; i++) {
			//获得用户对象
			AdminUserModel userModel = adminUserService.getUser(Integer.valueOf(id[i]));
			Assert.notNull(userModel, "要删除的用户不存在");
			//判断用户是否是管理员
			if (userModel.getRoleType().equals(1)) {
				return Response.response(500, "admin管理员不允许删除");
			} else {
				idList.add(Integer.valueOf(id[i]));
			}	
		}
		adminUserService.deleteAdminUsers(idList);
		return Response.success();
	}
	/**
	 * 重置密码
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = { "/resetPasswordById", "/resetChannelPassword" })
	@ResponseBody
	public Response resetPasswordById(Integer userId){
		if (userId == null) {
			return Response.response(ResponseCode.INNER_ERROR, "重置密码失败，无法获得用户信息");
		}
		adminUserService.changePassword(userId, new AdminUserModel().getDefaultPassword());
		return Response.success();
	}
	
	
	/**
	 * 分页显示未授权给该User的角色
	 * @param page
	 * @param pageSize
	 * @param userId
	 * @param name
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = "/pagingQueryNotGrantRoles")
	@ResponseBody
	public Response pagingQueryNotGrantRoles(Integer page, Integer pageSize, Integer userId, String name,
			HttpServletRequest request){
		Page<AdminRoleModel> pageResult = adminRoleService.pagingQueryNotGrantRoles(page, pageSize, userId, name);
		return Response.success(pageResult);
		
	}
	
	/**
	 * 获取已分配给用户的角色
	 * @param page
	 * @param pageSize
	 * @param userId
	 * @param name
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = "/pagingQueryGrantRoleByUserId")
	@ResponseBody
	public Response pagingQueryGrantRoleByUserId(Integer page, Integer pageSize, Integer userId, String name,
			HttpServletRequest request){
		Page<AdminRoleModel> pageResult = adminRoleService.pagingQueryGrantRoles(page, pageSize, userId, name);
		return Response.success(pageResult);	
	}
	
	/**
	 * 对用户授予角色
	 * @param authorityIds
	 * 角色列Ids
	 * @param userId
	 * 用户Id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/grantAuthorityToActor")
	@ResponseBody
	public Response grantAuthorityToActor(String authorityIds, Integer actorId, HttpServletRequest request){
		Assert.notNull(actorId, "用户id不能为空");
		Assert.notNull(authorityIds, "角色列表不能为空");
		List<Integer> authorityIdList = new ArrayList<Integer>();
		String temp[] = authorityIds.split(",");
		for (int i = 0; i < temp.length; i++) {
			authorityIdList.add(Integer.valueOf(temp[i]));
		}
		adminUserService.grantAuthoritysToActor(actorId, authorityIdList);
		return Response.success();
	}
	
	/**
	 * 移除用户的角色
	 * @param authorityIds
	 * @param userId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/terminateAuthorizationByUserInRoles")
	@ResponseBody
	
	public Response terminateAuthorizationByUserInRoles(String roleIds, Integer userId, HttpServletRequest request){

		Assert.notNull(userId, "用户id不能为空");
		Assert.notNull(roleIds, "撤销角色列表不能为空");
		List<Integer> authorityIdList = new ArrayList<Integer>();
		String temp[] = roleIds.split(",");
		for (int i = 0; i < temp.length; i++) {
			authorityIdList.add(Integer.valueOf(temp[i]));
		}
		adminRoleService.terminateAuthorizationByUserIdAuthorithIds(userId, authorityIdList);
		return Response.success();
		
	}
	
	/**
	 * 
	 * 角色管理-查看成员
	 * @param roleId
	 * @param page
	 * @param pageSize
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pageQueryActorByRoleId")
	@ResponseBody
	public Response pageQueryActorByRoleId(Integer roleId,Integer page,Integer pageSize,HttpServletRequest request){
		Assert.notNull(roleId,"角色id不能为空");
		page=(page==null?0:page);
		pageSize=(pageSize==null?20:pageSize);
		Page<AdminUserModel> resultPage = adminUserService.pageQueryActorByRoleId(roleId,
				page, pageSize);
		return Response.success(resultPage);
		
	}
	/**
	 * 用户修改密码 注意前端密码是经过MD5加密后的密码，否则将不能正确匹配用户的密码
	 * 
	 * @param oldUserPassword
	 *            原密码
	 * @param userPassword
	 *            新密码
	 * @return
	 */
	@RequestMapping(value = "/safe_updatePassword", method = { RequestMethod.POST })
	@ResponseBody
	public Response updatePassword(String oldUserPassword, String userPassword, HttpServletRequest request,
			HttpServletResponse resp) {

		Integer userId = (Integer) request.getSession().getAttribute("userId");
		if (userId == null) {
			return Response.response(ResponseCode.INNER_ERROR, "修改密码失败，无法获得正确的登录用户信息");
		}
		AdminUserModel userModel = adminUserService.getUser(userId);
		if (userModel == null) {
			return Response.response(ResponseCode.INNER_ERROR, "修改密码失败，无法获得正确的登录用户信息");
		}
		String oldUserPassword_salt = userModel.handlePassword(oldUserPassword);
		if (!oldUserPassword_salt.equals(userModel.getPassword())) {
			return Response.response(ResponseCode.INNER_ERROR, "修改密码失败，原密码不正确");
		}
		logger.info("用户更改密码:用户{}更改密码为{}", userModel.getName(), userPassword);
		adminUserService.changePassword(userId, new AdminUserModel().handlePassword(userPassword));
		return Response.success();

	}

	/**
	 * 重置密码(重置为初始密码)
	 * 
	 * @param request
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/safe_resetPassword", method = { RequestMethod.POST })
	@ResponseBody
	public Response resetPassword(HttpServletRequest request, HttpServletResponse resp) {
		Integer userId = (Integer) request.getSession().getAttribute("userId");
		if (userId == null) {
			return Response.response(ResponseCode.INNER_ERROR, "重置密码失败，无法获得正确的登录用户信息");
		}
		AdminUserModel userModel = adminUserService.getUser(userId);
		if (userModel == null) {
			return Response.response(ResponseCode.INNER_ERROR, "重置密码失败，无法获得正确的登录用户信息");
		}
		adminUserService.changePassword(userId, new AdminUserModel().getDefaultPassword());
		return Response.success();

	}
	
	/**
	 * 移除角色下人员
	 * 
	 * @param roleId
	 * @param actorId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deleteUserInRole")
	@ResponseBody
	public Response deleteUserInRole(Integer roleId, Integer actorId, HttpServletRequest request) {
		adminUserService.deleteUserInRole(roleId, actorId);
		return Response.success();
	}

}

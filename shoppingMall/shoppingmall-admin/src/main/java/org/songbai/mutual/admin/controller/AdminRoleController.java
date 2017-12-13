
package org.songbai.mutual.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.songbai.mutual.admin.model.AdminMenuResourceModel;
import org.songbai.mutual.admin.model.AdminRoleModel;
import org.songbai.mutual.admin.model.AdminUserModel;
import org.songbai.mutual.admin.service.AdminMenuResourceService;
import org.songbai.mutual.admin.service.AdminRoleService;
import org.songbai.mutual.utils.BaseController;
import org.songbai.mutual.utils.mvc.Page;
import org.songbai.mutual.utils.mvc.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/role")
public class AdminRoleController extends BaseController {
	
	@Autowired
	AdminRoleService adminRoleService;
	
	@Autowired
	AdminMenuResourceService adminMenuResouceService;
	/**
	 * 角色管理-添加角色
	 * @param name
	 * @param description
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addRole")
	@ResponseBody
	public Response addRole(String name,String description,HttpServletRequest request){
		AdminRoleModel roleModel=new AdminRoleModel();
		//判断名称非空
		Assert.notNull(name,"角色名不能为空");
		roleModel.setName(name);
		roleModel.setDescription(description);
		roleModel.setIsAdmin(0);
		//获得当前用户的id即角色创建解决
		roleModel.setCreateId((int)request.getSession().getAttribute("userId"));
		adminRoleService.createRole(roleModel);
		return Response.success();
		
	}
	
	/**
	 * 角色管理-删除角色
	 * @param roleIds
	 * @return
	 */
	@RequestMapping(value = "/deleteRoles")
	@ResponseBody
	public Response deleteRoles(String roleIds){
		Assert.notNull(roleIds,"要删除的角色Id不能为空");
		List<Integer>roleList = new ArrayList<Integer>();
		String[]temp= roleIds.split(",");
		for(int i=0;i<temp.length;i++){
			roleList.add(Integer.valueOf(temp[i]));
			System.out.println(temp[i]);
		}
		//返回未删除的角色
		List<AdminRoleModel>notDeleted=adminRoleService.deleteRoles(roleList);
		return Response.success(notDeleted);
		
	}
	
	/**
	 * 查询平台渠道下的角色列表
	 */
	@RequestMapping(value = "/pagingQueryChannelRoles")
	@ResponseBody
	public Response pagingQueryChannelRoles(Integer page, Integer pageSize, HttpServletRequest request){
		Page<AdminRoleModel> pageResult=adminRoleService.pagingQueryChannelRoles(page, pageSize);
		return Response.success(pageResult);
		
	}
	/**
	 * 更新角色的name，description
	 * @param id
	 * @param name
	 * @param description
	 * @return
	 */
	@RequestMapping(value = "/updateRole")
	@ResponseBody
	public Response updateRole(Integer id,String name,String description){
		Assert.notNull(id, "角色id不能为空");
		Assert.notNull(name, "角色名称不能为空");
		AdminRoleModel roleModel = new AdminRoleModel();
		roleModel.setId(id);
		roleModel.setName(name);
		roleModel.setDescription(description);
		adminRoleService.updateAdminRole(roleModel);
		
		return Response.success();	
	}
	
	/**
	 * 打开资源分配页面（即权限分配页面）
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "/getAllMenuPageUrl")
	@ResponseBody
	public Response getAllMenuPageUrl(Integer roleId,HttpServletRequest request){
		List<AdminMenuResourceModel> menuList=adminMenuResouceService.getAllMenuPageUrl(roleId);
		return Response.success(menuList);
	}
	
	/**
	 * 添加权限给角色
	 * @param roleId
	 * @param securityResourceIds
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/grantResourcesToRole")
	@ResponseBody
	public Response grantResourcesToRole (Integer roleId, String securityResourceIds, HttpServletRequest request){
		Assert.notNull(roleId, "角色id不能为空");
		//如果勾选权限为空，则删除该角色的权限表
		if (securityResourceIds.equals("")) {
			adminMenuResouceService.deleteAdminResourceAssignmentsByAuthorityId(roleId);
		
			//添加权限给角色
		} else {
			String[] ids = securityResourceIds.split(",");
			List<Integer> securityResourceList = new ArrayList<Integer>();
			for (int i = 0; i < ids.length; i++) {
				securityResourceList.add(Integer.valueOf(ids[i]));
			}
			adminMenuResouceService.saveMenuPageUrlToRole(roleId, securityResourceList);
		}
		return Response.success();
		
	}
	
	/**
	 * 获得当前id的权限菜单
	 * @param roleId
	 * @param request
	 * @return
	 */
	
	
	@RequestMapping(value = "/findMenuResourceTreeSelectItemByRoleId")
	@ResponseBody
	public Response findMenuResourceTreeSelectItemByRoleId(Integer roleId, HttpServletRequest request){
		
		List<AdminMenuResourceModel> allMenuResources = adminMenuResouceService.
				findMenuResourceTreeSelectItemByRoleId(roleId);
		
		return Response.success(allMenuResources);
		
	}

}


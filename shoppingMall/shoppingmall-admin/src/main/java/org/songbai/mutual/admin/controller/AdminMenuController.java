
package org.songbai.mutual.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.songbai.mutual.admin.model.AdminMenuResourceModel;
import org.songbai.mutual.admin.service.AdminMenuResourceService;
import org.songbai.mutual.utils.BaseController;
import org.songbai.mutual.utils.mvc.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * 菜单管理
 * @author huanglei
 * @date 2017年4月12日
 *
 */
@Controller
@RequestMapping("/menu")
public class AdminMenuController extends BaseController {
	
	@Autowired
	AdminMenuResourceService adminMenuResourceService;

	/**
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/safe_getMenuWithRoleId")
	@ResponseBody
	public Response getMenuWithRoleId(HttpServletRequest request, HttpServletResponse response) {
		List<AdminMenuResourceModel> result = (List<AdminMenuResourceModel>) request.getSession().getAttribute("menu");
		return Response.success(result);
	}
	

	@RequestMapping(value = "/pagingqueryMenu")
	@ResponseBody
	public Response pagingqueryMenu(){
		List<AdminMenuResourceModel> menuList=adminMenuResourceService.findMenuResources(null);
		return Response.success(menuList);
		
	}
	
	@RequestMapping(value = "/findMenus")
	@ResponseBody
	public Response findMenu(Integer level){
		List<AdminMenuResourceModel> menuResourceModels = adminMenuResourceService.findMenuResources(level);
		return Response.success(menuResourceModels);
		
	}
	
	/**
	 * 新增菜单并调整层级
	 * @param name
	 * @param url
	 * @param position
	 * @param parentId
	 * @param descrition
	 * @param menuIcon
	 * @return
	 */
	@RequestMapping(value = "/saveMenu")
	@ResponseBody
	public Response addMenu(String name,String url,Integer position,Integer parentId,String descrition,String menuIcon){
		//设置默认为一级目录
		parentId = parentId == null ? 0 : parentId;
		//为空判断
		Assert.notNull(name,"菜单名称不能为空");
		
		AdminMenuResourceModel menuModel=new AdminMenuResourceModel();
		menuModel.setName(name);
		menuModel.setUrl(url);
		menuModel.setPosition(position);
		menuModel.setParentId(parentId);
		menuModel.setDescription(descrition);
		menuModel.setMenuIcon(menuIcon);
		adminMenuResourceService.saveMenu(menuModel);
		return Response.success();
		
	}
	
	@RequestMapping(value = "/updateMenu")
	@ResponseBody
	public Response updateMenu(String name,String url,Integer position,Integer parentId,String description,Integer id){
		//非空判断
		Assert.notNull(parentId, "上级菜单Id不能为空！！");
		Assert.notNull(id, "菜单Id不能为空！！");
		Assert.notNull(name, "菜单名称不能为空！！");
		AdminMenuResourceModel menuModel=new AdminMenuResourceModel();
		menuModel.setId(id);
		menuModel.setName(name);
		menuModel.setUrl(url);
		menuModel.setPosition(position);
		menuModel.setParentId(parentId);
		menuModel.setDescription(description);
		adminMenuResourceService.updateMenu(menuModel);
		return Response.success();
		
	}
	/**
	 * 删除菜单(含子菜单时同时删除子菜单)
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/removeMenus")
	@ResponseBody
	public Response removeMenus(String ids){
		Assert.notNull(ids, "要删除的菜单Id不能为空");
		List<Integer> menuIds = new ArrayList<Integer>();
		String temp[] = ids.split(",");
		for (int i = 0; i < temp.length; i++) {
			menuIds.add(Integer.valueOf(temp[i]));
		}
		List<AdminMenuResourceModel>notDeleted=adminMenuResourceService.removeMenus(menuIds);
		return Response.success();
		
	}
	/**
	 * 获取叶子菜单
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getLeafMenu")
	@ResponseBody
	public Response getLeafMenu(HttpServletRequest request, HttpServletResponse response) {
		List<AdminMenuResourceModel> leafMenu = adminMenuResourceService.getAllLeafMenu();
		return Response.success(leafMenu);
	}
	/**
	 * 根据上级菜单Id获得所有的子菜单 完善权限模块之后注意权限的处理
	 * 
	 * @param parentCode
	 * @return
	 */
	@RequestMapping(value = "/getChildMenuByParentCode")
	@ResponseBody
	public Response getChildMenuByParentCode(Integer parentId) {
		AdminMenuResourceModel menuResourceModel = adminMenuResourceService.getMenuPedigree(parentId);
		return Response.success(menuResourceModel.getChildren());
	}
	
	
	/**
	 * 组装菜单数据 将菜单数据组装成前端认识的数据 注意attributes属性中的数据
	 * 
	 * @param adminMenuModels
	 * @return
	 */
	private List<HashMap<String, Object>> handleMenu(AdminMenuResourceModel menuResourceModel) {
		List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < menuResourceModel.getChildren().size(); i++) {
			AdminMenuResourceModel menuModel = menuResourceModel.getChildren().get(i);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("id", menuModel.getId());
			map.put("text", menuModel.getName());
			map.put("state",
					(menuModel.getChildren() != null && menuModel.getChildren().size() > 0) ? "open" : "close");
			map.put("url", menuModel.getUrl());// 这个URL是为了treeGrid 下同index和desc
			map.put("index", menuModel.getPosition());
			map.put("desc", menuModel.getDescription());

			if (menuModel.getChildren() != null && menuModel.getChildren().size() > 0) {
				map.put("children", this.handleMenu(menuModel));
			}
			if (menuModel.getUrl() != null) {
				HashMap<String, Object> attrMap = new HashMap<String, Object>();
				attrMap.put("url", menuModel.getUrl());
				map.put("attributes", attrMap);
			}
			result.add(map);
		}
		return result;
	}

	
	
	
}


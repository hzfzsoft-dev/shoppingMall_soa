package org.songbai.mutual.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.songbai.mutual.admin.model.AdminPageElementResourceModel;
import org.songbai.mutual.admin.model.AdminUserModel;
import org.songbai.mutual.admin.service.AdminPageElementResourceService;
import org.songbai.mutual.utils.mvc.ResponseCode;
import org.songbai.mutual.utils.BaseController;
import org.songbai.mutual.utils.mvc.Page;
import org.songbai.mutual.utils.mvc.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/pageElement")
public class AdminPageElementResourceController extends BaseController {

	@Autowired
	AdminPageElementResourceService adminPageElementResourceService;

	/**
	 * 根据登录用户来获取 用户所用有的pageElement
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/safe_getPageElementAll")
	@ResponseBody
	public Response getPageElementAll(HttpServletRequest request, HttpServletResponse response) {
		Integer actorId = (Integer) request.getSession().getAttribute("userId");
		AdminUserModel user = (AdminUserModel) request.getSession().getAttribute("user");
		List<String> pageElementResourcesAll = adminPageElementResourceService.getPageElementByActorId(actorId,
				user);
		return Response.success(pageElementResourcesAll);
	}
	@RequestMapping(value = "/pagingQuery")
	@ResponseBody
	public Response pagingQuery(Integer parentId,Integer pageSize,Integer page){
		pageSize = (pageSize == null ? Page.DEFAULE_PAGESIZE : pageSize);
		page = (page == null ? 0 : page);
		Page<AdminPageElementResourceModel> pageResult=adminPageElementResourceService.pagingQueryPageElement(parentId, pageSize, page);
		return Response.success(pageResult);
		
	}
	/**
	 * 添加页面元素（添加是页面标识不能重复）
	 * @param name
	 * @param url
	 * @param identifier
	 * 页面元素标识
	 * @param description
	 * @param menuId
	 * 上级菜单的id
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/addPageElement")
	@ResponseBody
	public Response addPageElement(AdminPageElementResourceModel pageElementModel){
		Assert.notNull(pageElementModel,"传入页面元素为空");
		Assert.notNull(pageElementModel.getName(),"页面元素名称不能为空");
		Assert.notNull(pageElementModel.getUrl(),"url不能为空");
		Assert.notNull(pageElementModel.getIdentifier(),"页面元素标识不能为空");
		pageElementModel.setCategory(AdminPageElementResourceModel.CATEGORY);
		//判断identifier(页面标识不能重复)
		if (adminPageElementResourceService.hasPageElementByIdentifier(pageElementModel.getIdentifier())) {
			return Response.response(ResponseCode.INNER_ERROR, "保存失败，已存在的元素标识");
		}
		adminPageElementResourceService.addPageElement(pageElementModel);
		return Response.success();
		
	}
	
	/**
	 * 删除页面元素（软删除），如果该权限被角色拥有，先删除关联，再设置状态
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/deletePageElement" )
	@ResponseBody
	
	public Response deletePageElement(String ids){
		//非空判断
		Assert.notNull(ids,"需要删除的id不能为空");
		List<Integer> idList=new ArrayList();
		String[] temp=ids.split(",");
		//转化成Integer
		for(int i=0;i<temp.length;i++){
			idList.add(Integer.valueOf(temp[i]));
		}
		adminPageElementResourceService.deletePageElement(idList);
		return Response.success();
		
	}
	/**
	 * 更新操作
	 * @param id
	 * @param name
	 * @param url
	 * @param identifier
	 * @param description
	 * @param menuId
	 *	对应DB中的parent_id
	 * @return
	 */
	@RequestMapping(value = "/updatePageElement" )
	@ResponseBody
	public Response updatePageElement(Integer id,String name,String url,String identifier,String description,Integer menuId){
		//非空判断
		Assert.notNull(name,"页面元素名称不能为空");
		Assert.notNull(url,"url不能为空");
		Assert.notNull(identifier,"页面元素标识不能为空");
		AdminPageElementResourceModel pageElementModel=new AdminPageElementResourceModel();
		pageElementModel.setId(id);
		pageElementModel.setName(name);
		pageElementModel.setUrl(url);
		pageElementModel.setIdentifier(identifier);
		pageElementModel.setParentId(menuId);
		adminPageElementResourceService.updatePageElement(pageElementModel);
		return Response.success();
		
	}
	
	
	
	
	
	
	
	
	
}

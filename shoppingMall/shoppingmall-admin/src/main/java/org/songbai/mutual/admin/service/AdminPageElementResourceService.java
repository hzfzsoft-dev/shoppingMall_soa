package org.songbai.mutual.admin.service;

import java.util.List;

import org.songbai.mutual.utils.mvc.Page;
import org.songbai.mutual.admin.model.AdminPageElementResourceModel;
import org.songbai.mutual.admin.model.AdminUserModel;

public interface AdminPageElementResourceService {

	/**
	 * 根据登录用户来获取 用户所用有的pageElement
	 * 
	 * @param actorId
	 * @return
	 */
	public List<String> getPageElementByActorId(Integer actorId,AdminUserModel user);
	
	/**
	 * 根据parentId获取页面元素
	 * @param parentId
	 * @param pageSize
	 * @param page
	 * @return
	 */
	public Page<AdminPageElementResourceModel> pagingQueryPageElement(Integer parentId,Integer pageSize,Integer page);
	
	
	public void addPageElement(AdminPageElementResourceModel pageElementModel);

	public boolean hasPageElementByIdentifier(String identifier);
	
	/**
	 * 软删除页面元素（先删除页面元素权限映射表，再修改状态）
	 * @param idList
	 */
	public void deletePageElement(List<Integer> idList);
	
	/**
	 * 更新页面元素
	 * @param pageElementModel
	 */
	public void updatePageElement(AdminPageElementResourceModel pageElementModel);

}

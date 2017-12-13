package org.songbai.mutual.admin.service;

import java.util.List;

import org.songbai.mutual.admin.model.AdminSecurityResourceModel;

public interface AdminUrlAccessResourceService {
	
	/**
	 * 
	 * @param menuId
	 * @param category
	 * @param type
	 * @return
	 */
	public List<AdminSecurityResourceModel> getAllByMenuId(Integer menuId, String category);

	

	/**
	 * 获取用户下的权限
	 * 
	 * @param actorId
	 * @return
	 */
	public List<AdminSecurityResourceModel> getSecurityResourcesByActorId(Integer actorId,
			String category);
}

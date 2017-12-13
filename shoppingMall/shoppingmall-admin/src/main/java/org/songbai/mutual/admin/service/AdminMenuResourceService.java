
package org.songbai.mutual.admin.service;

import java.util.List;

import org.songbai.mutual.admin.model.AdminMenuResourceModel;
import org.songbai.mutual.admin.model.AdminUserModel;

public interface AdminMenuResourceService {

	/**
	 * 根据参与者Id获得该参与者拥有权限的所有菜单
	 * 
	 * @param actorId
	 * @return
	 */
	public List<AdminMenuResourceModel> getMenuPedigreeByActorId(AdminUserModel actor);
	/**
	 * 根据角色id，获得该角色所有的权限	
	 * @param roleId
	 * @return AdminMenuResourceModel
	 */
	public List<AdminMenuResourceModel> getAllMenuPageUrl(Integer roleId);
	
	public void deleteAdminResourceAssignmentsByAuthorityId(Integer roleId);
	
	public void saveMenuPageUrlToRole(Integer roleId, List<Integer> securityResourceList);
	
	public List<AdminMenuResourceModel> findMenuResourceTreeSelectItemByRoleId(Integer roleId);
	
	public List<AdminMenuResourceModel> findMenuResources(Integer level);
	
	public void saveMenu(AdminMenuResourceModel menuModel);
	
	public void updateMenu(AdminMenuResourceModel menuModel);
	
	public List<AdminMenuResourceModel> removeMenus(List<Integer> ids);
	
	public List<AdminMenuResourceModel> getAllLeafMenu();
	public AdminMenuResourceModel getMenuPedigree(Integer parentId);
	

}

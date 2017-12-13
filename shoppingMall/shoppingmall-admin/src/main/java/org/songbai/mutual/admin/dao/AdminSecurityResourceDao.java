package org.songbai.mutual.admin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.songbai.mutual.admin.model.AdminMenuResourceModel;
import org.songbai.mutual.admin.model.AdminPageElementResourceModel;
import org.songbai.mutual.admin.model.AdminSecurityResourceModel;

public interface AdminSecurityResourceDao {

	// ***********************菜单权限资源*****************************

	/**
	 * 
	 * 多条件查询菜单
	 * 
	 * @return
	 */
	public List<AdminMenuResourceModel> findMenuResources(AdminMenuResourceModel menuResourceModel);

	/**
	 * 根据父菜单Id获得子菜单集合
	 * 
	 * @param parentId
	 * @return
	 */
	public List<AdminMenuResourceModel> getChildMenu(@Param("parentId") Integer parentId,
			@Param("status") Integer status, @Param("category") String category);

	/**
	 * 根据角色Id获得分配给该角色的菜单资源数据
	 * 
	 * @param roleId
	 * @param category
	 * @param level
	 *            菜单层级
	 * @return
	 */
	public List<AdminMenuResourceModel> findMenuResourcesByRoleId(@Param("roleId") Integer roleId,
			@Param("category") String category, @Param(value = "level") Integer level,
			@Param(value = "status") Integer status);

	/**
	 * 根据菜单id和角色id获得所有授权给该角色的该菜单的子菜单集合 适用于根据父菜单和角色数据获得已授权的子菜单集合
	 * 
	 * @param parentId
	 * @param roleId
	 * @return
	 */
	public List<AdminMenuResourceModel> queryMenuResourcesByParentIdRoleId(@Param(value = "parentId") Integer parentId,
			@Param(value = "roleId") Integer roleId, @Param(value = "status") Integer status,
			@Param(value = "category") String category);

	/**
	 * 通用查询，根据菜单id查询菜单所属的页面元素和后台方法，
	 * 
	 * @param menuId
	 * @param category
	 * @return
	 */
	public List<AdminSecurityResourceModel> getAllByMenuId(@Param(value = "menuId") Integer menuId,
			@Param(value = "category") String category);

	/**
	 * 根据用户id 查询actor的页面元素
	 * 
	 * @param actorId
	 * @param category
	 * @return
	 */
	public List<AdminSecurityResourceModel> getSecurityResourcesByActorId(@Param(value = "actorId") Integer actorId,
			 @Param(value = "category") String category);

	// ***************** URL访问权限资源*************************

	// *****************页面元素资源权限资源*************************
	/**
	 * 根据登录用户来获取 用户所用有的pageElement
	 * 
	 * @param actorId
	 * @param category
	 * @return
	 */

	public List<String> getPageElementByActorId(@Param(value = "actorId") Integer actorId,
			@Param(value = "category") String category);

	public List<String> getPageElementAll(@Param(value = "category") String category);
	
	public void createMenu(AdminMenuResourceModel model);

	public AdminMenuResourceModel getMenu(@Param(value="id")Integer id,@Param(value="status") Integer status,@Param(value="category") String category);

	public void updateMenu(AdminMenuResourceModel menuModel);
	
	/**
	 * 更新状态软删除
	 * @param id
	 */
	public void updateStatus(Integer id);

	public List<AdminPageElementResourceModel> pagingQueryPageElement(@Param(value="limit")Integer limit,@Param(value="pageSize")Integer pageSize,
			@Param(value="parentId")Integer parentId, @Param(value="category") String category);

	public Integer pagingQueryPageElement_count(@Param(value="parentId") Integer parentId);
	
	public void addPageElement(AdminPageElementResourceModel pageElementModel);

	public List<AdminPageElementResourceModel> findPageElementByIdentifier(@Param(value="identifier")String identifier);

	public void updatePageElement(AdminPageElementResourceModel pageElementModel);

	public List<AdminMenuResourceModel> getAllLeafMenu(@Param(value="category") String category);

	public Integer pagingQueryPageElement_count(Map<String, Object> param);

}



package org.songbai.mutual.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.songbai.mutual.admin.dao.AdminActorDao;
import org.songbai.mutual.admin.dao.AdminAuthorityDao;
import org.songbai.mutual.admin.dao.AdminResourceAssignmentDao;
import org.songbai.mutual.admin.dao.AdminSecurityResourceDao;
import org.songbai.mutual.admin.model.AdminMenuResourceModel;
import org.songbai.mutual.admin.model.AdminPageElementResourceModel;
import org.songbai.mutual.admin.model.AdminPopModel;
import org.songbai.mutual.admin.model.AdminResourceAssignmentModel;
import org.songbai.mutual.admin.model.AdminRoleModel;
import org.songbai.mutual.admin.model.AdminSecurityResourceModel;
import org.songbai.mutual.admin.model.AdminUserModel;
import org.songbai.mutual.admin.service.AdminMenuResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminMenuResouceServiceImpl implements AdminMenuResourceService {

	public static final Integer MENU_STATUS_NOTDELETE = 1;

	@Autowired
	AdminSecurityResourceDao adminSecurityResourceDao;

	@Autowired
	AdminResourceAssignmentDao adminResourceAssignmentDao;

	@Autowired
	AdminAuthorityDao adminAuthorityDao;

	@Autowired
	AdminActorDao adminActorDao;

	public List<AdminMenuResourceModel> getMenuPedigreeByActorId(AdminUserModel actor) {
		if (actor == null) {
			return new ArrayList<AdminMenuResourceModel>();
		}
		/**
		 * 1.根据actorId判断 actor是否是admin 是的话返回所有的 menu 2.不是的话根据
		 * actorId获取其角色来获取menu
		 */
		List<AdminMenuResourceModel> firstMenus = new ArrayList<AdminMenuResourceModel>();
		if ("admin".equals(actor.getUserAccount())) {
			// 获得所有的一级菜单
			AdminMenuResourceModel menuResourceModel = new AdminMenuResourceModel();
			menuResourceModel.setCategory(AdminMenuResourceModel.CATEGORY);
			menuResourceModel.setLevel(0);
			List<AdminMenuResourceModel> firstMenuResourceModels = adminSecurityResourceDao
					.findMenuResources(menuResourceModel);
			for (AdminMenuResourceModel firstMenuResourceModel : firstMenuResourceModels) {
				if (!this.hasMenuContent(firstMenus, firstMenuResourceModel)) {
					firstMenus.add(firstMenuResourceModel);
				}
			}
			for (AdminMenuResourceModel firstMenu : firstMenus) {
				handleChilden(firstMenu);
			}
			return firstMenus;
		} else {
			List<AdminRoleModel> roleModels = adminAuthorityDao.queryGrantRolesByUserId(actor.getId(),
					AdminRoleModel.CATEGORY);
			/**
			 * 获得已授权的一级菜单数据
			 */
			for (AdminRoleModel roleModel : roleModels) {
				List<AdminMenuResourceModel> firstMenuResourceModels_temp = adminSecurityResourceDao
						.findMenuResourcesByRoleId(roleModel.getId(), AdminMenuResourceModel.CATEGORY, 0,
								MENU_STATUS_NOTDELETE);
				for (AdminMenuResourceModel temp_menu : firstMenuResourceModels_temp) {
					if (!this.hasMenuContent(firstMenus, temp_menu)) {
						firstMenus.add(temp_menu);
					}
				}
			}
			/**
			 * 组装一级菜单下的已授权的子菜单数据
			 */
			for (AdminMenuResourceModel firstMenu : firstMenus) {
				handleChildenMeusByRoleIds(firstMenu, roleModels);
			}
			return firstMenus;
		}
	}
	
	
	public List<AdminMenuResourceModel> getAllMenuPageUrl(Integer roleId) {
		/**
		 * 1：获得所有的一级菜单，一级菜单应该是不会有相关的页面和URL的配置 2：根据一级菜单获得子菜单（注意需要递归，因为菜单的层级不能确定）
		 * 3：处理子菜单，子菜单授权检查、页面元素和URL数据关联和授权检查
		 */
		List<AdminMenuResourceModel> topMenus = this.findTopMenuResources();
		for (AdminMenuResourceModel topMenu : topMenus) {
			topMenu.setChecked(this.isAssignmen(roleId, topMenu.getId()));
			this.handleChildMenu(topMenu,roleId);
		}
		return topMenus;
	}



	public void deleteAdminResourceAssignmentsByAuthorityId(Integer roleId) {
		adminResourceAssignmentDao.deleteAdminResourceAssignmentsByAuthorityId(roleId);
		
	}



	public void saveMenuPageUrlToRole(Integer roleId, List<Integer> securityResourceList) {
		List<Integer> oldAminResourcesAssignment = adminResourceAssignmentDao
				.getAdminResourceAssignmentIdsByRoleId(roleId);
		//先删除原先权限
		if (!oldAminResourcesAssignment.isEmpty()) {
			adminResourceAssignmentDao.deleteAdminResourceAssignmentsByAuthorityId(roleId);
		}
		//再添加现在勾选的权限 
		if (securityResourceList.size()> 0) {
			List<AdminResourceAssignmentModel> list = new ArrayList<AdminResourceAssignmentModel>();
			for (Integer securityResourceId : securityResourceList) {
				AdminResourceAssignmentModel resourceAssignmentModel = new AdminResourceAssignmentModel();
				resourceAssignmentModel.setAuthorityId(roleId);
				resourceAssignmentModel.setSecurityResourceId(securityResourceId);
				list.add(resourceAssignmentModel);
			}
			adminResourceAssignmentDao.creatAdminResourceAssignment(list);
		}
		
	}



	public List<AdminMenuResourceModel> findMenuResourceTreeSelectItemByRoleId(Integer roleId) {
		//一级菜单
				List<AdminMenuResourceModel> topMenuResources = this.findTopMenuResources();
				//
				List<AdminMenuResourceModel> allMenuResourcesAsRole = adminSecurityResourceDao.findMenuResourcesByRoleId(roleId,
						AdminMenuResourceModel.CATEGORY, null, MENU_STATUS_NOTDELETE);
				//拉出下级菜单
				for (AdminMenuResourceModel menuResourceModel : topMenuResources) {
					menuResourceModel.setChecked(this.content(menuResourceModel, allMenuResourcesAsRole));
					menuResourceModel.setChildren(this.getChildren(menuResourceModel.getId(), allMenuResourcesAsRole));
				}
				return topMenuResources;
			
	}


	/**
	 * 查询菜单
	 */
	public List<AdminMenuResourceModel> findMenuResources(Integer level) {
		AdminMenuResourceModel menuResourceModel=new AdminMenuResourceModel();
		menuResourceModel.setLevel(level);
		menuResourceModel.setCategory(AdminMenuResourceModel.CATEGORY);
		List<AdminMenuResourceModel> menuList=adminSecurityResourceDao.findMenuResources(menuResourceModel);
		return menuList;
	}


	/**
	 * 增加菜单同时调整层级和父节点叶子属性
	 */
	public void saveMenu(AdminMenuResourceModel menuModel) {
		// 设置层级和调整父节点信息
		if (menuModel.getParentId() != null) {
			// 获得父节点实体
			AdminMenuResourceModel parentNode = adminSecurityResourceDao.getMenu(menuModel.getParentId(),
					MENU_STATUS_NOTDELETE, AdminMenuResourceModel.CATEGORY);
			// 设置层级
			menuModel.setLevel(parentNode.getLevel() + 1);
			parentNode.setIsLeaf(false);
			// 保存父节点信息
			adminSecurityResourceDao.updateMenu(parentNode);
		}
		// 设置默认范畴
		menuModel.setCategory(AdminMenuResourceModel.CATEGORY);
		adminSecurityResourceDao.createMenu(menuModel);

	}



	public void updateMenu(AdminMenuResourceModel menuModel) {
		adminSecurityResourceDao.updateMenu(menuModel);
		
	}



	public List<AdminMenuResourceModel> removeMenus(List<Integer> ids) {
		List<AdminMenuResourceModel> result=new ArrayList<AdminMenuResourceModel>();
		for (Integer id : ids) {
			// 判断是否还有子菜单
			if (!hasChildren(id)) {
				//做状态更新软删除
				adminSecurityResourceDao.updateStatus(id);
				//删除与菜单相关联的权限记录
				adminResourceAssignmentDao.deleteAdminResourceAssignment(id, null);
			} else if (hasChildren(id)) {
				adminSecurityResourceDao.updateStatus(id);
				// 获取该菜单的子菜单
				List<AdminMenuResourceModel> menuModels = adminSecurityResourceDao.getChildMenu(id,
						MENU_STATUS_NOTDELETE, AdminMenuResourceModel.CATEGORY);
				for (AdminMenuResourceModel menuModel : menuModels ) {
					//软删除子菜单
					adminSecurityResourceDao.updateStatus(menuModel.getId());
					//删除与子菜单相关联的权限记录
					adminResourceAssignmentDao.deleteAdminResourceAssignment(menuModel.getId(), null);
				}
			} else {
				result.add(adminSecurityResourceDao.getMenu(id, null, null));
			}
		}
		return result;
	}



	public List<AdminMenuResourceModel> getAllLeafMenu() {
		List<AdminMenuResourceModel> leafMenus = new ArrayList<AdminMenuResourceModel>();
		List<AdminMenuResourceModel> menuResourceModels = adminSecurityResourceDao.getAllLeafMenu(
				AdminMenuResourceModel.CATEGORY);
		for (AdminMenuResourceModel menuResourceModel : menuResourceModels) {
			if (!this.hasMenuContent(leafMenus, menuResourceModel)) {
				leafMenus.add(menuResourceModel);
			}
		}
		return leafMenus;
	}



	public AdminMenuResourceModel getMenuPedigree(Integer id) {
		id = id == null ? 0 : id;
		AdminMenuResourceModel menuResourceModel = adminSecurityResourceDao.getMenu(id, MENU_STATUS_NOTDELETE,
				AdminMenuResourceModel.CATEGORY);
		menuResourceModel.setChildren(this.getChildren(menuResourceModel.getId()));
		return menuResourceModel;
	}
	/**
	 * 获得顶级菜单 顶级菜单的层级为0 菜单维护时一定要注意菜单的层级
	 * 
	 * @return
	 */
	public List<AdminMenuResourceModel> findTopMenuResources() {
		AdminMenuResourceModel menuResourceModel = new AdminMenuResourceModel();
		menuResourceModel.setCategory(AdminMenuResourceModel.CATEGORY);
		menuResourceModel.setLevel(0);
		return adminSecurityResourceDao.findMenuResources(menuResourceModel);
	}

	
	
	/**
	 * 递归查找菜单下所有的子菜单
	 * 
	 * @param parentId
	 * @return
	 */
	private List<AdminMenuResourceModel> getChildren(Integer parentId) {
		List<AdminMenuResourceModel> result = adminSecurityResourceDao.getChildMenu(parentId, MENU_STATUS_NOTDELETE,
				AdminMenuResourceModel.CATEGORY);
		for (AdminMenuResourceModel menuResourceModel : result) {
			if (this.getChildren(menuResourceModel.getId()) != null
					&& this.getChildren(menuResourceModel.getId()).size() > 0) {
				menuResourceModel.setChildren(this.getChildren(menuResourceModel.getId()));
			}
		}
		return result;
	}
	private List<AdminMenuResourceModel> getChildren(Integer id, List<AdminMenuResourceModel> allMenuResourcesAsRole) {
		List<AdminMenuResourceModel> result = adminSecurityResourceDao.getChildMenu(id, MENU_STATUS_NOTDELETE,
				AdminMenuResourceModel.CATEGORY);
		for (AdminMenuResourceModel menuResourceModel : result) {
			menuResourceModel.setChecked(this.content(menuResourceModel, allMenuResourcesAsRole));
			if (this.getChildren(menuResourceModel.getId()) != null
					&& this.getChildren(menuResourceModel.getId()).size() > 0) {
				menuResourceModel.setChildren(this.getChildren(menuResourceModel.getId(), allMenuResourcesAsRole));
			}
		}
		return result;
	}
	/**
	 * 递归查找菜单下所有的子菜单
	 * 
	 * @param parentId
	 * @return
	 */
	private List<AdminMenuResourceModel> getChildrenIsType(Integer parentId) {
		List<AdminMenuResourceModel> result = adminSecurityResourceDao.getChildMenu(parentId, MENU_STATUS_NOTDELETE,
				AdminMenuResourceModel.CATEGORY);
		for (AdminMenuResourceModel menuResourceModel : result) {
			if (this.getChildrenIsType(menuResourceModel.getId()) != null
					&& this.getChildrenIsType(menuResourceModel.getId()).size() > 0) {
				menuResourceModel.setChildren(this.getChildrenIsType(menuResourceModel.getId()));
			}
		}
		return result;
	}
	
	/**
	 * 集合中是否已存在给菜单 菜单对象是否相等或菜单对象的id是否相等
	 * 
	 * @param menuResourceModels
	 * @param resourceModel
	 * @return
	 */
	private boolean hasMenuContent(List<AdminMenuResourceModel> menuResourceModels,
			AdminMenuResourceModel resourceModel) {

		for (AdminMenuResourceModel model : menuResourceModels) {
			if (model.getId().intValue() == resourceModel.getId().intValue()
					|| menuResourceModels.contains(resourceModel)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 分配给角色的菜单资源集合中是否包含指定的菜单资源
	 * 
	 * @param menuResourceModel
	 * @param allMenuResourcesAsRole
	 * @return
	 */
	private boolean content(AdminMenuResourceModel menuResourceModel,
			List<AdminMenuResourceModel> allMenuResourcesAsRole) {
		for (AdminMenuResourceModel menuResourceModelAsRole : allMenuResourcesAsRole) {
			if (menuResourceModelAsRole.getId() == menuResourceModel.getId()) {
				return true;
			}
		}
		return false;
	}
	
	private void handleChilden(AdminMenuResourceModel menuModel) {
		/**
		 * 1：获取所有的菜单数据 2：根据菜单的上下级关系组装菜单数据 3：递归子菜单
		 */
		List<AdminMenuResourceModel> childrenMenus = getChildenMeusByMenuId(menuModel.getId());
		if (childrenMenus != null && childrenMenus.size() > 0) {
			menuModel.setChecked(true);
			menuModel.setChildren(childrenMenus);
		}
	}

	private List<AdminMenuResourceModel> getChildenMeusByMenuId(Integer menuId) {
		List<AdminMenuResourceModel> result = new ArrayList<AdminMenuResourceModel>();
		List<AdminMenuResourceModel> temp_list = adminSecurityResourceDao.getChildMenu(menuId, MENU_STATUS_NOTDELETE,
				AdminMenuResourceModel.CATEGORY);
		for (AdminMenuResourceModel menu_temp : temp_list) {
			if (!this.hasMenuContent(result, menu_temp)) {
				result.add(menu_temp);
			}
		}
		return result;
	}

	/**
	 * 递归组装一级菜单下所有分配给角色的菜单数据
	 * 
	 * @param firstMenu
	 * @param roleModels
	 */
	private void handleChildenMeusByRoleIds(AdminMenuResourceModel menuModel, List<AdminRoleModel> roleModels) {
		/**
		 * 1：根据角色数据获得所有的菜单数据 2：根据菜单的上下级关系组装菜单数据 3：递归子菜单
		 */
		List<AdminMenuResourceModel> childrenMenus = getChildenMeusByMenuIdRoles(menuModel.getId(), roleModels);
		if (childrenMenus != null && childrenMenus.size() > 0) {
			menuModel.setChecked(true);
			menuModel.setChildren(childrenMenus);
		}
	}

	/**
	 * 带权限查询子菜单 关于带权限子菜单数据的查询
	 * 根据角色id和菜单的id获得子菜单的数据，汇总成子菜单的一个集合，该集合需要验证子菜单是否已经存在，如果存在了就不添加了
	 * 因为多角色情况下不同的角色授权的菜单数据可能会重叠，需要判重操作
	 * 
	 * @param menuId
	 * @param roleModels
	 * @return
	 */
	private List<AdminMenuResourceModel> getChildenMeusByMenuIdRoles(Integer menuId, List<AdminRoleModel> roleModels) {
		List<AdminMenuResourceModel> result = new ArrayList<AdminMenuResourceModel>();
		for (AdminRoleModel roleModel : roleModels) {
			List<AdminMenuResourceModel> temp_list = adminSecurityResourceDao.queryMenuResourcesByParentIdRoleId(menuId,
					roleModel.getId(), MENU_STATUS_NOTDELETE, AdminMenuResourceModel.CATEGORY);
			for (AdminMenuResourceModel menu_temp : temp_list) {
				if (!this.hasMenuContent(result, menu_temp)) {
					result.add(menu_temp);
				}
			}
		}
		return result;
	}




	/**
	 * 数据授权验证
	 * 
	 * @param roleId
	 * @param resourceId
	 * @return
	 */
	private boolean isAssignmen(Integer roleId, Integer resourceId) {
		AdminResourceAssignmentModel assignmentModel = adminResourceAssignmentDao.getByRoleIdAthourIdResourceId(
				roleId, resourceId);
		return assignmentModel != null;
	}
	
	/**
	 * 子菜单数据以及相关数据的组装 1: 检查是否授权 2：页面元素和URL数据获得 3：检查页面和URL数据的授权
	 * 4：递归一下是否还有子菜单，基本上是不会有的
	 * 
	 * @param topMenu
	 * @param roleId
	 */
	private void handleChildMenu(AdminMenuResourceModel topMenu, Integer roleId) {
		List<AdminMenuResourceModel> childMenus = this.getChildrenIsType(topMenu.getId());
		for (AdminMenuResourceModel childMenu : childMenus) {
			List<AdminSecurityResourceModel> pageElementResourceModels = adminSecurityResourceDao
					.getAllByMenuId(childMenu.getId(), AdminPageElementResourceModel.CATEGORY);
			for (AdminSecurityResourceModel resourceModel : pageElementResourceModels) {
				resourceModel.setChecked(this.isAssignmen(roleId, resourceModel.getId()));
			}
			childMenu.setPageElements(pageElementResourceModels);

			List<AdminSecurityResourceModel> popupModels = adminSecurityResourceDao.getAllByMenuId(childMenu.getId(),
					AdminPopModel.CATEGORY);
			for (AdminSecurityResourceModel resourceModel : popupModels) {
				resourceModel.setChecked(this.isAssignmen(roleId, resourceModel.getId()));
			}
			childMenu.setPopup(popupModels);
			childMenu.setChecked(this.isAssignmen(roleId, childMenu.getId()));
			List<AdminMenuResourceModel> sonMenus = this.getChildren(childMenu.getId());
			// 递归
			if (sonMenus != null && sonMenus.size() > 0) {
				for (AdminMenuResourceModel sonMenu : sonMenus) {
					this.handleChildMenu(sonMenu, roleId);
				}
				childMenu.setChildren(sonMenus);
			}
		}
		topMenu.setChildren(childMenus);
	}
	
	

	/**
	 * 根据菜单Id判断菜单是否含有子菜单
	 * 
	 * @param id
	 * @return 如果含有子菜单返回true否则返回false
	 */
	private boolean hasChildren(Integer id) {
		return adminSecurityResourceDao.getChildMenu(id, MENU_STATUS_NOTDELETE, AdminMenuResourceModel.CATEGORY) != null
				&& adminSecurityResourceDao
						.getChildMenu(id, MENU_STATUS_NOTDELETE, AdminMenuResourceModel.CATEGORY).size() > 0;
	}


}

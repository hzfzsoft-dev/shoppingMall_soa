package org.songbai.mutual.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.songbai.mutual.admin.dao.AdminAuthorityDao;
import org.songbai.mutual.admin.dao.AdminAuthorizationDao;
import org.songbai.mutual.admin.dao.AdminResourceAssignmentDao;
import org.songbai.mutual.admin.model.AdminRoleModel;
import org.songbai.mutual.admin.service.AdminRoleService;
import org.songbai.mutual.utils.mvc.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminRoleServiceImpl implements AdminRoleService {
	@Autowired
	AdminAuthorityDao adminAuthorityDao;
	@Autowired
	AdminAuthorizationDao adminAuthorizationDao;
	@Autowired
	AdminResourceAssignmentDao adminResourceAssignmentDao;

	/**
	 * 获得未授权的角色
	 */
	public Page<AdminRoleModel> pagingQueryNotGrantRoles(Integer page, Integer pageSize, Integer actorId, String name) {
	
		Integer limit = (page - 1) > 0 ? (page - 1) : 0 * pageSize;
		List<AdminRoleModel> resultList = adminAuthorityDao.pagingQueryNotGrantRolesByUserId(limit, pageSize, actorId,
				AdminRoleModel.CATEGORY, name);
		Integer count = adminAuthorityDao.pagingQueryNotGrantRolesByUserIdCount(actorId, AdminRoleModel.CATEGORY, name);
		Page<AdminRoleModel> resultPage = new Page<AdminRoleModel>(page, pageSize, count);
		resultPage.setData(resultList);
		return resultPage;
	}

	@Override
	public Page<AdminRoleModel> pagingQueryGrantRoles(Integer page, Integer pageSize, Integer actorId, String name) {
		Integer limit = (page - 1) > 0 ? ((page - 1)* pageSize) : 0;
		List<AdminRoleModel> resultList = adminAuthorityDao.pagingQueryGrantRolesByUserId(limit, pageSize, actorId,
				AdminRoleModel.CATEGORY, name);
		Integer count = adminAuthorityDao.pagingQueryGrantRolesByUserId_count(actorId, AdminRoleModel.CATEGORY, name);
		Page<AdminRoleModel> resultPage = new Page<AdminRoleModel>(page, pageSize, count);
		resultPage.setData(resultList);
		return resultPage;
	}
	
	/**
	 * 删除用户角色
	 * 
	 */
	@Override
	public void terminateAuthorizationByUserIdAuthorithIds(Integer actorId, List<Integer> authorityIdList) {
		adminAuthorizationDao.deleteAuthorizationByActorIdAuthorityIds(actorId, authorityIdList);
		
	}
	/**
	 * 角色管理-添加角色
	 */
	@Override
	public void createRole(AdminRoleModel roleModel) {
		adminAuthorityDao.createAdminRole(roleModel);
		
	}
	
	
	/**
	 * 角色管理——删除角色
	 * 在角色-用户中间表中存在的角色Id不能被删除，待确认
	 */
	@Override
	public List<AdminRoleModel> deleteRoles(List<Integer> roleList) {
		List<AdminRoleModel> roleresults = new ArrayList<AdminRoleModel>();
		//判断roleId是否在中间表中存在
		for(Integer roleId:roleList){
		
			//获得角色信息
			AdminRoleModel roleModel = adminAuthorityDao.getRole(roleId);
			roleresults.add(roleModel);
			//判断是否是管理账号，管理账号无法删除
			
			if(roleModel.getIsAdmin()==0){
				//删除中间表的关联信息
				adminAuthorizationDao.deleteAuthorizationByActorIdAuthorityId(null, roleId);
				//删除角色权限、角色页面元素资源、角色URL、角色菜单等配置数据
				adminResourceAssignmentDao.deleteAdminResourceAssignment(null,roleId);
				adminAuthorityDao.deleteAdminRole(roleId);
				//已删除的角色移出待删除表
				roleresults.remove(roleModel);
			}
		}
		return roleresults;
	}
	/**
	 * 按渠道Id查询角色
	 */
	@Override
	public Page<AdminRoleModel> pagingQueryChannelRoles(Integer page,Integer pageSize) {
		Integer limit=(page - 1) > 0 ? (page - 1) : 0 * pageSize;
		//列表
		List<AdminRoleModel> roleList=adminAuthorityDao.pagingQueryByagencyId(limit, pageSize);
		//角色数量
		Integer resultCount = adminAuthorityDao.pagingQueryByagencyIdCount();
		//返回页面
		Page<AdminRoleModel> resultPage = new Page<AdminRoleModel>(page, pageSize, resultCount);
		resultPage.setData(roleList);
		return resultPage;
	}

	@Override
	public void updateAdminRole(AdminRoleModel adminRoleModel) {
		adminAuthorityDao.updateAdminRole(adminRoleModel);
		
	}

}

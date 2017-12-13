
package org.songbai.mutual.admin.service.impl;

import java.util.List;

import org.songbai.mutual.admin.dao.AdminActorDao;
import org.songbai.mutual.admin.dao.AdminAuthorizationDao;
import org.songbai.mutual.admin.model.AdminAuthorizationModel;
import org.songbai.mutual.admin.model.AdminUserModel;
import org.songbai.mutual.admin.service.AdminUserService;
import org.songbai.mutual.utils.mvc.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminUserServiceImpl implements AdminUserService {
	@Autowired
	AdminActorDao adminActorDao;

	@Autowired
	AdminAuthorizationDao adminAuthorizationDao;

	@Override
	public AdminUserModel getUserByUserAccountPassword(String userAccount, String password) {
		if (password != null) {
			password = new AdminUserModel().handlePassword(password);
		}
		return adminActorDao.getUserByUserAccountPassword(userAccount, password);
	}

	@Override
	public void updateAdminUserExceptPassword(AdminUserModel adminUserModel) {
		adminActorDao.updateAdminUserExceptPassword(adminUserModel);

	}


	@Override
	public Page<AdminUserModel> getUserList(Integer page, Integer pageSize, String userAccount, String name,
			String email, Boolean disable, String phone) {
		// 数据库分页
		Integer limit = (page > 0 ? page * pageSize : 0);
		// 这里查询能操作后台的用户
		List<AdminUserModel> actorModels = adminActorDao.getActorsBy(limit, pageSize, userAccount, name, email,
				disable, phone, AdminUserModel.CATEGORY);
		// 查询总条数
		Integer totalCount = adminActorDao.getActorsByCount(userAccount, name, email, disable, phone,
				AdminUserModel.CATEGORY);
		// 生成页面信息
		Page<AdminUserModel> resultPage = new Page<AdminUserModel>(page, pageSize, totalCount);
		// 把数据放进page对象中
		resultPage.setData(actorModels);

		return resultPage;
	}

	@Override
	public AdminUserModel getUser(Integer actorId) {
		return adminActorDao.getAdminUser(actorId);
	}

	@Override
	public boolean hasUserAccount(String userAccount) {
		Integer count=adminActorDao.getActorsByCount(userAccount, null, null, null, null, null);
		return (count>0);
	}

	@Override
	public void createAdminUser(AdminUserModel userModel, Integer departmentId) {
		//设置密码
		userModel.setPassword(userModel.getDefaultPassword());
		if(departmentId != null){
			//保存用户和代理商关系
			adminActorDao.addDepartmentActor(departmentId, userModel.getId());
		}
		adminActorDao.createAdminUser(userModel);
	}

	@Override
	public void deleteAdminUsers(List<Integer> idList) {
		for(Integer id:idList){
			//删除与用户相关联的表信息
			adminAuthorizationDao.deleteAuthorizationByActorIdAuthorityIds(id,null);
			//删除用户
			adminActorDao.deletAdminUser(id);
		}
		
	}

	@Override
	public void changePassword(Integer actorId, String defaultPassword) {
		
		adminActorDao.updateAdminUserPassword( actorId, defaultPassword);
		
	}
	/**
	 * 授予角色
	 */

	@Override
	public void grantAuthoritysToActor(Integer actorId, List<Integer> authorityIdList) {
		
		for (Integer authorityId : authorityIdList) {
			AdminAuthorizationModel authorizationMoudel = new AdminAuthorizationModel();
			authorizationMoudel.setActorId(actorId);
			authorizationMoudel.setAuthorityId(authorityId);
			adminAuthorizationDao.createAuthorization(authorizationMoudel);
		}
		
		
	}
	
	

	@Override
	public Page<AdminUserModel> pageQueryActorByRoleId(Integer roleId,  Integer page, Integer pageSize) {
		Integer limit = page > 0 ? page * pageSize : 0 * pageSize;
		List<AdminUserModel> userList = adminActorDao.pageQueryActorByRoleId(roleId, limit, pageSize);
		Integer totalCount = adminActorDao.pageQueryActorByRoleId_count(roleId);
		Page<AdminUserModel> result = new Page<AdminUserModel>(page,pageSize, totalCount);
		result.setData(userList);
		return result;

	}

	@Override
	public void deleteUserInRole(Integer roleId, Integer actorId) {
		adminActorDao.deleteUserInRole(roleId, actorId);
		
	}

    @Override
    public AdminUserModel getUserByUserAccount(String userAccount) {
		AdminUserModel userModel=adminActorDao.getUserByUserAccount(userAccount);
        return userModel;
    }

}


package org.songbai.mutual.admin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.songbai.mutual.admin.model.AdminResourceAssignmentModel;

public interface AdminResourceAssignmentDao {

	
	/**
     * 根据数据id、资源id和角色id获得一条记录
	 * @param resourceId
	 * @param
     * @return
     */ 
	AdminResourceAssignmentModel getByRoleIdAthourIdResourceId( @Param(value="roleId")Integer roleId, @Param(value="resourceId")Integer resourceId);
	
	/**
	 * 删除某个角色的权限
	 * @param roleId
	 */
	void deleteAdminResourceAssignmentsByAuthorityId(Integer roleId);
	
	/**
	 * 添加勾选的权限
	 * @param roleId 
	 */
	List<Integer> getAdminResourceAssignmentIdsByRoleId(@Param("roleId") Integer roleId);
	/**
	 * 增加子菜单相关联的权限记录
	 * @param list
	 */
	void creatAdminResourceAssignment( List<AdminResourceAssignmentModel> list);
	/**
	 * 删除菜单相关联的权限记录
	 */
	void deleteAdminResourceAssignment(@Param(value="authorityId")Integer authorityId, @Param(value="securityResourceId")Integer securityResourceId);

	
	
}

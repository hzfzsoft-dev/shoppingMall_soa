package org.songbai.mutual.admin.service.impl;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.songbai.mutual.admin.dao.AdminResourceAssignmentDao;
import org.songbai.mutual.admin.dao.AdminSecurityResourceDao;
import org.songbai.mutual.admin.model.AdminPageElementResourceModel;
import org.songbai.mutual.admin.model.AdminUserModel;
import org.songbai.mutual.admin.service.AdminPageElementResourceService;
import org.songbai.mutual.utils.mvc.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminPageElementResourceServiceImpl implements AdminPageElementResourceService {
	
	@Autowired
	AdminSecurityResourceDao adminSecurityResourceDao;

	@Autowired
	AdminResourceAssignmentDao adminResourceAssignmentDao;

	public List<String> getPageElementByActorId(Integer actorId, AdminUserModel user) {
		List<String> pageElementResourcesAll = new ArrayList<String>();
		if ("admin".equals(user.getUserAccount())) {
			pageElementResourcesAll = adminSecurityResourceDao.getPageElementAll(AdminPageElementResourceModel.CATEGORY);
		} else {
			pageElementResourcesAll = adminSecurityResourceDao.getPageElementByActorId(actorId,
					AdminPageElementResourceModel.CATEGORY);
		}
		return pageElementResourcesAll;
	}

	public Page<AdminPageElementResourceModel> pagingQueryPageElement(Integer parentId, Integer pageSize,
			Integer page) {
		Integer limit =(page-1)>0?(page-1)*pageSize:0;
		String category=AdminPageElementResourceModel.CATEGORY;
		List<AdminPageElementResourceModel> data = adminSecurityResourceDao.pagingQueryPageElement(limit,pageSize,parentId,category);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("parentId", parentId);
		param.put("category",category);
		Integer resultCount = adminSecurityResourceDao.pagingQueryPageElement_count(param);
		Page<AdminPageElementResourceModel> pageResult = new Page<AdminPageElementResourceModel>(page, pageSize, resultCount);
		pageResult.setData(data);
		return pageResult;
	}

	public void addPageElement(AdminPageElementResourceModel pageElementModel) {
		adminSecurityResourceDao.addPageElement(pageElementModel);
		
	}

	public boolean hasPageElementByIdentifier(String identifier) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("identifier", identifier);
		param.put("category", AdminPageElementResourceModel.CATEGORY);
		return adminSecurityResourceDao.pagingQueryPageElement_count(param) > 0;
	}

	public void deletePageElement(List<Integer> idList) {
		for(Integer id:idList){
			//先删除映射
			adminResourceAssignmentDao.deleteAdminResourceAssignment(id, null);;
			//更新状态（执行软删除）
			adminSecurityResourceDao.updateStatus(id);
		}
		
	}

	public void updatePageElement(AdminPageElementResourceModel pageElementModel) {
		adminSecurityResourceDao.updatePageElement(pageElementModel);
		
	}




}

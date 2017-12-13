package org.songbai.mutual.admin.service.impl;

import java.util.List;

import org.songbai.mutual.admin.dao.AdminResourceAssignmentDao;
import org.songbai.mutual.admin.dao.AdminSecurityResourceDao;
import org.songbai.mutual.admin.model.AdminSecurityResourceModel;
import org.songbai.mutual.admin.service.AdminUrlAccessResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminUrlAccessResourceServiceImpl implements AdminUrlAccessResourceService {

	@Autowired
	AdminSecurityResourceDao adminSecurityResourceDao;
	@Autowired
	AdminResourceAssignmentDao adminResourceAssignmentDao;

	@Override
	public List<AdminSecurityResourceModel> getAllByMenuId(Integer menuId, String category) {
		return adminSecurityResourceDao.getAllByMenuId(menuId, category);
	}

	@Override
	public List<AdminSecurityResourceModel> getSecurityResourcesByActorId(Integer actorId,
			String category) {
		return adminSecurityResourceDao.getSecurityResourcesByActorId(actorId, category);
	}

}

package org.songbai.mutual.admin.service.impl;

import java.util.List;

import org.songbai.mutual.admin.dao.UpdateVersionDao;
import org.songbai.mutual.admin.model.AppVersionMongoModel;
import org.songbai.mutual.admin.service.UpdateVersionService;
import org.songbai.mutual.admin.vo.AppVersionVO;
import org.songbai.mutual.model.user.AppVersionModel;
import org.songbai.mutual.utils.mvc.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class UpdateVersionServiceImpl implements UpdateVersionService {
	private static final String LOGCOL="force_version_log";
	
	@Autowired
	UpdateVersionDao updateVersionDao;
	@Autowired
	MongoTemplate mongoTemplate;


	@Override
	public void addUpdateVersion(AppVersionModel appVersion) {
		updateVersionDao.addUpdateVersion(appVersion);
		
	}

	@Override
	public void updateForceVersion(AppVersionModel appVersion) {
		AppVersionVO oldAppVersion=updateVersionDao.findUpdateVersionById(appVersion.getId());
		updateVersionDao.updateForceVersion(appVersion);
		AppVersionVO newAppVersion=updateVersionDao.findUpdateVersionById(appVersion.getId());
		AppVersionMongoModel versionMongo=new AppVersionMongoModel();
		versionMongo.setOldVersion(oldAppVersion);
		versionMongo.setModifyName(newAppVersion.getModifyName());
		versionMongo.setModifyTime(newAppVersion.getModifyTime());
		versionMongo.setModifyUser(newAppVersion.getModifyUser());
		versionMongo.setNewVesion(newAppVersion);
		mongoTemplate.insert(versionMongo,LOGCOL);
	}

	@Override
	public Page<AppVersionVO> QueryForceVersion(AppVersionVO version, Integer page, Integer pageSize) {
		Integer limit=(page)>0?page*pageSize:0;
		List<AppVersionVO> result=updateVersionDao.findByPage(version,limit,pageSize);
		Integer count =updateVersionDao.findByPage_count(version);
		Page<AppVersionVO> pageResult=new Page<AppVersionVO>(page,pageSize,count);
		pageResult.setData(result);
		return pageResult;
	}


	
}

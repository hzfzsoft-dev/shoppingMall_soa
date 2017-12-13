package org.songbai.mutual.admin.service;


import org.songbai.mutual.admin.vo.AppVersionVO;
import org.songbai.mutual.model.user.AppVersionModel;
import org.songbai.mutual.utils.mvc.Page;

public interface UpdateVersionService {


	void addUpdateVersion(AppVersionModel appVersion);

	void updateForceVersion(AppVersionModel appVersion);

	Page<AppVersionVO> QueryForceVersion(AppVersionVO version, Integer page, Integer pageSize);

}

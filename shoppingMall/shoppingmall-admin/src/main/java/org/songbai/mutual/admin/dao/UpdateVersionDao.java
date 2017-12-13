package org.songbai.mutual.admin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.songbai.mutual.admin.vo.AppVersionVO;
import org.songbai.mutual.model.user.AppVersionModel;

public interface UpdateVersionDao {


	void addUpdateVersion(AppVersionModel appVersion);

	AppVersionVO findUpdateVersionById(Integer id);

	void updateForceVersion(AppVersionModel appVersion);

	List<AppVersionVO> findByPage(@Param("version")AppVersionVO version, @Param("limit")Integer limit,@Param("pageSize") Integer pageSize);

	Integer findByPage_count(AppVersionVO version);
	

}

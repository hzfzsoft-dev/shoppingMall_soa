package org.songbai.mutual.admin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.songbai.mutual.admin.model.AdminCertificationAuditeModel;
import org.songbai.mutual.model.user.UserModel;

/**
 * 
 * @author fangchao
 *
 * 2017年7月12日 下午1:51:01
 */
public interface AdminCertificationAuditeDao {
	
	public List<AdminCertificationAuditeModel> queryCertificationAudite(@Param("userId") Integer userId, @Param("nickName") String nickName, @Param("realName") String realName,
			@Param("phone") String phone, @Param("certCode") String certCode, @Param("status") Integer status, @Param("limit") Integer limit, @Param("pageSize") Integer pageSize);
	
	public Integer queryCertificationAuditeCount(@Param("userId") Integer userId, @Param("nickName") String nickName, @Param("realName") String realName,
			@Param("phone") String phone, @Param("certCode") String certCode, @Param("status") Integer status);
	
	public void updateCertificationAuditeBySuccess(AdminCertificationAuditeModel certificationAudite);
	public void updateCertificationAuditeByFail(AdminCertificationAuditeModel certificationAudite);
	
	public void updateUserCertification(@Param("userId") Integer userId, @Param("status") Integer status);
	
	
	public AdminCertificationAuditeModel findCertificationAudite(@Param("userId") Integer userId);
	
	public UserModel findUserById(@Param("userId") Integer userId);
	
	public Integer getCount();
	
	public Integer findUserCertificationBySuccess(@Param("userInfoId") Integer userInfoId);
	
	public Integer findUserIdByUserInfoId(@Param("userInfoId") Integer userInfoId);
}

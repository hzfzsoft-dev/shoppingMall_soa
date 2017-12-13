package org.songbai.mutual.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.songbai.mutual.admin.service.UpdateVersionService;
import org.songbai.mutual.admin.vo.AppVersionVO;
import org.songbai.mutual.model.user.AppVersionModel;
import org.songbai.mutual.utils.mvc.Page;
import org.songbai.mutual.utils.mvc.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/appVersion")

/**

 * @Description:后台版本更新

 * @author:huanglei

 * @time:2017年7月3日 下午4:41:09

 */
public class UpdateVersionController {
	@Autowired
	UpdateVersionService updateVersionService;
	
	 
	 @RequestMapping(value = "addUpdateVersion")
	 @ResponseBody
	 public Response addUpdateVersion(AppVersionModel appVersion,HttpServletRequest request){
		Assert.notNull(appVersion,"参数为空");
		Assert.notNull(appVersion.getLastVersion(),"最新版本不能为空");
		Assert.notNull(appVersion.getForceUpdateAllPreVersions(),"是否强制更新之前版本为空");
		Assert.notNull(appVersion.getUpdateAllPreVersions(),"是否更新之前版本为空");
		Integer userId = (Integer)request.getSession().getAttribute("userId");
		appVersion.setModifyUser(userId);
		updateVersionService.addUpdateVersion(appVersion);
		return Response.success();
		 
	 }
	 
	 @RequestMapping(value = "updateForceVersion")
	 @ResponseBody
	 public Response updateForceVersion(AppVersionModel appVersion,HttpServletRequest request){
		Assert.notNull(appVersion,"参数为空");
		Assert.notNull(appVersion.getId(),"缺少需要更新的id");
		Assert.notNull(appVersion.getLastVersion(),"最新版本不能为空");
		Assert.notNull(appVersion.getForceUpdateAllPreVersions(),"是否强制更新之前版本为空");
		Assert.notNull(appVersion.getUpdateAllPreVersions(),"是否更新之前版本为空");
		Integer userId = (Integer)request.getSession().getAttribute("userId");
		appVersion.setModifyUser(userId);
		updateVersionService.updateForceVersion(appVersion);
		return Response.success();
		 
	 }
	 
	 @RequestMapping(value = "queryForceVersion")
	 @ResponseBody
	 public Response QueryForceVersion(AppVersionVO version,Integer page,Integer pageSize){
		
		page=(page==null?0:page);
		pageSize=(pageSize==null?20:pageSize);
		Page<AppVersionVO>pageResult= updateVersionService.QueryForceVersion (version,page,pageSize);
		
		return Response.success(pageResult);
	 }
	 	
	
	
}

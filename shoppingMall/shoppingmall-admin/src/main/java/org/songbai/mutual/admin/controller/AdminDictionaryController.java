
package org.songbai.mutual.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.songbai.mutual.admin.model.AdminUserModel;
import org.songbai.mutual.admin.service.AdminDictionaryService;
import org.songbai.mutual.model.dictionary.AdminDictionaryModel;
import org.songbai.mutual.utils.BaseController;
import org.songbai.mutual.utils.mvc.Page;
import org.songbai.mutual.utils.mvc.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author fangchao
 *
 * 2017年7月12日 上午10:41:09
 */
@RequestMapping("/dictionary")
@Controller
public class AdminDictionaryController extends BaseController {
	@Autowired
	private AdminDictionaryService dictionaryService;
	
	/**
	 * 查询字典
	 * @param page
	 * @param pageSize
	 * @param status
	 * @param type
	 *  类型
	 * @return
	 */
	
	@RequestMapping(value="/findDictionaryByPage")
	@ResponseBody
	public Response findDictionaryByPage(Integer page,Integer pageSize,Integer status,String type){
		page = page == null ? 0 : page;
		pageSize = pageSize == null ? Page.DEFAULE_PAGESIZE : pageSize;
		Page<AdminDictionaryModel> pageResult=dictionaryService.findDictionaryByPage( page, pageSize,status,type);
		return Response.success(pageResult);

	}
	@RequestMapping(value="/saveDictionary")
	@ResponseBody
	public Response saveDictionary(String type,String name,String code,String value,String comments,HttpServletRequest request){
		//获得登录用户name
		AdminUserModel user=getLoginUser(request);
		Assert.notNull(user,"获取不到登录用户");
		String createUser=user.getName();
		
		Assert.notNull(type,"类型不能为空");
		Assert.notNull(name,"名称不能为空");
		Assert.notNull(code,"代码不能为空");
		Assert.notNull(value,"取值不能为空");
		
		//设置字典
		AdminDictionaryModel dictionaryModel=new AdminDictionaryModel();
		dictionaryModel.setType(type);
		dictionaryModel.setName(name);
		dictionaryModel.setCode(code);
		dictionaryModel.setValue(value);
		dictionaryModel.setComments(comments);
		dictionaryModel.setCreateUser(createUser);
		dictionaryModel.setUpdateUser(createUser);
		dictionaryService.addDictionary(dictionaryModel);
		return Response.success();
		
	}
	@RequestMapping(value="/updateDictionary")
	@ResponseBody
	public Response updateDictionary(Integer id,Integer status,String type,String name,String code,String value,String comments,HttpServletRequest request){
		//获得登录用户name
		AdminUserModel user=getLoginUser(request);
		Assert.notNull(user,"获取不到登录用户");
		String updateUser=user.getName();
		
		Assert.notNull(type,"类型不能为空");
		Assert.notNull(name,"名称不能为空");
		Assert.notNull(code,"代码不能为空");
		Assert.notNull(value,"取值不能为空");
		Assert.notNull(id, "id不能为空");
		
		//设置字典
		AdminDictionaryModel dictionaryModel=new AdminDictionaryModel();
		dictionaryModel.setId(id);
		dictionaryModel.setType(type);
		dictionaryModel.setName(name);
		dictionaryModel.setCode(code);
		dictionaryModel.setStatus(status);
		dictionaryModel.setValue(value);
		dictionaryModel.setComments(comments);
		dictionaryModel.setUpdateUser(updateUser);
		dictionaryService.updateDictionary(dictionaryModel);
		return Response.success();
		
	}
	

}

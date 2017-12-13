
package org.songbai.mutual.admin.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.songbai.mutual.admin.model.AdminMenuResourceModel;
import org.songbai.mutual.admin.model.AdminPageElementResourceModel;
import org.songbai.mutual.admin.model.AdminSecurityResourceModel;
import org.songbai.mutual.admin.model.AdminUserModel;
import org.songbai.mutual.admin.service.AdminRoleService;
import org.songbai.mutual.admin.service.AdminUrlAccessResourceService;
import org.songbai.mutual.admin.service.AdminUserService;
import org.songbai.mutual.admin.service.impl.AdminMenuResouceServiceImpl;
import org.songbai.mutual.utils.AdminUserUtil;
import org.songbai.mutual.utils.BaseController;
import org.songbai.mutual.utils.mvc.Response;
import org.songbai.mutual.utils.mvc.ResponseCode;
import org.songbai.mutual.utils.prop.SpringProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/login")
public class AdminLoginController extends BaseController{
	Logger logger = LoggerFactory.getLogger(AdminLoginController.class);
	@Autowired
	AdminUserService adminUserService;

	@Autowired
	AdminUrlAccessResourceService adminUrlAccessResourceService;

	@Autowired
	AdminRoleService adminRoleService;

	@Autowired
	private SpringProperties springProperties;
	
	@Autowired
	AdminMenuResouceServiceImpl adminMenuResouceService;


	/**
	 * 登录验证
	 *
	 * @param userAccount
	 * @param password
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/verification")
	@ResponseBody
	public Response verification(String userAccount, String password, HttpServletRequest request,
								 HttpServletResponse response) throws Exception {
		// v2 用户登录后台重新

		AdminUserModel user = adminUserService.getUserByUserAccount(userAccount);
		if (user == null) {
			return Response.response(ResponseCode.PARAM_ERROR, "该用户不存在");
		}
		Integer passEncryptTimes = user.getPassEncryptTimes();
		Long accountLimitTime = user.getAccountLimitTime();
		Integer times = Integer.valueOf(springProperties.getProperty("admin.user.pass_encrypt_times", "5"));
		if (passEncryptTimes >= times && accountLimitTime > System.currentTimeMillis()) {
			return Response.error(
					"该用户被禁止登录,请在" + ((accountLimitTime - System.currentTimeMillis()) / (1000 * 60) + 1) + "分钟后登录");
		}

		//密码不正确
		if (!user.getPassword().equals(new AdminUserModel().handlePassword(password))) {
			//修改密码错误次数和超时时间
			passEncryptTimes = passEncryptTimes + 1;
			user.setPassEncryptTimes(passEncryptTimes);
			if (passEncryptTimes >= times) {
				user.setAccountLimitTime(
						(long) (System.currentTimeMillis() + 1000 * 60 * (Math.pow(2, passEncryptTimes - times))));
			}
			adminUserService.updateAdminUserExceptPassword(user);
			return Response.response(ResponseCode.INNER_ERROR, "用户密码错误,当前错误" + passEncryptTimes + "次");
		}
		//用户不可用
		if (user.isDisable()) {
			return Response.response(ResponseCode.INNER_ERROR, "该用户已禁用，请联系管理员");
		}

		logger.info("用户{}登录密码{}", user.getName(), password);
		user.setAccountLimitTime(0L);
		user.setPassEncryptTimes(0);
		adminUserService.updateAdminUserExceptPassword(user);


		/**
		 * 保存用户信息到Session，以后可能会添加用户的组织机构信息
		 */
		request.getSession().setAttribute("user", user);
		request.getSession().setAttribute("userId", user.getId());

		/**
		 * 获取urlAccess
		 */
		// 获取所有的该平台 or 渠道下的 所有权限(urlAccess)
		List<AdminSecurityResourceModel> adminSecurityResourceModels_all = adminUrlAccessResourceService
				.getAllByMenuId(null, AdminPageElementResourceModel.CATEGORY);
		List<String> url11 = new ArrayList<>();
		List<String> urlAccessList_all = new ArrayList<>();
		for (AdminSecurityResourceModel model : adminSecurityResourceModels_all) {
			if (model.getUrl() != null) {
				String urlAccess[] = model.getUrl().split("\\|");
				url11.add(model.getUrl());
				for (int i = 0; i < urlAccess.length; i++) {
					urlAccessList_all.add(urlAccess[i]);
				}
			}
		}
		request.getSession().setAttribute("allUrlAccess", urlAccessList_all);

		// 获取该用户下的所有的 权限（urlAccess ）
		List<AdminSecurityResourceModel> adminSecurityResourceModels = adminUrlAccessResourceService
				.getSecurityResourcesByActorId(user.getId(),
						AdminPageElementResourceModel.CATEGORY);
		List<String> urlAccessList = new ArrayList<>();
		for (AdminSecurityResourceModel model : adminSecurityResourceModels) {
			if (model.getUrl() != null) {
				String urlAccess[] = model.getUrl().split("\\|");
				for (int i = 0; i < urlAccess.length; i++) {
					urlAccessList.add(urlAccess[i]);
				}
			}
		}
		request.getSession().setAttribute("urlAccess", urlAccessList);
		/**
		 * 获取menu
		 */
		List<AdminMenuResourceModel> menu = adminMenuResouceService.getMenuPedigreeByActorId(user);
		request.getSession().setAttribute("menu", menu);

		AdminUserUtil.login(user);

		return Response.success(user.getResourceType());
	}

	@RequestMapping(value = "/safe_userMession")
	@ResponseBody
	public Response userMession(HttpServletRequest request, HttpServletResponse response) {
		AdminUserModel user = (AdminUserModel) request.getSession().getAttribute("user");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", user.getName());
		map.put("level", user.getResourceType());
		return Response.success(map);

	}

	/**
	 * 退出系统 清除session和用户Id数据
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/safe_loginOut")
	@ResponseBody
	public Response loginOut(HttpServletRequest request, HttpServletResponse response) throws IOException {

		request.getSession().removeAttribute("user");
		request.getSession().removeAttribute("userId");
		request.getSession().removeAttribute("allUrlAccess");
		request.getSession().removeAttribute("urlAccess");
		request.getSession().removeAttribute("menu");

		AdminUserUtil.logout();
		return Response.success();
	}

	@RequestMapping(value = "/responseRedirect")
	public void responseRedirect(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getRequestDispatcher("login.html").forward(request, response);

	}

}


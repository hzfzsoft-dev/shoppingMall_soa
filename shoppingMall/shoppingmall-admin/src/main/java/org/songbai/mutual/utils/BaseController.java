package org.songbai.mutual.utils;

import javax.servlet.http.HttpServletRequest;

import org.songbai.mutual.admin.model.AdminUserModel;

public class BaseController {



	
	/**
	 * 获取当前登录的用户
	 * @param request
	 * @return
	 */

	public AdminUserModel getLoginUser(HttpServletRequest request) {
		return (AdminUserModel) request.getSession().getAttribute("user");
	}

	
}

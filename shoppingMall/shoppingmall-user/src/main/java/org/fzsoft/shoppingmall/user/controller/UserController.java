package org.fzsoft.shoppingmall.user.controller;


import org.fzsoft.shoppingmall.utils.mvc.Response;
import org.fzsoft.shoppingmall.utils.mvc.annotation.LimitLess;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
* @ClassName: UserController 
* @Description: 我的
* @author huanglei
* @date 2017年7月18日 下午2:34:16 
*
 */

@Controller
@RequestMapping("user")
public class UserController {

	@RequestMapping("index")
	@ResponseBody
	@LimitLess
	public Response myInfo(){
		//Integer userId = UserUtil.getUserId();


		return Response.success("ok");
		
	}
	

	

	
	



}

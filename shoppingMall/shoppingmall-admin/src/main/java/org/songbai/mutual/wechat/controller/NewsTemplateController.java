package org.songbai.mutual.wechat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.songbai.mutual.model.wx.LeYuTemplateModel;
import org.songbai.mutual.utils.mvc.Page;
import org.songbai.mutual.utils.mvc.Response;
import org.songbai.mutual.wechat.service.NewsTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;


/**
 * 微信推送消息管理
 * @author czh
 * 2017年8月7日2017上午9:25:59
 */
@Controller
@RequestMapping("/newsTemplate")
public class NewsTemplateController {
	private static Logger logger = LoggerFactory.getLogger(NewsTemplateController.class);
	
	@Autowired
	private NewsTemplateService newsTemplateService;
	
	@RequestMapping("/pagingQuery")
	@ResponseBody
	public Response pagingQuery(Integer page,Integer pageSize,LeYuTemplateModel model) {
		page = page == null ? 0 : page;
		pageSize = pageSize == null ? Page.DEFAULE_PAGESIZE : pageSize;
		Page<LeYuTemplateModel> result = newsTemplateService.pagingQuery(page, pageSize, model);
		return Response.success(result);
	}
	
	@RequestMapping("/addNewTemplate")
	@ResponseBody
	public Response addNewTemplate(String param) {
		logger.info("添加模板消息:{}",param);
		LeYuTemplateModel model = JSON.parseObject(param,LeYuTemplateModel.class);
		Assert.notNull(model.getType(), "消息类型不能为空");
		Assert.notNull(model.getTemplateId(), "模板ID不能为空");
		Assert.notNull(model.getTemplatName(), "模板标题不能为空");
		Assert.notNull(model.getTemplatContent(), "模板内容不能为空");
		return newsTemplateService.saveNewTemplate(model);
		
	}
	@RequestMapping("/updateNewTemplate")
	@ResponseBody
	public Response updateNewTemplate(String param) {
		logger.info("修改模板消息:{}",param);
		LeYuTemplateModel model = JSON.parseObject(param,LeYuTemplateModel.class);
		Assert.notNull(model.getType(), "消息类型不能为空");
		Assert.notNull(model.getTemplateId(), "模板ID不能为空");
		Assert.notNull(model.getTemplatName(), "模板标题不能为空");
		Assert.notNull(model.getTemplatContent(), "模板内容不能为空");
		return newsTemplateService.updateNewTemplate(model);
		
	}
	@RequestMapping("/deleteTemplate")
	@ResponseBody
	public Response deleteTemplate(Integer id) {
		logger.info("删除模板消息id:{}",id);
		Assert.notNull(id, "id不能为空");
		return newsTemplateService.deleteTemplate(id);
	}

}

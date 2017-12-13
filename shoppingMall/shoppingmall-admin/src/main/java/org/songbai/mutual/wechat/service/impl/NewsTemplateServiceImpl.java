package org.songbai.mutual.wechat.service.impl;

import java.util.List;

import org.songbai.mutual.constants.wx.WechatToken;
import org.songbai.mutual.model.wx.LeYuTemplateModel;
import org.songbai.mutual.utils.mvc.Page;
import org.songbai.mutual.utils.mvc.Response;
import org.songbai.mutual.utils.mvc.ResponseCode;
import org.songbai.mutual.wechat.dao.NewsTemplateDao;
import org.songbai.mutual.wechat.service.NewsTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
@Service
public class NewsTemplateServiceImpl implements NewsTemplateService{
	
	@Autowired
	private NewsTemplateDao newsTemplateDao;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public Page<LeYuTemplateModel> pagingQuery(Integer page, Integer pageSize, LeYuTemplateModel model) {
		Integer limit =((page-1)>0?(page-1)*pageSize:0);
		List<LeYuTemplateModel> templateList = newsTemplateDao.pagingQuery(limit, pageSize, model);
		//条目数
		Integer count=newsTemplateDao.pagingQueryBy_count(model);
		Page<LeYuTemplateModel> pageResult=new Page<LeYuTemplateModel>(limit,pageSize,count);
		pageResult.setData(templateList);
		return pageResult;
	}

	@Override
	public Response saveNewTemplate(LeYuTemplateModel model) {
		if (checkNewTemplate(model)) {
			newsTemplateDao.save(model);
			redisTemplate.opsForHash().put(WechatToken.WX_TEMPLATE, model.getType(), model);
			return Response.success();
		} 
		return Response.response(ResponseCode.INNER_ERROR, "该模板Id或消息类型已经存在");
	}

	@Override
	public Response updateNewTemplate(LeYuTemplateModel model) {
		if (existTemplate(model)) {
			newsTemplateDao.update(model);
			redisTemplate.opsForHash().put(WechatToken.WX_TEMPLATE, model.getType(), model);
			return Response.success();
		}
		return Response.response(ResponseCode.INNER_ERROR, "该模板Id或消息类型已经存在");
	}

	@Override
	public boolean checkNewTemplate(LeYuTemplateModel model) {
		LeYuTemplateModel templateModel = newsTemplateDao.checkNewTemplate(model);
		if (templateModel ==null) {
			return true;
		}
		return false;
	}
	
	public boolean existTemplate(LeYuTemplateModel model) {
		LeYuTemplateModel templateModel = newsTemplateDao.checkNewTemplate(model);
		if (templateModel == null) {
			return true;
		} 
		if (templateModel != null && templateModel.getType().equals(model.getType()) 
				&& templateModel.getTemplateId().equals(model.getTemplateId())) {
			return true;
		}
		return false;
	}
	
	@Override
	public Response deleteTemplate(Integer id) {
		newsTemplateDao.delete(id);
		return Response.success();
	}


}

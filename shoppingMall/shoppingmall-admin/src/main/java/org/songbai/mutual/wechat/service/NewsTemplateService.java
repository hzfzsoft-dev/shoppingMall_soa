package org.songbai.mutual.wechat.service;

import org.songbai.mutual.model.wx.LeYuTemplateModel;
import org.songbai.mutual.utils.mvc.Page;
import org.songbai.mutual.utils.mvc.Response;

/**
 * 微信推送消息管理
 * @author czh
 * 2017年8月7日2017上午9:28:38
 */
public interface NewsTemplateService {
	/**
	 * 查询列表
	 * @param userId
	 * @param page
	 * @param pageSize
	 * @return
	 */
	Page<LeYuTemplateModel> pagingQuery(Integer page,Integer pageSize, LeYuTemplateModel model);
	/**
	 * 添加模板消息
	 * @param model
	 * @return
	 */
	Response saveNewTemplate(LeYuTemplateModel model);
	/**
	 * 修改模板消息
	 * @param model
	 * @return
	 */
	Response updateNewTemplate(LeYuTemplateModel model);
	
	/**
	 * 校验模板消息不能重复
	 */
	boolean checkNewTemplate(LeYuTemplateModel model);
	
	/**
	 * 删除
	 */
	Response deleteTemplate(Integer id);
}

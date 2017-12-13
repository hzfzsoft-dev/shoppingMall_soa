package org.songbai.mutual.wechat.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.songbai.mutual.model.wx.LeYuTemplateModel;

public interface NewsTemplateDao {
	
	List<LeYuTemplateModel> pagingQuery(@Param(value="limit")Integer limit ,@Param(value="pageSize")Integer pageSize, @Param("model")LeYuTemplateModel model);
	
	Integer pagingQueryBy_count(LeYuTemplateModel model);
	
	void save(LeYuTemplateModel model);
	
	void update(LeYuTemplateModel model);
	
	public LeYuTemplateModel checkNewTemplate(LeYuTemplateModel model);
	
	void delete(Integer id);
}

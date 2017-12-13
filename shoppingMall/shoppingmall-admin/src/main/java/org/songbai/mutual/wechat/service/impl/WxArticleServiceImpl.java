package org.songbai.mutual.wechat.service.impl;

import org.songbai.mutual.model.wx.WxArticleModel;
import org.songbai.mutual.utils.mvc.Page;
import org.songbai.mutual.utils.mvc.Response;
import org.songbai.mutual.wechat.dao.WxArticleDao;
import org.songbai.mutual.wechat.service.WxArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WxArticleServiceImpl implements WxArticleService {

    @Autowired
    WxArticleDao wxArticleDao;

    @Override
    public Page<WxArticleModel> wxArticleList(Integer page, Integer pageSize, WxArticleModel model) {
        Integer limit = ((page - 1) > 0 ? (page - 1) * pageSize : 0);
        List<WxArticleModel> templateList = wxArticleDao.wxArticleList(limit, pageSize, model);
        //条目数
        Integer count = wxArticleDao.wxArticleBy_count(model);
        Page<WxArticleModel> pageResult = new Page<WxArticleModel>(limit, pageSize, count);
        pageResult.setData(templateList);
        return pageResult;
    }

    @Override
    public Response addWxArticle(WxArticleModel model) {
        model.setCreate_time(new Date());
        wxArticleDao.save(model);
        return Response.success();

    }

    @Override
    public WxArticleModel findById(Integer id) {
        return wxArticleDao.findById(id);
    }

    @Override
    public Response updateWxArticle(WxArticleModel model) {
        wxArticleDao.updateWxArticle(model);
        return Response.success();

    }


    @Override
    public Response deleteWxArticle(Integer id) {
        wxArticleDao.deleteWxArticle(id);
        return Response.success();
    }


}

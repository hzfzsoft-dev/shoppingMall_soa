package org.songbai.mutual.wechat.service.impl;

import org.songbai.mutual.model.wx.WechatMenuModel;
import org.songbai.mutual.utils.mvc.Page;
import org.songbai.mutual.utils.mvc.Response;
import org.songbai.mutual.wechat.dao.WeChatMenuDao;
import org.songbai.mutual.wechat.service.WeChatMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WeChatMenuServiceImpl implements WeChatMenuService {

    @Autowired
    WeChatMenuDao menuDao;

    @Override
    public Page<WechatMenuModel> pagingQuery(Integer page, Integer pageSize, WechatMenuModel model) {
        Integer limit = ((page - 1) > 0 ? (page - 1) * pageSize : 0);
        List<WechatMenuModel> templateList = menuDao.pagingQuery(limit, pageSize, model);
        //条目数
        Integer count = menuDao.pagingQueryBy_count(model);
        Page<WechatMenuModel> pageResult = new Page<WechatMenuModel>(limit, pageSize, count);
        pageResult.setData(templateList);
        return pageResult;
    }

    @Override
    public Response addWeChatMenu(WechatMenuModel model) {
        model.setCreateTime(new Date());
        menuDao.save(model);
        return Response.success();
    }

    @Override
    public List<WechatMenuModel> find() {
        List<WechatMenuModel> list = menuDao.find();
        return list;
    }

    @Override
    public List<WechatMenuModel> findByParentId(Integer id) {
        List<WechatMenuModel> list = menuDao.findByParentId(id);
        return list;
    }


    @Override
    public Response updateWeChatMenu(WechatMenuModel model) {
        menuDao.updateWeChatMenu(model);
        return Response.success();

    }


    @Override
    public WechatMenuModel findById(Integer id) {
        return menuDao.findById(id);
    }

    @Override
    public Response deleteWeChatMenu(Integer id) {
        menuDao.deleteWeChatMenu(id);
        return Response.success();
    }


}

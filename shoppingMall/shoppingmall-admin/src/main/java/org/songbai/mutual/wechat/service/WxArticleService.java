package org.songbai.mutual.wechat.service;


import org.songbai.mutual.model.wx.WxArticleModel;
import org.songbai.mutual.utils.mvc.Page;
import org.songbai.mutual.utils.mvc.Response;


public interface WxArticleService {
    /**
     * 显示菜单按钮
     *
     * @param page
     * @param pageSize
     * @param model
     * @return
     */
    Page<WxArticleModel> wxArticleList(Integer page, Integer pageSize, WxArticleModel model);

    /**
     * 新增菜单
     *
     * @param model
     * @return
     */
    Response addWxArticle(WxArticleModel model);

    /**
     * 查询id 的数据
     *
     * @param id
     * @return
     */
    public WxArticleModel findById(Integer id);

    /**
     * 更新
     *
     * @param model
     * @return
     */
    Response updateWxArticle(WxArticleModel model);


    /**
     * 删除
     */
    Response deleteWxArticle(Integer id);
}

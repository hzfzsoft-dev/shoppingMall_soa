package org.songbai.mutual.wechat.service;

import org.songbai.mutual.model.wx.WechatMenuModel;
import org.songbai.mutual.utils.mvc.Page;
import org.songbai.mutual.utils.mvc.Response;

import java.util.List;

public interface WeChatMenuService {

    /**
     * 显示菜单按钮
     *
     * @param page
     * @param pageSize
     * @param model
     * @return
     */
    Page<WechatMenuModel> pagingQuery(Integer page, Integer pageSize, WechatMenuModel model);

    /**
     * 新增菜单
     *
     * @param model
     * @return
     */
    Response addWeChatMenu(WechatMenuModel model);

    /**
     * 查询parent_id 为空的数据（主菜单）
     *
     * @return
     */
    public List<WechatMenuModel> find();

    /**
     * 查询parent_id 为空的数据（主菜单）
     *
     * @return
     */
    public List<WechatMenuModel> findByParentId(Integer id);

    /**
     * 查询id 的数据
     *
     * @param id
     * @return
     */
    public WechatMenuModel findById(Integer id);

    /**
     * 更新
     *
     * @param model
     * @return
     */
    Response updateWeChatMenu(WechatMenuModel model);


    /**
     * 删除
     */
    Response deleteWeChatMenu(Integer id);
}

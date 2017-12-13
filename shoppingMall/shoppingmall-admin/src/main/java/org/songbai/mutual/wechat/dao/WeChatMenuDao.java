package org.songbai.mutual.wechat.dao;

import org.apache.ibatis.annotations.Param;
import org.songbai.mutual.model.wx.WechatMenuModel;

import java.util.List;

public interface WeChatMenuDao {
    List<WechatMenuModel> pagingQuery(@Param(value="limit")Integer limit , @Param(value="pageSize")Integer pageSize, @Param("model")WechatMenuModel model);

    Integer pagingQueryBy_count(WechatMenuModel model);

    List<WechatMenuModel> find();

    List<WechatMenuModel> findByParentId(Integer id);

    void save(WechatMenuModel model);

    public WechatMenuModel findById(Integer id);

    void updateWeChatMenu(WechatMenuModel model);

//    WechatMenuModel findById(Integer id);

    void deleteWeChatMenu(Integer id);

}

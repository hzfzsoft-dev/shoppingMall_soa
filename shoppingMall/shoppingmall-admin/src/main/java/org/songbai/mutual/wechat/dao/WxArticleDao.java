package org.songbai.mutual.wechat.dao;

import org.apache.ibatis.annotations.Param;
import org.songbai.mutual.model.wx.WxArticleModel;

import java.util.List;


public interface WxArticleDao {
    List<WxArticleModel> wxArticleList(@Param(value="limit")Integer limit , @Param(value="pageSize")Integer pageSize, @Param("model")WxArticleModel model);

    Integer wxArticleBy_count(WxArticleModel model);


    void save(WxArticleModel model);

    void updateWxArticle(WxArticleModel model);

    WxArticleModel findById(Integer id);

    void deleteWxArticle(Integer id);
}

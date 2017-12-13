package org.songbai.mutual.wechat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.songbai.mutual.model.wx.WxArticleModel;
import org.songbai.mutual.utils.mvc.Page;
import org.songbai.mutual.utils.mvc.Response;
import org.songbai.mutual.wechat.service.WxArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 后台维护用户图文推送信息
 */
@Controller
@RequestMapping("/wxArticle")
public class WxArticleController {
    private static Logger logger = LoggerFactory.getLogger(NewsTemplateController.class);

    @Autowired
    WxArticleService wxArticleService;


    @RequestMapping("/wxArticleList")
    @ResponseBody
    public Response wxArticleList(Integer page, Integer pageSize, WxArticleModel model) {
        page = page == null ? 0 : page;
        pageSize = pageSize == null ? Page.DEFAULE_PAGESIZE : pageSize;
        Page<WxArticleModel> result = wxArticleService.wxArticleList(page, pageSize, model);
        logger.info("Return the data:{} ",result);
        return Response.success(result);
    }

    @RequestMapping("/addWxArticle")
    @ResponseBody
    public Response addWxArticle(WxArticleModel model) {

        Assert.notNull(model.getId(), "id不能为空");
        Assert.notNull(model.getType(), "类型不能为空");
        Assert.notNull(model.getTitle(), "标题不能为空");
        Assert.notNull(model.getPic_url(), "pic_url不能为空");
        Assert.notNull(model.getUrl(), "url不能为空");
        Assert.notNull(model.getSort(), "排序号不能为空");

        WxArticleModel oldArticle = wxArticleService.findById(model.getId());
        if(oldArticle!=null){
            return Response.errorResponse("已存在重复的id信息");
        }
        return wxArticleService.addWxArticle(model);
    }

    @RequestMapping("/updateWxArticle")
    @ResponseBody
    public Response updateWxArticle(WxArticleModel model) {
        Assert.notNull(model.getId(), "id不能为空");
        Assert.notNull(model.getType(), "类型不能为空");
        Assert.notNull(model.getTitle(), "标题不能为空");
        Assert.notNull(model.getPic_url(), "pic_url不能为空");
        Assert.notNull(model.getUrl(), "url不能为空");
        Assert.notNull(model.getSort(), "排序号不能为空");

        WxArticleModel oldArticle = wxArticleService.findById(model.getId());
        if(oldArticle==null){
            return Response.errorResponse("无法获取需要修改的信息");
        }
        return wxArticleService.updateWxArticle(model);
    }

    @RequestMapping("/deleteWxArticle")
    @ResponseBody
    public Response deleteWxArticle(Integer id) {
        Assert.notNull(id, "id不能为空");
        return wxArticleService.deleteWxArticle(id);
    }

}


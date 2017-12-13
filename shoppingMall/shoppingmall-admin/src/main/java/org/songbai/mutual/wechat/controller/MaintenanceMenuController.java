package org.songbai.mutual.wechat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.songbai.mutual.model.wx.WechatMenuModel;
import org.songbai.mutual.utils.mvc.Page;
import org.songbai.mutual.utils.mvc.Response;
import org.songbai.mutual.wechat.service.WeChatMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 后台维护微信公众号底部菜单
 */
@Controller
@RequestMapping("/maintMenu")
public class MaintenanceMenuController {
    private static Logger logger = LoggerFactory.getLogger(NewsTemplateController.class);

    @Autowired
    WeChatMenuService menuService;

    @RequestMapping("/pagingQuery")
    @ResponseBody
    public Response pagingQuery(Integer page, Integer pageSize, WechatMenuModel model) {
        page = page == null ? 0 : page;
        pageSize = pageSize == null ? Page.DEFAULE_PAGESIZE : pageSize;
        Page<WechatMenuModel> result = menuService.pagingQuery(page, pageSize, model);
        logger.info("Return the data:{} ",result);
        return Response.success(result);
    }

    @RequestMapping("/addWeChatMenu")
    @ResponseBody
    public Response addWeChatMenu(WechatMenuModel model) {
        logger.info("type：{}",model.getType());
        Assert.notNull(model.getId(), "菜单id不能为空");
        Assert.notNull(model.getName(), "菜单名称不能为空");
        Assert.notNull(model.getType(),"菜单类型不能为空");
        Assert.notNull(model.getMenuSort(),"菜单的排序不能为空");
        if("view".equals(model.getType())){
            Assert.notNull(model.getUrl(),"菜单url不能为空");
        }
        if("click".equals(model.getType())){
            Assert.notNull(model.getKey_click(),"key值不能为空");
        }
        //判断主菜单不能超过三个
        List<WechatMenuModel> mainList = menuService.find();
        if(mainList.size()>3){
            return Response.errorResponse("主菜单不能超过三个");
        }
        //判断子菜单不能超过5个
        List<WechatMenuModel> sublist = menuService.findByParentId(model.getId());
        if(sublist.size()>5){
            return Response.errorResponse("子菜单不能超过五个");
        }
        WechatMenuModel oldMenu = menuService.findById(model.getId());
        if(oldMenu!=null){
            return Response.errorResponse("已存在重复的菜单id");
        }
        return menuService.addWeChatMenu(model);
    }

    @RequestMapping("/updateWeChatMenu")
    @ResponseBody
    public Response updateWeChatMenu(WechatMenuModel model) {
        Assert.notNull(model.getId(), "菜单id不能为空");
        Assert.notNull(model.getName(), "菜单名称不能为空");
        Assert.notNull(model.getType(),"菜单类型不能为空");
        Assert.notNull(model.getMenuSort(),"菜单的排序不能为空");
        if("view".equals(model.getType())){
            Assert.notNull(model.getUrl(),"菜单url不能为空");
        }
        if("click".equals(model.getType())){
            Assert.notNull(model.getKey_click(),"key值不能为空");
        }
        WechatMenuModel oldMenu = menuService.findById(model.getId());
        if(oldMenu==null){
            return Response.errorResponse("无法获取需要修改的信息");
        }

        return menuService.updateWeChatMenu(model);
    }


    @RequestMapping("/deleteWeChatMenu")
    @ResponseBody
    public Response deleteWeChatMenu(Integer id) {
        Assert.notNull(id, "id不能为空");
        return menuService.deleteWeChatMenu(id);
    }

}

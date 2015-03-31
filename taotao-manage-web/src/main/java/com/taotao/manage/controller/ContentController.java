package com.taotao.manage.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.taotao.common.vo.EasyUIResult;
import com.taotao.common.vo.TaotaoResult;
import com.taotao.manage.pojo.Content;
import com.taotao.manage.service.ContentService;

@RequestMapping(value = "/content/")
@Controller
public class ContentController {
    
    @Autowired
    private ContentService contentService;

    /**
     * 根据内容分类id分页查询内容
     * 
     * @param categoryId
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/query/list")
    @ResponseBody
    public EasyUIResult queryPageList(@RequestParam("categoryId")Long categoryId,@RequestParam("page")Integer page, @RequestParam("rows")Integer rows) {
        Content content = new Content();
        content.setCategoryId(categoryId);
        PageInfo<Content> pageInfo = contentService.queryPageListOrderByUpdated(page, rows, content);
        return new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
    }
    
    /**
     * 新增内容
     * 
     * @param content
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public TaotaoResult save(Content content){
        Date date = new Date();
        content.setCreated(date);
        content.setUpdated(date);
        content.setId(null);//强制改为null
        this.contentService.save(content);
        return TaotaoResult.ok();
    }
    
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult edit(Content content) {
            content.setUpdated(new Date());
            this.contentService.updateSelective(content);
            return TaotaoResult.ok();
    }

}

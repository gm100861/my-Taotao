package com.taotao.manage.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.vo.TaotaoResult;
import com.taotao.manage.pojo.ContentCategory;
import com.taotao.manage.service.ContentCategoryService;

@RequestMapping(value = "/content/category/")
@Controller
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public List<ContentCategory> list(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
        return contentCategoryService.queryListByParentId(parentId);
    }

    /**
     * 新增分类
     * 
     * @param parentId
     * @param name
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult create(@RequestParam("parentId") Long parentId, @RequestParam("name") String name) {
        Date date = new Date();
        // 创建新的分类
        ContentCategory contentCategory = new ContentCategory(null, parentId, name, 1, 1, false, date, date);
        this.contentCategoryService.save(contentCategory);

        // 检测 parentId所对应的分类是否是父节点，如果不是，改为父节点
        ContentCategory parent = this.contentCategoryService.queryById(parentId);
        if (!parent.getIsParent()) {
            // 更新
            parent.setIsParent(true);
            parent.setUpdated(new Date());
            this.contentCategoryService.update(parent);
        }

        return TaotaoResult.ok(contentCategory);
    }

    /**
     * 节点重命名
     * 
     * @param id
     * @param name
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult update(@RequestParam("id") Long id, @RequestParam("name") String name) {
        ContentCategory contentCategory = new ContentCategory();
        contentCategory.setId(id);
        contentCategory.setName(name);
        this.contentCategoryService.updateSelective(contentCategory);
        return TaotaoResult.ok();
    }

    /**
     * 删除节点
     *  1、删除所有的子节点
     *  2、判断父节点是否还有子节点，如果没有，修改isParent为false
     * @param parentId
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult delete(@RequestParam("parentId") Long parentId, @RequestParam("id") Long id) {
        // 删除当前节点
        this.contentCategoryService.delete(id);
        
        // 删除所有子节点
        //待删除
        List<Long> deleteIds = new ArrayList<Long>();
        queryCategoryByParentId(id, deleteIds);
        if(!deleteIds.isEmpty()){
            this.contentCategoryService.delete(deleteIds.toArray(new Long[]{}));
        }

        // 检测 parentId所对应的分类是否是父节点，如果不是，改为子节点
        ContentCategory param = new ContentCategory();
        param.setParentId(parentId);
        Integer count = this.contentCategoryService.queryCount(param);
        if(count == 0){
            //修改isParent为false
            ContentCategory parent = new ContentCategory();
            parent.setId(parentId);
            parent.setIsParent(false);
            parent.setUpdated(new Date());
            this.contentCategoryService.updateSelective(parent);
        }
        
        return TaotaoResult.ok();

    }
    
    /**
     * 递归查询
     * 
     * @param parentId
     * @param deleteIds
     */
    private void queryCategoryByParentId(Long parentId, List<Long> deleteIds){
        ContentCategory param = new ContentCategory();
        param.setParentId(parentId);
        List<ContentCategory> categories = this.contentCategoryService.queryList(param);
        for (ContentCategory contentCategory : categories) {
            deleteIds.add(contentCategory.getId());
            //判断是否为父节点
            if(contentCategory.getIsParent()){
                queryCategoryByParentId(contentCategory.getId(), deleteIds);
            }
        }
    }
    
}

package com.taotao.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.manage.base.mapper.TaotaoMapper;
import com.taotao.manage.mapper.ContentCategoryMapper;
import com.taotao.manage.pojo.ContentCategory;

/**
 * 内容分类业务处理
 * 
 */
@Service
public class ContentCategoryService extends BaseService<ContentCategory>{
    
    @Autowired
    private ContentCategoryMapper contentCategoryMapper;

    @Override
    public TaotaoMapper<ContentCategory> getMapper() {
        return contentCategoryMapper;
    }

    /**
     * 根据parentId查询分类信息
     * 
     * @param parentId
     * @return
     */
    public List<ContentCategory> queryListByParentId(Long parentId) {
        //设置查询条件
        ContentCategory contentCategory = new ContentCategory();
        contentCategory.setParentId(parentId);
        return this.contentCategoryMapper.select(contentCategory);
    }

    /**
     * 根据条件查询数量
     * 
     * @param param
     * @return
     */
    public Integer queryCount(ContentCategory param) {
        return this.contentCategoryMapper.selectCount(param);
    }

}

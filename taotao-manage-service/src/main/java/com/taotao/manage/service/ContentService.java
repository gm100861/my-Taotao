package com.taotao.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.manage.base.mapper.TaotaoMapper;
import com.taotao.manage.mapper.ContentMapper;
import com.taotao.manage.pojo.Content;

/**
 * 
 * 内容相关业务
 *
 */
@Service
public class ContentService extends BaseService<Content>{
    
    @Autowired
    private ContentMapper contentMapper;

    @Override
    public TaotaoMapper<Content> getMapper() {
        return contentMapper;
    }

    public PageInfo<Content> queryPageListOrderByUpdated(Integer page, Integer pageSize, Content content) {
        // 分页条件
        PageHelper.startPage(page, pageSize, true);
        List<Content> list = this.contentMapper.queryListOrderByUpdated(content);
        return new PageInfo<Content>(list);
    }

}

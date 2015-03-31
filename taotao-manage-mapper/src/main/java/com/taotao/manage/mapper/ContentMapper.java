package com.taotao.manage.mapper;

import java.util.List;

import com.taotao.manage.base.mapper.TaotaoMapper;
import com.taotao.manage.pojo.Content;

/**
 * 内容
 */
public interface ContentMapper extends TaotaoMapper<Content> {

    /**
     * 根据内容id查询内容列表，通过updated实现倒序查询
     * 
     * @param content
     * @return
     */
    List<Content> queryListOrderByUpdated(Content content);

}

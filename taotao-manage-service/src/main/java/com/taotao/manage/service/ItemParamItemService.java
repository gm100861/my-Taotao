package com.taotao.manage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.manage.base.mapper.TaotaoMapper;
import com.taotao.manage.mapper.ItemParamItemMapper;
import com.taotao.manage.pojo.ItemParamItem;

/**
 * 生成的规格参数数据相关业务逻辑
 */
@Service
public class ItemParamItemService extends BaseService<ItemParamItem> {
    
    @Autowired
    private ItemParamItemMapper itemParamItemMapper;

    @Override
    public TaotaoMapper<ItemParamItem> getMapper() {
        return itemParamItemMapper;
    }

}

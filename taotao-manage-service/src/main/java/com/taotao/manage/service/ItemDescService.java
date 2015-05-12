package com.taotao.manage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.manage.base.mapper.TaotaoMapper;
import com.taotao.manage.mapper.ItemDescMapper;
import com.taotao.manage.pojo.ItemDesc;

/**
 * 处理商品描述相关的业务逻辑
 * 
 */
@Service
public class ItemDescService extends BaseService<ItemDesc>{
    
    @Autowired
    private ItemDescMapper itemDescMapper;

    @Override
    public TaotaoMapper<ItemDesc> getMapper() {
        return itemDescMapper;
    }

    public void updateItemDesc(ItemDesc itemDesc) {
        this.itemDescMapper.updateItemDescByItemId(itemDesc);
    }

}

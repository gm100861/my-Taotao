package com.taotao.manage.mapper;

import com.taotao.manage.base.mapper.TaotaoMapper;
import com.taotao.manage.pojo.ItemDesc;

public interface ItemDescMapper extends TaotaoMapper<ItemDesc>{

    /**
     * 根据商品id更新描述数据
     * 
     * @param itemDesc
     */
    void updateItemDescByItemId(ItemDesc itemDesc);

}

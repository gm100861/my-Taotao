package com.taotao.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.vo.TaotaoResult;
import com.taotao.manage.pojo.ItemParamItem;
import com.taotao.manage.service.ItemParamItemService;

@RequestMapping(value = "/item/param/item")
@Controller
public class ItemParamItemController {
    
    @Autowired
    private ItemParamItemService itemParamItemService;
    
    /**
     * 根据商品ID查询规则参数数据
     * 
     * @param itemId
     * @return
     */
    @RequestMapping(value = "/query/{itemId}", method = RequestMethod.GET)
    @ResponseBody
    public TaotaoResult query(@PathVariable("itemId")Long itemId){
        ItemParamItem itemParamItem = new ItemParamItem();
        itemParamItem.setItemId(itemId);
        itemParamItem = itemParamItemService.queryByWhere(itemParamItem);
        return TaotaoResult.ok(itemParamItem);
    }

}

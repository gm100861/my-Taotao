package com.taotao.manage.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.vo.web.ItemCatResult;
import com.taotao.manage.service.ItemCatService;

@RequestMapping(value = "/web/itemcat")
@Controller
public class WebItemCatController {
    
    @Autowired
    private ItemCatService itemCatService;
    
    /**
     * 提供对外的接口服务，查询所有商品类目，并且生成ItemCatResult结构
     * 
     * @return
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public ItemCatResult queryAll(){
        return itemCatService.queryAllToWeb();
    }

}

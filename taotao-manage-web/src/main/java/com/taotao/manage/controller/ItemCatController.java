package com.taotao.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.service.ItemCatService;

@RequestMapping(value = "/item/cat")
@Controller
public class ItemCatController {
    
    @Autowired
    private ItemCatService itemCatService;
    
    /**
     * 根据parentId查询类目信息
     * 
     * @param parentId
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public List<ItemCat> list(@RequestParam(value = "id", defaultValue = "0")Long parentId){
        return itemCatService.queryListByParentId(parentId);
    }

}

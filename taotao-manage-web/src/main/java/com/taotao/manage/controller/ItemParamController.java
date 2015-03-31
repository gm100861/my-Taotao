package com.taotao.manage.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.taotao.common.vo.EasyUIResult;
import com.taotao.common.vo.TaotaoResult;
import com.taotao.manage.pojo.ItemParam;
import com.taotao.manage.service.ItemParamService;

@RequestMapping(value = "/item/param")
@Controller
public class ItemParamController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemParamController.class);
    
    @Autowired
    private ItemParamService itemParamService;
    
    /**
     * 通类目id查询模板
     * 
     * @param itemCatId
     * @return
     */
    @RequestMapping(value = "/query/itemcatid/{itemCatId}", method = RequestMethod.GET)
    @ResponseBody
    public TaotaoResult queryByItemCatId(@PathVariable("itemCatId")Long itemCatId){
        ItemParam itemParam = new ItemParam();
        itemParam.setItemCatId(itemCatId);
        itemParam = this.itemParamService.queryByWhere(itemParam);
        return TaotaoResult.ok(itemParam);
    }
    
    /**
     * 保存模板数据
     * 
     * @param paramData
     * @param itemCatId
     * @return
     */
    @RequestMapping(value = "/save/{itemCatId}",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult save(@RequestParam("paramData")String paramData,@PathVariable("itemCatId")Long itemCatId){
        Date date = new Date();
        this.itemParamService.save(new ItemParam(null, itemCatId, paramData, date, date));
        return TaotaoResult.ok();
    }
    
    /**
     * 分页查询模板数据
     * 
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public EasyUIResult queryList(@RequestParam("page") Integer page, @RequestParam("rows") Integer rows) {
        if (LOGGER.isDebugEnabled()) {
            // 输出，输入参数
            LOGGER.debug("查询商品列表 , page = {}, rows = {}", page, rows);
        }
        PageInfo<ItemParam> pageInfo = this.itemParamService.queryPageList(page, rows);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("查询结果 , total = {}, pages = {}", pageInfo.getTotal(), pageInfo.getPages());
        }
        return new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
    }
    

}

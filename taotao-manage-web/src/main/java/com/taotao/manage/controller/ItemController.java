package com.taotao.manage.controller;

import java.util.Date;
import java.util.List;

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
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.pojo.ItemParamItem;
import com.taotao.manage.service.ItemDescService;
import com.taotao.manage.service.ItemParamItemService;
import com.taotao.manage.service.ItemService;

@RequestMapping(value = "/item")
@Controller
public class ItemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemDescService itemDescService;

    @Autowired
    private ItemParamItemService itemParamItemService;

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public EasyUIResult queryList(@RequestParam("page") Integer page, @RequestParam("rows") Integer rows) {
        if (LOGGER.isDebugEnabled()) {
            // 输出，输入参数
            LOGGER.debug("查询商品列表 , page = {}, rows = {}", page, rows);
        }
        PageInfo<Item> pageInfo = this.itemService.queryPageList(page, rows);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("查询结果 , total = {}, pages = {}", pageInfo.getTotal(), pageInfo.getPages());
        }
        return new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 新增商品
     * 
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult save(Item item, @RequestParam("desc") String desc,
            @RequestParam("itemParams") String itemParams) {
        Date date = new Date();
        // 将商品对象保存到数据库中
        this.itemService.saveItem(item);
        // 保存的对象中获取id
        Long itemId = item.getId();
        ItemDesc itemDesc = new ItemDesc(itemId, desc, date, date);
        // 保存商品描述数据
        itemDescService.save(itemDesc);

        // 保存规则参数数据
        itemParamItemService.save(new ItemParamItem(null, itemId, itemParams, date, date));

        return TaotaoResult.ok(item.getId());
    }

    /**
     * 查询商品描述信息
     * 
     * @param itemId 商品ID
     * @return
     */
    @RequestMapping(value = "/query/desc/{itemId}")
    @ResponseBody
    public TaotaoResult queryItemDesc(@PathVariable("itemId") Long itemId) {
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(itemId);
        ItemDesc desc = this.itemDescService.queryByWhere(itemDesc);
        return TaotaoResult.ok(desc);
    }

    /**
     * 查询商品描述信息
     * 
     * @param itemId 商品ID
     * @return
     */
    @RequestMapping(value = "/query/{id}")
    @ResponseBody
    public TaotaoResult queryItemById(@PathVariable("id") Long id) {
        Item item = null;
        try {
            item = this.itemService.queryById(id);
            if (item != null) {
                return TaotaoResult.ok(item);
            }
            return TaotaoResult.build(202, "查询的商品不存在!");
        } catch (Exception e) {
            LOGGER.error("根据ID查询商品失败！", e);
        }
        return TaotaoResult.build(201, "查询失败!", null);
    }

    /**
     * 
     * @param item
     * @param desc
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult update(Item item, @RequestParam("desc") String desc,
            @RequestParam("itemParams") String itemParams, @RequestParam("itemParamId") Long itemParamId) {
        // 更新数据
        // 不能修改的字段：ID、created、status
        this.itemService.updateItem(item);

        ItemDesc itemDesc = new ItemDesc(item.getId(), desc, null, new Date());
        // 更新描述
        this.itemDescService.updateItemDesc(itemDesc);

        // 保存规则参数数据
        itemParamItemService.updateSelective(new ItemParamItem(itemParamId, item.getId(), itemParams, null,
                new Date()));
        return TaotaoResult.ok();
    }
    /**
     * 更具类目查询商品（前10个）
     * @param cid
     * @return
     */
    
    @RequestMapping(value = "/query/ids/{strids}")
    @ResponseBody
    public TaotaoResult queryItemByCid(@PathVariable("strids") String strids) {
        List<Item> items = null;
        try {
            String[] ids = strids.split(",");
            items = this.itemService.queryListItemByIds(ids);
            if (items != null) {
                return TaotaoResult.ok(items);
            }
            return TaotaoResult.build(202, "查询的商品不存在!");
        } catch (Exception e) {
            LOGGER.error("根据ID查询商品失败！", e);
        }       
        return TaotaoResult.build(201, "查询失败!", null);
    }
}

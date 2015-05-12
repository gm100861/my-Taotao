package com.taotao.manage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.service.RedisService;
import com.taotao.common.vo.web.ItemCatData;
import com.taotao.common.vo.web.ItemCatResult;
import com.taotao.manage.base.mapper.TaotaoMapper;
import com.taotao.manage.mapper.ItemCatMapper;
import com.taotao.manage.pojo.ItemCat;

@Service
public class ItemCatService extends BaseService<ItemCat> {

    @Autowired
    private ItemCatMapper itemCatMapper;
    
    @Autowired
    private RedisService redisService;
    
    private static final String TAOTAO_MANAGE_ITEM_CAT_ALL = "TAOTAO_MANAGE_ITEM_CAT_ALL";
    
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public TaotaoMapper<ItemCat> getMapper() {
        return itemCatMapper;
    }

    /**
     * 根据parentId查询商品类目
     * 
     * @param parentId
     * @return
     */
    public List<ItemCat> queryListByParentId(Long parentId) {
        // 构造，根据parentID查询的条件
        ItemCat itemCat = new ItemCat();
        itemCat.setParentId(parentId);
        return this.itemCatMapper.select(itemCat);
    }

    public ItemCatResult queryAllToWeb() {
        //添加缓存逻辑
        String data = this.redisService.get(TAOTAO_MANAGE_ITEM_CAT_ALL);
        if(StringUtils.isNotBlank(data)){
            // json转为对象
            try {
                return MAPPER.readValue(data, ItemCatResult.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        ItemCatResult result = new ItemCatResult();
        // 全部查出，并且在内存中生成树形结构
        List<ItemCat> cats = queryAll();

        // 转为map存储，key为父节点ID，value为数据集合
        Map<Long, List<ItemCat>> itemCatMap = new HashMap<Long, List<ItemCat>>();
        for (ItemCat itemCat : cats) {
            if (!itemCatMap.containsKey(itemCat.getParentId())) {
                itemCatMap.put(itemCat.getParentId(), new ArrayList<ItemCat>());
            }
            itemCatMap.get(itemCat.getParentId()).add(itemCat);
        }

        // 封装一级对象
        List<ItemCat> itemCatList1 = itemCatMap.get(0L);
        for (ItemCat itemCat : itemCatList1) {
            ItemCatData itemCatData = new ItemCatData();
            itemCatData.setUrl("/products/" + itemCat.getId() + ".html");
            itemCatData.setName("<a href='" + itemCatData.getUrl() + "'>" + itemCat.getName() + "</a>");
            result.getItemCats().add(itemCatData);
            if (!itemCat.getIsParent()) {
                continue;
            }

            // 封装二级对象
            List<ItemCat> itemCatList2 = itemCatMap.get(itemCat.getId());
            List<ItemCatData> itemCatData2 = new ArrayList<ItemCatData>();
            itemCatData.setItems(itemCatData2);
            for (ItemCat itemCat2 : itemCatList2) {
                ItemCatData id2 = new ItemCatData();
                id2.setName(itemCat2.getName());
                id2.setUrl("/products/" + itemCat2.getId() + ".html");
                itemCatData2.add(id2);
                if (itemCat2.getIsParent()) {
                    // 封装三级对象
                    List<ItemCat> itemCatList3 = itemCatMap.get(itemCat2.getId());
                    List<String> itemCatData3 = new ArrayList<String>();
                    id2.setItems(itemCatData3);
                    for (ItemCat itemCat3 : itemCatList3) {
                        itemCatData3.add("/products/" + itemCat3.getId() + ".html|" + itemCat3.getName());
                    }
                }
            }
            if (result.getItemCats().size() >= 14) {
                break;
            }
        }
        
        //保存到redis中
        try {
            this.redisService.set(TAOTAO_MANAGE_ITEM_CAT_ALL, MAPPER.writeValueAsString(result), 60 * 60 * 24 * 30);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        
        return result;
    }

}

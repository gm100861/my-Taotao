package com.taotao.manage.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.vo.TaotaoItemMQResult;
import com.taotao.manage.base.mapper.TaotaoMapper;
import com.taotao.manage.mapper.ItemMapper;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.vo.SearchItem;

/**
 * 商品相关的业务逻辑
 * 
 * @author itcast
 *
 */
@Service
public class ItemService extends BaseService<Item> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemService.class);

    @Autowired
    private ItemMapper itemMapper;
    
    private static final ObjectMapper MAPPER = new ObjectMapper();
    
//    @Autowired
//    private HttpSolrServer httpSolrServer;
    
    @Resource(name = "itemMQTemplate")
    private RabbitTemplate rabbitTemplate;

    @Override
    public TaotaoMapper<Item> getMapper() {
        return this.itemMapper;
    }
    
    public void saveItem(Item item){
        //初始状态，状态为：1
        item.setStatus(1);
        item.setCreated(new Date());
        item.setUpdated(item.getCreated());
        super.save(item);
    }

    /**
     * 更新商品数据，不能修改的字段：ID、created、status
     * @param item
     */
    public void updateItem(Item item) {
        // 强制将不能修改的字段设为null
        item.setStatus(null);
        item.setCreated(null);
        //设置更新时间
        item.setUpdated(new Date());
        //更新数据
        this.itemMapper.updateByPrimaryKeySelective(item);
        
        //发送消息到交换机
        // {"itemId":1111}
        Date mqDate = new Date();
        TaotaoItemMQResult taotaoItemMQResult = new TaotaoItemMQResult(item.getId(), item.getUpdated(), mqDate);
        try {
            this.rabbitTemplate.convertAndSend("update", MAPPER.writeValueAsString(taotaoItemMQResult));
        } catch (Exception e) {
            LOGGER.error("发送消息失败! itemId = " + item.getId(), e);
        }
        
        //更新数据到solr索引
//        try {
//            item = this.itemMapper.selectByPrimaryKey(item);
//            SearchItem searchItem = new SearchItem();
//            // Item ==> SearchItem
//            BeanUtils.copyProperties(item, searchItem);
//            httpSolrServer.addBean(searchItem);
//            httpSolrServer.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        
        //更新redis缓存逻辑
        
        //更新 某某某 系统逻辑
    }
   
    public List<Item> queryListItemByIds(String[] ids) {
        return this.itemMapper.queryListItemByIds(ids);
    }
}

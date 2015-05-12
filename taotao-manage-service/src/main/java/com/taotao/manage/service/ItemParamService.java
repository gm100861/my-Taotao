package com.taotao.manage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.manage.base.mapper.TaotaoMapper;
import com.taotao.manage.mapper.ItemParamMapper;
import com.taotao.manage.pojo.ItemParam;

/**
 * 规格参数模板相关业务逻辑
 */
@Service
public class ItemParamService extends BaseService<ItemParam> {

    @Autowired
    private ItemParamMapper itemParamMapper;

    @Override
    public TaotaoMapper<ItemParam> getMapper() {
        return itemParamMapper;
    }

}

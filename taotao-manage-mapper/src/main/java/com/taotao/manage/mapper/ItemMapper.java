package com.taotao.manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.taotao.manage.base.mapper.TaotaoMapper;
import com.taotao.manage.pojo.Item;

public interface ItemMapper extends TaotaoMapper<Item>{
    List<Item> queryListItemByIds(@Param("ids")String[] ids);
}

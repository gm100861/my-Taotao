package com.taotao.manage.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.manage.base.mapper.TaotaoMapper;

/**
 * 通用Service，实现CRUD
 * 
 * @author itcast
 *
 * @param <T>
 */
@Service
public abstract class BaseService<T> {

    public abstract TaotaoMapper<T> getMapper();

    /**
     * 保存数据
     * 
     * @param t
     */
    public void save(T t) {
        getMapper().insert(t);
    }

    /**
     * 根据条件删除数据
     * 
     * @param t 条件
     * @return 删除数据的条数
     */
    public Integer delete(Long id) {
        return getMapper().deleteByPrimaryKey(id);
    }

    /**
     * 根据IDS删除
     * 
     * @param ids
     * @return
     */
    public Integer delete(Long[] ids) {
        return this.getMapper().deleteByIDS(ids);
    }

    /**
     * 根据条件修改数据
     * 
     * @param t
     * @return
     */
    public Integer update(T t) {
        return getMapper().updateByPrimaryKey(t);
    }
    
    /**
     * 只会更新不是null的数据
     * 
     * @param t
     * @return
     */
    public Integer updateSelective(T t) {
        return getMapper().updateByPrimaryKeySelective(t);
    }

    /**
     * 根据ID查询数据
     * 
     * @param id
     * @return
     */
    public T queryById(Long id) {
        return getMapper().selectByPrimaryKey(id);
    }

    /**
     * 根据条件查询数据
     * 
     * @return
     */
    public T queryByWhere(T t) {
        List<T> list = getMapper().select(t);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 查询所有数据
     * 
     * @return
     */
    public List<T> queryAll() {
        return getMapper().select(null);
    }

    /**
     * 根据条件查询
     * 
     * @return
     */
    public List<T> queryList(T t) {
        return getMapper().select(t);
    }

    /**
     * 分页查询数据
     * 
     * @return
     */
    public PageInfo<T> queryPageList(Integer page, Integer pageSize) {
        // 分页条件
        PageHelper.startPage(page, pageSize, true);
        List<T> list = this.queryAll();
        return new PageInfo<T>(list);
    }

    /**
     * 根据条件分页查询
     * 
     * @param page 当前页数
     * @param pageSize 每页显示的数据条数
     * @param t
     * @return
     */
    public PageInfo<T> queryPageList(Integer page, Integer pageSize, T t) {
        // 分页条件
        PageHelper.startPage(page, pageSize, true);
        List<T> list = this.queryList(t);
        return new PageInfo<T>(list);
    }

}

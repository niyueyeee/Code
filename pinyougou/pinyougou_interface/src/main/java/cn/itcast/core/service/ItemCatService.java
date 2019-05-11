package cn.itcast.core.service;

import cn.itcast.core.pojo.item.ItemCat;

import java.util.List;

/**
 * @author niyueyeee
 * @create 2019-04-29 20:13
 */
public interface ItemCatService {
    List<cn.itcast.core.pojo.item.ItemCat> findByParentId(Long id);

    void add(ItemCat itemCat);

    void update(ItemCat itemCat);

    cn.itcast.core.pojo.item.ItemCat findOne(Long id);

    void delete(Long[] ids);

    List<cn.itcast.core.pojo.item.ItemCat> findAll();

}

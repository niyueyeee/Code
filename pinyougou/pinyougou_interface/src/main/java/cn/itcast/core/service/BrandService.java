package cn.itcast.core.service;

import cn.itcast.core.pojo.good.Brand;
import entity.PageResult;

import java.util.List;
import java.util.Map;

/**
 * @author niyueyeee
 * @create 2019-04-23 20:49
 */
public interface BrandService {
    List<Brand> findAll();

    PageResult findPage(Integer pageNum, Integer pageSize);

    void add(cn.itcast.core.pojo.good.Brand brand);

    Brand findOne(Long id);

    void update(cn.itcast.core.pojo.good.Brand brand);

    void delete(Long[] ids);

    PageResult search(Integer pageNum, Integer pageSize, cn.itcast.core.pojo.good.Brand brand);

    List<Map> selectOptionList();

}

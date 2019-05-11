package cn.itcast.core.service;

import cn.itcast.core.pojo.template.TypeTemplate;
import entity.PageResult;

import java.util.List;
import java.util.Map;

/**
 * @author niyueyeee
 * @create 2019-04-27 15:35
 */
public interface TypeTemplateService {
    PageResult search(Integer page, Integer rows, cn.itcast.core.pojo.template.TypeTemplate tt);

    void add(TypeTemplate tt);

    cn.itcast.core.pojo.template.TypeTemplate findOne(Long id);

    void update(cn.itcast.core.pojo.template.TypeTemplate tt);

    void delete(Long[] ids);

    List<Map> findBySpecList(Long id);
}

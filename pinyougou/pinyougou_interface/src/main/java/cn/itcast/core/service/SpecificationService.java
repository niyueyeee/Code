package cn.itcast.core.service;

import cn.itcast.core.pojo.specification.Specification;
import entity.PageResult;
import vo.SpecificationVo;

import java.util.List;
import java.util.Map;

/**
 * @author niyueyeee
 * @create 2019-04-26 21:23
 */
public interface SpecificationService {
    PageResult search(Integer page, Integer rows, Specification specification);

    void add(SpecificationVo vo);

    SpecificationVo findOne(Long id);

    void update(SpecificationVo vo);

    void delete(long[] ids);

    List<Map> selectOptionList();
}

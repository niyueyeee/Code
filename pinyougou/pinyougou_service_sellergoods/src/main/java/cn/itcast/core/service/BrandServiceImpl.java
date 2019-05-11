package cn.itcast.core.service;

import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.dao.good.BrandDao;
import cn.itcast.core.pojo.good.BrandQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author niyueyeee
 * @create 2019-04-23 20:49
 */
@Service
@Transactional
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandDao brandDao;

    //查询所有
    @Override
    public List<Brand> findAll() {
        return brandDao.selectByExample(null);
    }

    //查询分页
    @Override
    public PageResult findPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Brand> brands = brandDao.selectByExample(null);
        PageInfo<Brand> p = new PageInfo<>(brands);
        return new PageResult(p.getTotal(), p.getList());
    }

    //条件查询分页
    @Override
    public PageResult search(Integer pageNum, Integer pageSize, Brand brand) {
        PageHelper.startPage(pageNum, pageSize);
        BrandQuery brandQuery = new BrandQuery();
        BrandQuery.Criteria criteria = brandQuery.createCriteria();
        if (null != brand.getName() && !"".equals(brand.getName())) {
            criteria.andNameLike("%" + brand.getName() + "%");
        }
        if (null != brand.getFirstChar() && !"".equals(brand.getFirstChar())) {
            criteria.andNameLike("%" + brand.getFirstChar() + "%");
        }
        List<Brand> brands = brandDao.selectByExample(brandQuery);
        PageInfo<Brand> p = new PageInfo<>(brands);
        return new PageResult(p.getTotal(), p.getList());
    }

    //查询所有品牌结果集返回值为List<Map>
    @Override
    public List<Map> selectOptionList() {
        return brandDao.selectOptionList();
    }

    //添加品牌
    @Override
    public void add(Brand brand) {
        brandDao.insertSelective(brand);
    }

    //查询一个
    @Override
    public Brand findOne(Long id) {
        return brandDao.selectByPrimaryKey(id);
    }

    //修改
    @Override
    public void update(Brand brand) {
        brandDao.updateByPrimaryKeySelective(brand);
    }

    //删除多个
    @Override
    public void delete(Long[] ids) {
        BrandQuery brandQuery = new BrandQuery();
        BrandQuery.Criteria criteria = brandQuery.createCriteria();
        criteria.andIdIn(Arrays.asList(ids));
        brandDao.deleteByExample(brandQuery);
    }
}

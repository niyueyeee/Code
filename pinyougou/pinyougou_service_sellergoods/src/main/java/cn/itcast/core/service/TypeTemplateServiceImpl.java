package cn.itcast.core.service;

import cn.itcast.core.dao.specification.SpecificationDao;
import cn.itcast.core.dao.specification.SpecificationOptionDao;
import cn.itcast.core.dao.template.TypeTemplateDao;
import cn.itcast.core.pojo.specification.Specification;
import cn.itcast.core.pojo.specification.SpecificationOption;
import cn.itcast.core.pojo.specification.SpecificationOptionQuery;
import cn.itcast.core.pojo.template.TypeTemplate;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author niyueyeee
 * @create 2019-04-27 15:36
 */
@Service
@Transactional
public class TypeTemplateServiceImpl implements TypeTemplateService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private TypeTemplateDao typeTemplateDao;
    @Autowired
    private SpecificationOptionDao specificationOptionDao;

    //查询所有加分页
    @Override
    public PageResult search(Integer page, Integer rows, cn.itcast.core.pojo.template.TypeTemplate tt) {
        List<TypeTemplate> typeTemplates = typeTemplateDao.selectByExample(null);
        for (cn.itcast.core.pojo.template.TypeTemplate typeTemplate : typeTemplates) {
            List<Map> brandList = JSON.parseArray(typeTemplate.getBrandIds(), Map.class);
            //缓存品牌列表
            redisTemplate.boundHashOps("brandList").put(typeTemplate.getId(), brandList);
            //获取List<Map>
            List<Map> specList = findBySpecList(typeTemplate.getId());
            redisTemplate.boundHashOps("specList").put(typeTemplate.getId(), specList);

        }
        PageHelper.startPage(page, rows);
        PageHelper.orderBy("id desc");
        Page<TypeTemplate> p = (Page<TypeTemplate>) typeTemplateDao.selectByExample(null);

        return new PageResult(p.getTotal(), p.getResult());
    }

    //添加
    @Override
    public void add(TypeTemplate tt) {
        typeTemplateDao.insertSelective(tt);
    }

    //查询一个模板对象
    @Override
    public TypeTemplate findOne(Long id) {
        return typeTemplateDao.selectByPrimaryKey(id);
    }

    //修改
    @Override
    public void update(TypeTemplate tt) {
        typeTemplateDao.updateByPrimaryKeySelective(tt);
    }

    //删除
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            typeTemplateDao.deleteByPrimaryKey(id);
        }
    }

    //查询模板对象 中规格集合 返回值 List<Map>
    @Override
    public List<Map> findBySpecList(Long id) {
        //1:入参:模板对象的ID
        TypeTemplate typeTemplate = typeTemplateDao.selectByPrimaryKey(id);
        // 2:查询:模板对象  从对象中获取出 String  specIds   = [{"id":27,"text":"网络"},{"id":32,"text":"机身内存"}]
        String specIds = typeTemplate.getSpecIds();


        //3:json格式转换   List<Map>  listMap = JSON.parseArray(specIds,Map.class)
        List<Map> listMap = JSON.parseArray(specIds, Map.class);
        for (Map map : listMap) {
            //4:根据上面的27  规格选项表的外键 查询集合
            SpecificationOptionQuery query = new SpecificationOptionQuery();
            // 类型转换异常   Object --> 基本类型(整数,String Boolean)    特殊类型  长整
            query.createCriteria().andSpecIdEqualTo(Long.parseLong(String.valueOf(map.get("id"))));
            //query.createCriteria().andSpecIdEqualTo((long)(Integer)(map.get("id")));
            List<SpecificationOption> specificationOptions = specificationOptionDao.selectByExample(query);//健壮
            //5:查询到的集合设置到上面的Map中
            map.put("options", specificationOptions);
        }
        /* Map1
            put(id,27)
            put(text,网络)
            put(options,List)*/
        return listMap;
    }
}

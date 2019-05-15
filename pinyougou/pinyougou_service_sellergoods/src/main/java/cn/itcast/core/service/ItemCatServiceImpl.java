package cn.itcast.core.service;

import cn.itcast.core.dao.item.ItemCatDao;
import cn.itcast.core.pojo.item.ItemCat;
import cn.itcast.core.pojo.item.ItemCatQuery;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author niyueyeee
 * @create 2019-04-29 20:13
 */
@Service
@Transactional
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private ItemCatDao itemCatDao;
    @Autowired
    private RedisTemplate redisTemplate;

    //搜索
    @Override
    public List<ItemCat> findByParentId(Long parentId) {
        //    1:查询Mysql中的商品分类结果集 保存到缓存中一份
        List<ItemCat> itemCats = findAll();
        for (cn.itcast.core.pojo.item.ItemCat itemCat : itemCats) {
            redisTemplate.boundHashOps("itemCat").put(itemCat.getName(), itemCat.getTypeId());
        }
        ItemCatQuery query = new ItemCatQuery();
        query.createCriteria().andParentIdEqualTo(parentId);
        return itemCatDao.selectByExample(query);
    }

    //添加
    @Override
    public void add(ItemCat itemCat) {
        itemCatDao.insertSelective(itemCat);
    }

    //修改
    @Override
    public void update(ItemCat itemCat) {
        itemCatDao.updateByPrimaryKeySelective(itemCat);
    }

    //回显
    @Override
    public ItemCat findOne(Long id) {
        return itemCatDao.selectByPrimaryKey(id);
    }

    //删除
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            itemCatDao.deleteByPrimaryKey(id);
        }
    }

    //查询所有
    @Override
    public List<ItemCat> findAll() {
        return itemCatDao.selectByExample(null);
    }
}

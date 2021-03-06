package cn.itcast.core.service;

import cn.itcast.core.dao.ad.ContentDao;
import cn.itcast.core.pojo.ad.Content;
import cn.itcast.core.pojo.ad.ContentQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentDao contentDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<Content> findAll() {
        List<Content> list = contentDao.selectByExample(null);
        return list;
    }

    @Override
    public PageResult findPage(Content content, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<Content> page = (Page<Content>) contentDao.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void add(Content content) {
        redisTemplate.boundHashOps("content").delete(content.getCategoryId());
        contentDao.insertSelective(content);
    }

    //更新
    @Override
    public void edit(Content content) {
        //修改:广告分类ID
        //去数据库查询原来的广告对象
        Content c = contentDao.selectByPrimaryKey(content.getId());

        //进入下面的方法之前进入切面 DataSourceTransactionManager dataSource 连接Mysql rollback
        //判断是否修改了广告分类ID
        if (!c.getCategoryId().equals(content.getCategoryId())) {
            redisTemplate.boundHashOps("content").delete(c.getCategoryId());
        }
        //现在的广告分类对应的集合多了一个  删除现在的广告分类的缓存
        //1: 删除缓存
        redisTemplate.boundHashOps("content").delete(content.getCategoryId());
        //修改Mysql数据库
        contentDao.updateByPrimaryKeySelective(content);
    }

    @Override
    public Content findOne(Long id) {

        Content content = contentDao.selectByPrimaryKey(id);
        return content;
    }

    @Override
    public void delAll(Long[] ids) {
        if (ids != null) {
            for (Long id : ids) {
//                redisTemplate.boundHashOps("content").delete(content.getCategoryId());
                contentDao.deleteByPrimaryKey(id);
            }
        }
    }

    //根据广告分类Id 查询此分类对应的广告集合
    @Override
    public List<Content> findByCategoryId(Long categoryId) {
        //1：先查询缓存
        List<Content> contentList = (List<Content>) redisTemplate.boundHashOps("content").get(categoryId);
        //2:没有 从数据库查
        if (null == contentList || contentList.size() == 0) {

            ContentQuery contentQuery = new ContentQuery();
            contentQuery.createCriteria().andCategoryIdEqualTo(categoryId).andStatusEqualTo("1");
            //排序
            contentQuery.setOrderByClause("sort_order desc");
            contentList = contentDao.selectByExample(contentQuery);
            //3: 存入缓存
            redisTemplate.boundHashOps("content").put(categoryId, contentList);
            redisTemplate.boundHashOps("content").expire(1, TimeUnit.HOURS);
        }
        //4:返回
        return contentList;
    }

}

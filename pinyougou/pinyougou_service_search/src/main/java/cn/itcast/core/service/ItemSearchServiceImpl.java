package cn.itcast.core.service;

import cn.itcast.core.pojo.item.Item;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 搜索管理
 */
@Service
public class ItemSearchServiceImpl implements ItemsearchService {

    @Autowired
    private SolrTemplate solrTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Map<String, Object> search(Map<String, String> searchMap) {
        HashMap<String, Object> resultMap = new HashMap<>();
        //1:商品分类结果集
        List<String> categoryListByKeywords = findCategoryListByKeywords(searchMap);
        resultMap.put("categoryList", categoryListByKeywords);
        if (null != categoryListByKeywords && categoryListByKeywords.size() > 0) {
            Object typeId = redisTemplate.boundHashOps("itemCat").get(categoryListByKeywords.get(0));
            //2:品牌结果集
            List<Map> brandList = (List<Map>) redisTemplate.boundHashOps("brandList").get(typeId);
            //3:规格结果集
            List<Map> specList = (List<Map>) redisTemplate.boundHashOps("apecList").get(typeId);
            resultMap.put("brandList", brandList);
            resultMap.put("specList", specList);
        }
        //4:查询结果集 总条数 总页数
        resultMap.putAll(search2(searchMap));
        return resultMap;
    }

    private List<String> findCategoryListByKeywords(Map<String, String> searchMap) {
        //关键词
        Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
        SimpleQuery query = new SimpleQuery(criteria);
        //根据商品分类进行分组查询
        GroupOptions groupOptions = new GroupOptions();
        groupOptions.addGroupByField("item_category");
        query.setGroupOptions(groupOptions);
        //执行查询
        GroupPage<Item> page = solrTemplate.queryForGroupPage(query, Item.class);
        //查询分组结果
        GroupResult<Item> item_category = page.getGroupResult("item_category");
        Page<GroupEntry<Item>> groupEntries = item_category.getGroupEntries();
        List<String> categoryList = new ArrayList<>();
        for (GroupEntry<cn.itcast.core.pojo.item.Item> itemGroupEntry : groupEntries.getContent()) {
            categoryList.add(itemGroupEntry.getGroupValue());
        }
        return categoryList;
    }

    //查询高亮结果集
    public Map<String, Object> search2(Map<String, String> searchMap) {
        Map<String, Object> resultMap = new HashMap<>();
        //关键词
        Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
        SimpleHighlightQuery highlightQuery = new SimpleHighlightQuery(criteria);
        //过滤条件
        //排序
        //分页
        String pageNo = searchMap.get("pageNo");
        String pageSize = searchMap.get("pageSize");
        highlightQuery.setOffset((Integer.parseInt(pageNo) - 1) * Integer.parseInt(pageSize));
        highlightQuery.setRows(Integer.parseInt(pageSize));

        //高亮
        HighlightOptions highlightOptions = new HighlightOptions();
        //设置高亮的域
        highlightOptions.addField("item_title");
        //前缀
        highlightOptions.setSimplePrefix("<em style='color:red'>");
        //后缀
        highlightOptions.setSimplePostfix("</em>");
        highlightQuery.setHighlightOptions(highlightOptions);
        //执行查询
        HighlightPage<Item> page = solrTemplate.queryForHighlightPage(highlightQuery, Item.class);
        //高亮的数据  entity=Item对象的值
        for (HighlightEntry<cn.itcast.core.pojo.item.Item> itemHighlightEntry : page.getHighlighted()) {
            Item entity = itemHighlightEntry.getEntity();
            List<HighlightEntry.Highlight> highlights = itemHighlightEntry.getHighlights();
            if (null != highlights && highlights.size() > 0) {
                entity.setTitle(highlights.get(0).getSnipplets().get(0));
            }
        }
        //分页结果集
        List<Item> content = page.getContent();

        resultMap.put("rows", content);
        //查询总条数
        resultMap.put("total", page.getTotalElements());
        //总页数
        resultMap.put("totalPages", page.getTotalPages());
        return resultMap;
    }

    //查询普通结果集
    public Map<String, Object> search1(Map<String, String> searchMap) {


        Map<String, Object> resultMap = new HashMap<>();
        //关键词
        Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
        Query query = new SimpleQuery(criteria);
        //过滤条件
        //排序
        //分页
        String pageNo = searchMap.get("pageNo");
        String pageSize = searchMap.get("pageSize");
        query.setOffset((Integer.parseInt(pageNo) - 1) * Integer.parseInt(pageSize));
        query.setRows(Integer.parseInt(pageSize));

        //高亮

        //执行查询
        ScoredPage<Item> page = solrTemplate.queryForPage(query, Item.class);

        //分页结果集
        List<Item> content = page.getContent();

        resultMap.put("rows", content);
        //查询总条数
        resultMap.put("total", page.getTotalElements());
        //总页数
        resultMap.put("totalPages", page.getTotalPages());
        return resultMap;
    }
}

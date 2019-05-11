package cn.itcast;

import cn.itcast.core.dao.item.ItemDao;
import cn.itcast.core.pojo.item.Item;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

/**
 * @author NiYueYeee
 * @create 2019-05-11 21:21
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/*.xml"})
public class ImportDataTest {
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private SolrTemplate solrTemplate;

    @Test
    public void importData(){
        List<Item> items = itemDao.selectByExample(null);
        for (cn.itcast.core.pojo.item.Item item : items) {
            item.setSpecMap(JSON.parseObject(item.getSpec(), Map.class));
        }
        solrTemplate.saveBeans(items);
        solrTemplate.commit();
    }
}

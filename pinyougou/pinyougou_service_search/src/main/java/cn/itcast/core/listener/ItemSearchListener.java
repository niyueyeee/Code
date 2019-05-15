package cn.itcast.core.listener;

import cn.itcast.core.dao.item.ItemDao;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.List;

/**
 * @author NiYueYeee
 * @create 2019-05-15 16:30
 */
public class ItemSearchListener implements MessageListener {
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public void onMessage(Message message) {
        ActiveMQTextMessage atm = (ActiveMQTextMessage) message;
        //商品ID
        try {
            String id = atm.getText();
            System.out.println("jieshou ID:" + id);
            //2:将商品信息保存到索引库
            cn.itcast.core.pojo.item.ItemQuery itemQuery = new cn.itcast.core.pojo.item.ItemQuery();
            itemQuery.createCriteria().andGoodsIdEqualTo(Long.valueOf(id)).andIsDefaultEqualTo("1").andStatusEqualTo("1");
            List<cn.itcast.core.pojo.item.Item> itemList = itemDao.selectByExample(itemQuery);
            solrTemplate.saveBeans(itemList, 1000);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}

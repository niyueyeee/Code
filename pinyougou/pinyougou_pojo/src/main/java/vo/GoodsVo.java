package vo;

import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.pojo.good.GoodsDesc;
import cn.itcast.core.pojo.item.Item;

import java.io.Serializable;
import java.util.List;

/**
 * @author niyueyeee
 * @create 2019-04-30 9:58
 */
public class GoodsVo implements Serializable {

    //商品对象
    private cn.itcast.core.pojo.good.Goods goods;

    //商品详情对象
    private cn.itcast.core.pojo.good.GoodsDesc goodsDesc;

    //库存集合对象
    private List<cn.itcast.core.pojo.item.Item> itemList;

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public GoodsDesc getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(GoodsDesc goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }
}

package cn.itcast.core.service;

import cn.itcast.core.dao.seller.SellerDao;
import cn.itcast.core.pojo.seller.Seller;
import cn.itcast.core.pojo.seller.SellerQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author niyueyeee
 * @create 2019-04-28 21:45
 */
@Service
@Transactional
public class SellerServiceImpl implements SellerService {
    @Autowired
    private SellerDao sellerDao;

    //添加
    @Override
    public void add(cn.itcast.core.pojo.seller.Seller seller) {
        seller.setPassword(new BCryptPasswordEncoder().encode(seller.getPassword()));
        seller.setStatus("0");
        sellerDao.insertSelective(seller);
    }

    //根据用户名查询用户对象
    @Override
    public cn.itcast.core.pojo.seller.Seller findOne(String username) {

        return sellerDao.selectByPrimaryKey(username);
    }

    //查询未审核
    @Override
    public PageResult search(Integer page, Integer rows, cn.itcast.core.pojo.seller.Seller seller) {
        PageHelper.startPage(page, rows);
        SellerQuery query = new SellerQuery();
        query.createCriteria().andStatusEqualTo(seller.getStatus());
        Page<Seller> p = (Page<Seller>) sellerDao.selectByExample(query);
        return new PageResult(p.getTotal(), p.getResult());
    }

    //修改
    @Override
    public void updateStatus(String sellerId, String status) {
        Seller seller = new Seller();
        if ("1".equals(status)) {
            seller.setStatus(status);
            seller.setSellerId(sellerId);
            sellerDao.updateByPrimaryKeySelective(seller);
        }
    }
}
